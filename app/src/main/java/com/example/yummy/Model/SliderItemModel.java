package com.example.yummy.Model;

public class SliderItemModel {

    private String imageURL;
    private String mealName;

    public SliderItemModel(String imageURL, String mealName) {
        this.imageURL = imageURL;
        this.mealName = mealName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getMealName() {
        return mealName;
    }
}
