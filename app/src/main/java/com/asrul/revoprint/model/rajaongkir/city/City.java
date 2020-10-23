package com.asrul.revoprint.model.rajaongkir.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City{

	@SerializedName("rajaongkir")
	@Expose
	private Rajaongkir rajaongkir;

	public void setRajaongkir(Rajaongkir rajaongkir){
		this.rajaongkir = rajaongkir;
	}

	public Rajaongkir getRajaongkir(){
		return rajaongkir;
	}
}