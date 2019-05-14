package com.course.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String userName;
    private String password;
    private int age;
    private String gender;
    private int isDelete;
    private int permission;

    @Override
    public String toString(){
        return (
                "{id:" + id + "," +
                "userName:" + userName + "," +
                "password:" + password + "," +
                "age:" + age + "," +
                "gender:" + gender + "," +
                "isDelete:" + isDelete + "," +
                "permission:" + permission + "}"
                );
    }
}
