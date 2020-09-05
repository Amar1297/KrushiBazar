package com.example.myapplication;

public class Product {
    private String title;
    private String Desc;
    private String price;
    private String status;
    private String quantity;
    private String image_url;
    private  String contact;
    private String rating;

    public Product() {
    }

    public Product(String title, String desc, String price, String status, String quantity, String image_url, String contact, String rating) {
        this.title = title;
        Desc = desc;
        this.price = price;
        this.status = status;
        this.quantity = quantity;
        this.image_url = image_url;
        this.contact = contact;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

