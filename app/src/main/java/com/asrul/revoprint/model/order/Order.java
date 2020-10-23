package com.asrul.revoprint.model.order;

import com.google.gson.annotations.SerializedName;

public class Order{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private OrderData orderData;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setOrderData(OrderData orderData){
		this.orderData = orderData;
	}

	public OrderData getOrderData(){
		return orderData;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}