package com.mxn.soul.flowingdrawer.data;

/**
 * Created by ZEROYU on 5/10/2016.
 */
public class UserMessage {
    private String Id;
    private String username;
    private String password;

    public UserMessage(String username, String password) {
        this.Id = null; //default
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
