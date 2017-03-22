package com.IEEE.SCD;


public class suspect {
    public String getName() {
        if(this.name!=null)
        return name;
        else return"--";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        if(this.id!=null)
        return id;
        else return "-";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        if(this.height!=null)
        return height;
        else return "--";
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBody_type() {
        if (this.body_type!=null)
            return body_type;
        else return "--";
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public void setSkin_color(String skin_color) {
        this.skin_color = skin_color;
    }

    String name,id,dob,gender,height,body_type,skin_color;
}
