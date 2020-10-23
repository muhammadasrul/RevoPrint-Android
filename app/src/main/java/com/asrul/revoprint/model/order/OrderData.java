package com.asrul.revoprint.model.order;

import com.google.gson.annotations.SerializedName;

public class OrderData {

	@SerializedName("file_path")
	private String filePath;

	@SerializedName("total")
	private String total;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("notes")
	private String notes;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("address")
	private String address;

	@SerializedName("courier")
	private String courier;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public void setFilePath(String filePath){
		this.filePath = filePath;
	}

	public String getFilePath(){
		return filePath;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}
}