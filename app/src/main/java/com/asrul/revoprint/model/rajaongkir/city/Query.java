package com.asrul.revoprint.model.rajaongkir.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query{

	@SerializedName("province")
	@Expose
	private String province;

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}
}