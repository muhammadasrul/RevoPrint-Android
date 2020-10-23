package com.asrul.revoprint.model.rajaongkir.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("city_name")
	@Expose
	private String cityName;

	@SerializedName("province")
	@Expose
	private String province;

	@SerializedName("province_id")
	@Expose
	private String provinceId;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("postal_code")
	@Expose
	private String postalCode;

	@SerializedName("city_id")
	@Expose
	private String cityId;

	public ResultsItem(String cityName, String province, String provinceId, String type, String postalCode, String cityId) {
		this.cityName = cityName;
		this.province = province;
		this.provinceId = provinceId;
		this.type = type;
		this.postalCode = postalCode;
		this.cityId = cityId;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
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

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public void setCityId(String cityId){
		this.cityId = cityId;
	}

	public String getCityId(){
		return cityId;
	}
}