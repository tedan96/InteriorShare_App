package com.example.aws.blogapp.Models;

public class Shopping {
    String productImg;
    private String productId;
    private String price;
    private String link;

    public Shopping(String productId, String price, String productImg, String link) {
        this.productImg = productImg;
        this.productId = productId;
        this.price = price;
        this.link = link;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setlink(String link) {
        this.link = link;
    }

    public String getProductImg() {
        return productImg;
    }

    public String getProductId() {
        return productId;
    }

    public String getPrice() {
        return price;
    }

    public String getlink() {return link;}
}