package com.IEEE.SCD;

/**
 * Created by uae25 on 4/9/2017.
 */

public class SCD_item {
    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getSuspect_id() {
        return suspect_id;
    }

    public void setSuspect_id(String suspect_id) {
        this.suspect_id = suspect_id;
    }

    public String getAvg_location() {
        return avg_location;
    }

    public void setAvg_location(String avg_location) {
        this.avg_location = avg_location;
    }

    public String getAvg_victims() {
        return avg_victims;
    }

    public void setAvg_victims(String avg_victims) {
        this.avg_victims = avg_victims;
    }

    public String getAvg_evidence() {
        return avg_evidence;
    }

    public void setAvg_evidence(String avg_evidence) {
        this.avg_evidence = avg_evidence;
    }

    public String getAvg_type() {
        return avg_type;
    }

    public void setAvg_type(String avg_type) {
        this.avg_type = avg_type;
    }

    public String getAvg_weapon() {
        return avg_weapon;
    }

    public void setAvg_weapon(String avg_weapon) {
        this.avg_weapon = avg_weapon;
    }

    public String getAvg_witness() {
        return avg_witness;
    }

    public void setAvg_witness(String avg_witness) {
        this.avg_witness = avg_witness;
    }

    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    String case_id;
    String suspect_id;
    String avg_location;
    String avg_victims;
    String avg_evidence;
    String avg_type;
    String avg_weapon;
    String avg_witness;
    String overall;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
}
