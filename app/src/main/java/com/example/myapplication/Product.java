package com.example.myapplication;


public class Product {
    private String title;
    private String Desc;
    private int price;
    private String status;
    private int quantity;
    private Double rating;
    private String image_url;
    private String contact;
    private String Email;
    private Double latitude;
    private Double longitude;

    public Product() {
    }

    public Product(String Email,String title, String desc, int price, String status,
                   int quantity, String image_url, String contact,Double latitude,Double longitude) {
        this.Email=Email;
        this.title = title;
        this.Desc = desc;
        this.price = price;
        this.status = status;
        this.quantity = quantity;
        this.image_url = image_url;
        this.contact = contact;
        this.latitude=latitude;
        this.longitude=longitude;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}