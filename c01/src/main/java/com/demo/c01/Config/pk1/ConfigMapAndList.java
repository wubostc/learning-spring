package com.demo.c01.Config.pk1;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties
public class ConfigMapAndList {
    private List<String> users;

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ConfigMapAndList{" +
                "users=" + users +
                '}';
    }


}
