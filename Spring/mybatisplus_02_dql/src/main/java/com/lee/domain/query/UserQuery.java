package com.lee.domain.query;

import com.lee.domain.User;
import lombok.Data;

@Data
public class UserQuery extends User {
    // 一般数值型的一般会有上下限，比如价格，还有日期型也会有上下限，字符串类型的是不会有上下限的
    // 继承自User那么User所有的属性都会有，再声明个具有上限的age2属性
    private Integer age2;
}
