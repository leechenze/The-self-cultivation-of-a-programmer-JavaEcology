package com.lee.domain;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;

// lombok
// @Setter
// @Getter
// @ToString
// @NoArgsConstructor
// @AllArgsConstructor
// @EqualsAndHashCode
// lombok 简写注解：data 等同于 以上的所有注解
@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private Integer age;
    private String tel;
}
