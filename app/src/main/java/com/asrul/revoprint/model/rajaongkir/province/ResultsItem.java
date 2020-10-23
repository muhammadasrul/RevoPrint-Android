package com.asrul.revoprint.model.rajaongkir.province;

import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("province")
	private String province;

	@SerializedName("province_id")
	private String provinceId;

	public ResultsItem(String province, String provinceId) {
		this.province = province;
		this.provinceId = provinceId;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setProvinceId(String provinceId){
		this.provinceId = provinceId;
	}

	public String getProvinceId(){
		return provinceId;
	}
}