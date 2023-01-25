package com.example.yummy.SearchByCountry.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AreaListModel {

	@SerializedName("meals")
	private List<AreaModel> meals;

	public List<AreaModel> getMeals(){
		return meals;
	}
}