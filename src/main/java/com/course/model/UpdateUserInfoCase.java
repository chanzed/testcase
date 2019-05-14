package com.course.model;

import lombok.Data;

@Data
public class UpdateUserInfoCase {
    private int id;
    private int userId;
    private String userName;
    private String password;
    private int age;
    private String gender;
    private int isDelete;
    private int permission;
    private String expected;

}
