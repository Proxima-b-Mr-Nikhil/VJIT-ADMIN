package com.nikhil.vjitadmin;

public class User {

    private String id;
    private String name;
    private String year;
    private String roll;
    private String phone;
    private String section;
    private String imageurl;
    private String status;
    private String search;
    private String verification;
    private String position;

    public User(String id, String name, String imageurl, String status, String search, String year, String section, String roll, String phone, String verification, String position) {
        this.id = id;
        this.name = name;
        this.imageurl = imageurl;
        this.status = status;
        this.search = search;
        this.year = year;
        this.section = section;
        this.roll = roll;
        this.phone = phone;
        this.verification = verification;
        this.position = position;
    }

    public User() {

    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
