package com.lee.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

// lombok
// @Setter
// @Getter
// @ToString
// @NoArgsConstructor
// @AllArgsConstructor
// @EqualsAndHashCode
// lombok 简写注解：data 等同于 以上的所有注解
@Data
// @TableName("tb_user")
public class User {
    // 在application中进行配置该项（ID生成策略）
    // @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    @TableField(value = "pwd", select = false)
    private String password;
    private Integer age;
    private String tel;
    private String gender;
    @TableField(exist = false)
    private Integer online;
    // 逻辑删除字段，标记当前是否被删除，同样在application中全局配置了该项。
    // @TableLogic(value = "0", delval = "1")
    private Integer deleted;
    @Version
    private Integer version;


}
