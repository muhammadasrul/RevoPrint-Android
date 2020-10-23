package com.asrul.revoprint.model.produk;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_DESC;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_ID;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_IMAGE;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_JENIS;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_NAME;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_PRICE;
import static com.asrul.revoprint.database.DatabaseContract.getColumnString;

public class DataProduk implements Parcelable {

	@SerializedName("id_produk")
	private String idProduk;

	@SerializedName("kategori_id")
	private String kategoriId;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("nama")
	private String nama;

	@SerializedName("harga")
	private String harga;

	@SerializedName("gambar1")
	private String gambar1;

	@SerializedName("gambar3")
	private String gambar3;

	@SerializedName("gambar2")
	private String gambar2;

	@SerializedName("jenis")
	private String jenis;

	@SerializedName("deskripsi")
	private String deskripsi;

	public DataProduk(Parcel in) {
		this.idProduk = in.readString();
		this.kategoriId = in.readString();
		this.keterangan = in.readString();
		this.nama = in.readString();
		this.harga = in.readString();
		this.gambar1 = in.readString();
		this.gambar3 = in.readString();
		this.gambar2 = in.readString();
		this.jenis = in.readString();
		this.deskripsi = in.readString();
	}

	public DataProduk(Cursor cursor) {
		this.idProduk = getColumnString(cursor, PRODUCT_ID);
		this.nama = getColumnString(cursor, PRODUCT_NAME);
		this.harga = getColumnString(cursor, PRODUCT_PRICE);
		this.gambar1 = getColumnString(cursor, PRODUCT_IMAGE);
		this.deskripsi = getColumnString(cursor, PRODUCT_DESC);
		this.jenis = getColumnString(cursor, PRODUCT_JENIS);
	}

	public static final Creator<DataProduk> CREATOR = new Creator<DataProduk>() {
		@Override
		public DataProduk createFromParcel(Parcel in) {
			return new DataProduk(in);
		}

		@Override
		public DataProduk[] newArray(int size) {
			return new DataProduk[size];
		}
	};

	public DataProduk() {

	}

	public void setIdProduk(String idProduk){
		this.idProduk = idProduk;
	}

	public String getIdProduk(){
		return idProduk;
	}

	public void setKategoriId(String kategoriId){
		this.kategoriId = kategoriId;
	}

	public String getKategoriId(){
		return kategoriId;
	}

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setGambar1(String gambar1){
		this.gambar1 = gambar1;
	}

	public String getGambar1(){
		return gambar1;
	}

	public void setGambar3(String gambar3){
		this.gambar3 = gambar3;
	}

	public String getGambar3(){
		return gambar3;
	}

	public void setGambar2(String gambar2){
		this.gambar2 = gambar2;
	}

	public String getGambar2(){
		return gambar2;
	}

	public void setJenis(String jenis){
		this.jenis = jenis;
	}

	public String getJenis(){
		return jenis;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.idProduk);
		dest.writeString(this.kategoriId);
		dest.writeString(this.keterangan);
		dest.writeString(this.nama);
		dest.writeString(this.harga);
		dest.writeString(this.gambar1);
		dest.writeString(this.gambar3);
		dest.writeString(this.gambar2);
		dest.writeString(this.jenis);
		dest.writeString(this.deskripsi);
	}
}