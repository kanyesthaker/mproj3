package com.example.kanyes.mdbsocials;

import java.io.Serializable;

/**
 * Created by Kanyes on 9/27/2017.
 */

public class Social implements Serializable {
    public String email;
    public String event;
    public String id;
    public String num_interested;
    public String description;
    public String date;

    public Social(){
        email = "";
        event = "";
        id = "";
        num_interested = "";
        description = "";
        date = "";
    }

    public Social(String email, String event, String id, String num_interested, String description, String date){
        this.email = email;
        this.event = event;
        this.id = id;
        this.num_interested = num_interested;
        this.description = description;
        this.date = date;
    }
    public String getEmail(){return email;}
    public String getEvent(){return event;}
    public String getId(){return id;}
    public String getNum_interested(){return num_interested;}
    public String getDescription(){return description;}
    public String getDate(){return date;}
    public void setEmail(String email){this.email = email;}
    public void setEvent(String event){this.event = event;}
    public void setId(String image){this.id = id;}
    public void setNum_interested(String num_interested){this.num_interested = num_interested;}
    public void setDescription(String description){this.description = description;}
    public void setDate(String date){this.date = date;}
}
