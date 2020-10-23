package com.asrul.revoprint.model.produk;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class Produk implements Parcelable {

	@SerializedName("data")
	private ArrayList<DataProduk> data;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	protected Produk(Parcel in) {
		error = in.readByte() != 0;
		message = in.readString();
		status = in.readInt();
	}

	public static final Creator<Produk> CREATOR = new Creator<Produk>() {
		@Override
		public Produk createFromParcel(Parcel in) {
			return new Produk(in);
		}

		@Override
		public Produk[] newArray(int size) {
			return new Produk[size];
		}
	};

	public void setData(ArrayList<DataProduk> data){
		this.data = data;
	}

	public ArrayList<DataProduk> getData(){
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (error ? 1 : 0));
		dest.writeString(message);
		dest.writeInt(status);
	}
}