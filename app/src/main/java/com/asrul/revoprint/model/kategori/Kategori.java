package com.asrul.revoprint.model.kategori;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Kategori{

	@SerializedName("data")
	private List<DataKategori> data;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<DataKategori> data){
		this.data = data;
	}

	public List<DataKategori> getData(){
		return data;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}