package com.lee.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_user")
public class User {
    private Long id;
    private String name;
    @TableField(value = "pwd", select = false)
    private String password;
    private Integer age;
    private String tel;
    private String gender;
    @TableField(exist = false)
    private Integer online;
}
