package edu.wtamu.cis.cidm4385saru.changeexchain;

import java.util.UUID;

/**
 * Created by sarup on 4/8/2018.
 */

public class User {
    private UUID mId;
    private String mUsername;
    private String mPassword;

   public User(){
       this(UUID.randomUUID());
   }

   public User (UUID id){
       mId = id;
   }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }
}

