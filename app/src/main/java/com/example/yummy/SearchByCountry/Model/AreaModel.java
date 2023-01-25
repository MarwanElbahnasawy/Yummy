package com.example.yummy.SearchByCountry.Model;

import com.google.gson.annotations.SerializedName;

public class AreaModel {

	@SerializedName("strArea")
	private String strArea;

	public String getStrArea(){
		return strArea;
	}
}