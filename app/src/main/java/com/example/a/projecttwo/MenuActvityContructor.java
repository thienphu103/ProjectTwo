package com.example.a.projecttwo;

import java.io.Serializable;

public class MenuActvityContructor implements Serializable {
    String images;
    String title;
    String price;

    public MenuActvityContructor() {

    }

    public MenuActvityContructor(String images, String title, String price) {
        this.images = images;
        this.title = title;
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
