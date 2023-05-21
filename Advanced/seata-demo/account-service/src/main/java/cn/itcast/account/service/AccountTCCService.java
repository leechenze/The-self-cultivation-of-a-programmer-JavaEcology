package cn.itcast.account.service;


import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface AccountTCCService {
    /**
     * name表示try方法
     * commitMethod表示confirm方法
     * rollbackMethod表示cancel方法
     */
    @TwoPhaseBusinessAction(name = "deduct", commitMethod = "confirm", rollbackMethod = "cancel")
    void deduct(@BusinessActionContextParameter(paramName = "userId") String userId,
                @BusinessActionContextParameter(paramName = "money") int money);

    /** 这两个方法是获取事务信息和参数信息的，前提是必须要在Try的方法中指定 @BusinessActionContextParameter 这个注解 */
    boolean confirm(BusinessActionContext ctx);

    boolean cancel(BusinessActionContext ctx);


}
