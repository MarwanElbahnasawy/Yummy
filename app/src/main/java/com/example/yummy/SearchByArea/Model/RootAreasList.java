package com.example.yummy.SearchByArea.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RootAreasList {

	@SerializedName("meals")
	private List<EachAreaModel> meals;

	public List<EachAreaModel> getMeals(){
		return meals;
	}
}