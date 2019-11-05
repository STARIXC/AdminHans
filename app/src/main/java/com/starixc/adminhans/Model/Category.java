package com.starixc.adminhans.Model;

public class Category {
    private String Name;
    private String Image;
    private String cid;

    public Category() {
    }

    public Category(String name, String image, String cid) {
        Name = name;
        Image = image;
        this.cid = cid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
