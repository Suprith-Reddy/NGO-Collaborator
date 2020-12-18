package com.supri.education.Model;

/**
 * Created by Administrator on 10/10/2017.
 */

public class UserModel {
    private String id;
    private String type;
    private String ngotype;
    private String username;
    private String fullname;
    private String password;
    private String email;
    private String contact;

    public UserModel() {

    }

    public UserModel(String type, String ngotype, String fullname, String username, String password, String email, String contact) {
        this.type = type;
        this.ngotype = ngotype;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.contact = contact;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public String getNgotype() { return ngotype; }
    public String getUsername() { return username; }
    public String getFullname() { return fullname; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getContact() { return contact; }

    public void setId(String id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setNgotype(String ngotype) { this.ngotype = ngotype; }
    public void setUsername(String username) { this.username = username; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setContact(String contact) {this.contact = contact;}
}
