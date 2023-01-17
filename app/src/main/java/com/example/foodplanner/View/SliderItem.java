package com.example.foodplanner.View;

public class SliderItem {

    //Here u can use string variable to store url if u want to load image from
    //the internet
//    private int image;
//
//
//    public SliderItem(int image){
//        this.image = image;
//    }
//
//    public int getImage(){
//        return image;
//    }

    private String imageURL;
    private String mealName;

    public SliderItem(String imageURL, String mealName){
        this.imageURL = imageURL;
        this.mealName = mealName;
    }

    public String getImageURL(){
        return imageURL;
    }

    public String getMealName() {
        return mealName;
    }
}
