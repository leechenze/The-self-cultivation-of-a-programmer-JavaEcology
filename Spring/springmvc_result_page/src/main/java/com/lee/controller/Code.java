package com.lee.controller;

public class Code {
    /**
     * 200系列：成功状态码定义
     * 200 + 操作码(1,2,3,4) + 成功与否(0,1)
     */
    public static final Integer SAVE_OK = 20011;
    public static final Integer DELETE_OK = 20021;
    public static final Integer UPDATE_OK = 20031;
    public static final Integer GET_OK = 20041;

    /**
     * 200系列：失败状态码定义
     * 200 + 操作码(1,2,3,4) + 成功与否(0,1)
     */
    public static final Integer SAVE_ERR = 20010;
    public static final Integer DELETE_ERR = 20020;
    public static final Integer UPDATE_ERR = 20030;
    public static final Integer GET_ERR = 20040;

    /**
     * 500系列：
     */
    public static final Integer SYSTEM_ERR = 50001;
    public static final Integer BUSINESS_ERR = 50002;
    public static final Integer OTHER_ERR = 50003;

}
