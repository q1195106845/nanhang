package com.nanhang.ad.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author yangjianghe
 * @Date 2018/10/20 10:15
 **/
@Data
public class User {

    private String userName;

    private String password;

    private String cn;

    private String group;

    /**
     * 禁用状态 0：启用 1：禁用
     */
    private String disabled;

    private String name;



    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", cn='" + cn + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
