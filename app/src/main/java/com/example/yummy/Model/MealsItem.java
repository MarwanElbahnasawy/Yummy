package com.example.yummy.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "MealsItem", primaryKeys = {"strMeal", "weekDay"})
public class MealsItem implements Serializable {

    public String documentID;
    private String currentUserEmail;
    @NonNull
    private String strMeal;
    @NonNull
    private String weekDay;

    public MealsItem(String documentID, String strMeal, String strArea, String strMealThumb, String strInstructions, String strYoutube, String weekDay) {
        this.documentID = documentID;
        this.strMeal = strMeal;
        this.strArea = strArea;
        this.strMealThumb = strMealThumb;
        this.strInstructions = strInstructions;
        this.strYoutube = strYoutube;
        this.weekDay = weekDay;
    }



    private String strIngredient10;
    private String strIngredient12;
    private String strIngredient11;
    private String strIngredient14;
    private String strCategory;
    private String strIngredient13;
    private String strIngredient15;
    private String strArea;
    private String strTags;
    private String idMeal;
    private String strInstructions;
    private String strIngredient1;
    private String strIngredient3;
    private String strIngredient2;
    private String strIngredient5;
    private String strIngredient4;
    private String strIngredient7;
    private String strIngredient6;
    private String strIngredient9;
    private String strIngredient8;
    private String strMealThumb;
    private String strYoutube;


    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure10;
    private String strMeasure11;
    private String strMeasure9;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure1;
    private String strMeasure2;
    private String strMeasure15;

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }



    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }



    public String getStrIngredient10() {
        return strIngredient10;
    }

    public String getStrIngredient12() {
        return strIngredient12;
    }

    public String getStrIngredient11() {
        return strIngredient11;
    }

    public String getStrIngredient14() {
        return strIngredient14;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrIngredient13() {
        return strIngredient13;
    }



    public String getStrIngredient15() {
        return strIngredient15;
    }



    public String getStrArea() {
        return strArea;
    }



    public String getStrTags() {
        return strTags;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrIngredient1() {
        return strIngredient1;
    }

    public String getStrIngredient3() {
        return strIngredient3;
    }

    public String getStrIngredient2() {
        return strIngredient2;
    }


    public String getStrIngredient5() {
        return strIngredient5;
    }

    public String getStrIngredient4() {
        return strIngredient4;
    }

    public String getStrIngredient7() {
        return strIngredient7;
    }

    public String getStrIngredient6() {
        return strIngredient6;
    }

    public String getStrIngredient9() {
        return strIngredient9;
    }

    public String getStrIngredient8() {
        return strIngredient8;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMeasure12() {
        return strMeasure12;
    }

    public String getStrMeasure13() {
        return strMeasure13;
    }

    public String getStrMeasure10() {
        return strMeasure10;
    }

    public String getStrMeasure11() {
        return strMeasure11;
    }

    public String getStrMeasure9() {
        return strMeasure9;
    }

    public String getStrMeasure7() {
        return strMeasure7;
    }

    public String getStrMeasure8() {
        return strMeasure8;
    }

    public String getStrMeasure5() {
        return strMeasure5;
    }

    public String getStrMeasure6() {
        return strMeasure6;
    }

    public String getStrMeasure3() {
        return strMeasure3;
    }

    public String getStrMeasure4() {
        return strMeasure4;
    }

    public String getStrMeasure1() {
        return strMeasure1;
    }

    public String getStrMeasure2() {
        return strMeasure2;
    }

    public String getStrMeasure15() {
        return strMeasure15;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public void setStrIngredient10(String strIngredient10) {
        this.strIngredient10 = strIngredient10;
    }

    public void setStrIngredient12(String strIngredient12) {
        this.strIngredient12 = strIngredient12;
    }

    public void setStrIngredient11(String strIngredient11) {
        this.strIngredient11 = strIngredient11;
    }

    public void setStrIngredient14(String strIngredient14) {
        this.strIngredient14 = strIngredient14;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public void setStrIngredient13(String strIngredient13) {
        this.strIngredient13 = strIngredient13;
    }

    public void setStrIngredient15(String strIngredient15) {
        this.strIngredient15 = strIngredient15;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public void setStrTags(String strTags) {
        this.strTags = strTags;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public void setStrIngredient1(String strIngredient1) {
        this.strIngredient1 = strIngredient1;
    }

    public void setStrIngredient3(String strIngredient3) {
        this.strIngredient3 = strIngredient3;
    }

    public void setStrIngredient2(String strIngredient2) {
        this.strIngredient2 = strIngredient2;
    }

    public void setStrIngredient5(String strIngredient5) {
        this.strIngredient5 = strIngredient5;
    }

    public void setStrIngredient4(String strIngredient4) {
        this.strIngredient4 = strIngredient4;
    }

    public void setStrIngredient7(String strIngredient7) {
        this.strIngredient7 = strIngredient7;
    }

    public void setStrIngredient6(String strIngredient6) {
        this.strIngredient6 = strIngredient6;
    }

    public void setStrIngredient9(String strIngredient9) {
        this.strIngredient9 = strIngredient9;
    }

    public void setStrIngredient8(String strIngredient8) {
        this.strIngredient8 = strIngredient8;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public void setStrMeasure12(String strMeasure12) {
        this.strMeasure12 = strMeasure12;
    }

    public void setStrMeasure13(String strMeasure13) {
        this.strMeasure13 = strMeasure13;
    }

    public void setStrMeasure10(String strMeasure10) {
        this.strMeasure10 = strMeasure10;
    }

    public void setStrMeasure11(String strMeasure11) {
        this.strMeasure11 = strMeasure11;
    }

    public void setStrMeasure9(String strMeasure9) {
        this.strMeasure9 = strMeasure9;
    }

    public void setStrMeasure7(String strMeasure7) {
        this.strMeasure7 = strMeasure7;
    }

    public void setStrMeasure8(String strMeasure8) {
        this.strMeasure8 = strMeasure8;
    }

    public void setStrMeasure5(String strMeasure5) {
        this.strMeasure5 = strMeasure5;
    }

    public void setStrMeasure6(String strMeasure6) {
        this.strMeasure6 = strMeasure6;
    }

    public void setStrMeasure3(String strMeasure3) {
        this.strMeasure3 = strMeasure3;
    }

    public void setStrMeasure4(String strMeasure4) {
        this.strMeasure4 = strMeasure4;
    }

    public void setStrMeasure1(String strMeasure1) {
        this.strMeasure1 = strMeasure1;
    }

    public void setStrMeasure2(String strMeasure2) {
        this.strMeasure2 = strMeasure2;
    }

    public void setStrMeasure15(String strMeasure15) {
        this.strMeasure15 = strMeasure15;
    }
}


