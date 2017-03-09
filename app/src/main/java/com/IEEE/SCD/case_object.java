package com.IEEE.SCD;

/**
 * Created by uae25 on 3/3/2017.
 */

public class case_object {
    public String getId() {
        return id;
    }

    String id;
    String date;
    double lat;
    double lang;
    String time;

    public String getSuspect() {
        return suspect;
    }

    public String getVictim() {
        return victim;
    }

    public String getEvidence() {
        return evidence;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public double getLang() {
        return lang;
    }

    public double getLat() {
        return lat;
    }

    public String getDate() {
        return date;
    }

    String type;
    String evidence;
    String victim;
    String suspect;
    String wit_name;
    String wit_height;
    String wit_skin;

    public String getWit_eth() {
        return wit_eth;
    }

    public void setWit_eth(String wit_eth) {
        this.wit_eth = wit_eth;
    }

    String wit_eth;
    public String getWit_body() {
        return wit_body;
    }

    public void setWit_body(String wit_body) {
        this.wit_body = wit_body;
    }

    String wit_body;

    public String getWit_name() {
        return wit_name;
    }

    public void setWit_name(String wit_name) {
        this.wit_name = wit_name;
    }

    public String getWit_height() {
        return wit_height;
    }

    public void setWit_height(String wit_height) {
        this.wit_height = wit_height;
    }

    public String getWit_skin() {
        return wit_skin;
    }

    public void setWit_skin(String wit_skin) {
        this.wit_skin = wit_skin;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    String weapon;
    void setId(String id1){
        id=id1;
    }
    void setDate(String date1){
        date=date1;
    }
    void setLat(double lat1){
        lat=lat1;
    }
    void setLang(double lang1){
        lang=lang1;

    }
    void setTime(String time1){
        time=time1;
    }
    void setType(String type1){
        type=type1;
    }
    void setEvidence(String evidence1){
        if(evidence==null)
            evidence=evidence1+",";
        else
        evidence=evidence+evidence1+",";
    }
    void setVictim(String victim1){
        if(victim==null)
            victim=victim1+",";
        else
        victim=victim+victim1+",";
    }
    void setSuspect(String suspect1){
        if(suspect==null)
            suspect=suspect1+",";
        else
        suspect=suspect+suspect1+",";
    }
    void setDeletevictim(String victim1){
        victim=victim1;
    }
    void setDeletesuspect(String suspect1){
        suspect=suspect1;
    }
    void setDeleteEvidence(String evidence1){
        evidence=evidence1;
    }
}
