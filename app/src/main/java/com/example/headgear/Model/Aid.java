package com.example.headgear.Model;

public class Aid {
    private String Description;
    private String Image;
    private String Title;

    public Aid() {
    }

    public Aid(String description, String image, String title) {
        Description = description;
        Image = image;
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
