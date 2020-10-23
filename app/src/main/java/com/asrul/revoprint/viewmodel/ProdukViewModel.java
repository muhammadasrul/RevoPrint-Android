package com.asrul.revoprint.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.asrul.revoprint.api.ApiClient;
import com.asrul.revoprint.api.ApiService;
import com.asrul.revoprint.model.produk.DataProduk;
import com.asrul.revoprint.model.produk.Produk;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukViewModel extends ViewModel {

    private MutableLiveData<List<DataProduk>> produkList;

    public LiveData<List<DataProduk>> getDataProduk() {
        if (produkList == null) {
            produkList = new MutableLiveData<>();
            loadProduk();
        }
        return produkList;
    }

    private void loadProduk() {

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<Produk> call = apiService.getProduk();

        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                produkList.setValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {

            }
        });
    }
}
