package cn.itcast.account.service.impl;

import cn.itcast.account.entity.AccountFreeze;
import cn.itcast.account.mapper.AccountFreezeMapper;
import cn.itcast.account.mapper.AccountMapper;
import cn.itcast.account.service.AccountTCCService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class AccountTCCServiceImpl implements AccountTCCService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFreezeMapper accountFreezeMapper;

    @Override
    @Transactional
    public void deduct(String userId, int money) {

        // 获取事务ID
        String xid = RootContext.getXID();

        // 判断业务悬挂
        // 判读accountFreeze是否有冻结记录，如果有，一定是CANCEL执行过，就变成悬挂业务了，要拒绝业务。
        AccountFreeze oldAccountFreeze = accountFreezeMapper.selectById(xid);
        if (oldAccountFreeze != null) {
            // CANCEL执行过，要拒绝业务。
            return;
        }

        // 扣减可用金额
        accountMapper.deduct(userId, money);
        // 记录冻结金额，事务状态。
        AccountFreeze accountFreeze = new AccountFreeze();
        accountFreeze.setUserId(userId);
        accountFreeze.setFreezeMoney(money);
        accountFreeze.setState(AccountFreeze.State.TRY);
        accountFreeze.setXid(xid);
        accountFreezeMapper.insert(accountFreeze);

    }

    /**
     * confirm提交事务逻辑
     * @param ctx
     * @return
     */
    @Override
    public boolean confirm(BusinessActionContext ctx) {
        // 获取事务ID
        String xid = ctx.getXid();

        // 根据ID删除冻结记录
        int count = accountFreezeMapper.deleteById(xid);
        return count == 1;
    }

    /**
     * cancel回滚事务逻辑
     * @param ctx
     * @return
     */
    @Override
    public boolean cancel(BusinessActionContext ctx) {

        // 获取xid 和 userId
        String xid = ctx.getXid();
        String userId = ctx.getActionContext("userId").toString();
        // 查询冻结记录
        AccountFreeze accountFreeze = accountFreezeMapper.selectById(xid);

        // 空回滚的判断，判断accountFreeze是否为Null。
        if (accountFreeze == null) {
            // 为Null证明try没执行，需要空回滚。
            accountFreeze.setUserId(userId);
            accountFreeze.setFreezeMoney(0);
            accountFreeze.setState(AccountFreeze.State.CANCEL);
            accountFreeze.setXid(xid);
            accountFreezeMapper.insert(accountFreeze);
            return true;
        }

        // 判断幂等
        if (accountFreeze.getState() == AccountFreeze.State.CANCEL) {
            // 证明已经处理过一次CANCEL了，无需重复处理。
            return true;
        }

        // 恢复可用余额
        accountMapper.refund(accountFreeze.getUserId(), accountFreeze.getFreezeMoney());

        // 将冻结金额清零，状态改为CANCEL
        accountFreeze.setFreezeMoney(0);
        accountFreeze.setState(AccountFreeze.State.CANCEL);

        // 更新操作
        int count = accountFreezeMapper.updateById(accountFreeze);
        return count == 1;
    }
}
