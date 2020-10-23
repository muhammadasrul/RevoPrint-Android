package com.asrul.revoprint.api;

import com.asrul.revoprint.model.kategori.Kategori;
import com.asrul.revoprint.model.login.Login;
import com.asrul.revoprint.model.order.Order;
import com.asrul.revoprint.model.produk.Produk;
import com.asrul.revoprint.model.rajaongkir.city.City;
import com.asrul.revoprint.model.rajaongkir.cost.Cost;
import com.asrul.revoprint.model.rajaongkir.province.Province;
import com.asrul.revoprint.model.register.Register;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Call<Login> loginResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<Register> registerResponse(
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("produk")
    Call<Produk> getProduk();

    @GET("kategori")
    Call<Kategori> getKategori();

    @GET("kategori/{kategori_id}")
    Call<Produk> getProdukByKategoriId(
            @Path("kategori_id") int kategori_id
    );

    @Multipart
    @POST("order")
    Call<Order> postOrder(
            @Part MultipartBody.Part file_path,
            @PartMap Map<String, RequestBody> order
    );

    @GET("order")
    Call<Order> getorder();

    @Headers("key: 2dcb2554bc557679154673d32b879769")
    @GET("province")
    Call<Province> getProvince();

    @Headers("key: 2dcb2554bc557679154673d32b879769")
    @GET("city")
    Call<City> getCity (@Query("province") String province);

    @FormUrlEncoded
    @Headers({
            "key: 2dcb2554bc557679154673d32b879769",
            "content-type: application/x-www-form-urlencoded"})
    @POST("cost")
    Call<Cost> getCost(
            @Field("origin") String origin,
            @Field("destination") String destination,
            @Field("weight") String weight,
            @Field("courier") String courier
    );
}
