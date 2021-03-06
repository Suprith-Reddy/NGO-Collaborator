package com.supri.education.Model;

/**
 * Created by Administrator on 10/10/2017.
 */

public class ReactModel {
    private String id;
    private String username;
    private String authID;
    private String description;
    private String address;
    private String contact;
    private String timestamp;

    public ReactModel() {}

    public ReactModel(String authid, String username, String description, String address, String contact) {
        this.authID = authid;
        this.username = username;
        this.description = description;
        this.address = address;
        this.contact = contact;
        this.timestamp = Long.toString(System.currentTimeMillis()/1000);
    }

    public String getId() { return id; }
    public String getAuthID() { return authID; }
    public String getUsername() { return username; }
    public String getDescription() { return description; }
    public String getAddress() { return address; }
    public String getTimestamp() { return timestamp; }
    public String getContact() { return contact; }

    public void setId(String id) { this.id = id; }
    public void setAuthID(String id) { this.authID = id; }
    public void setUsername(String username) { this.username = username; }
    public void setDescription(String description) { this.description = description; }
    public void setAddress(String address) { this.address = address; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setContact(String contact) {this.contact = contact;}
}
