package com.rrs.entities;

import java.sql.Connection;

public class Admin {
    private int adminId;
    private String adminName;
    private String email;
    private String password;
    private Connection conn;

    public Admin(int adminId, String adminName, String email, String password) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.email = email;
        this.password = password;
    }

    public Admin() {}

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

