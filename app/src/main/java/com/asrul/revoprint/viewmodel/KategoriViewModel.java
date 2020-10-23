package com.asrul.revoprint.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.asrul.revoprint.api.ApiClient;
import com.asrul.revoprint.api.ApiService;
import com.asrul.revoprint.model.kategori.DataKategori;
import com.asrul.revoprint.model.kategori.Kategori;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriViewModel extends ViewModel {
    public MutableLiveData<List<DataKategori>> kategoriList;

    public LiveData<List<DataKategori>> getKategori() {
        if (kategoriList == null) {
            kategoriList = new MutableLiveData<>();
            loadKategori();
        }
        return kategoriList;
    }

    private void loadKategori() {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<Kategori> call = apiService.getKategori();

        call.enqueue(new Callback<Kategori>() {
            @Override
            public void onResponse(Call<Kategori> call, Response<Kategori> response) {
                kategoriList.setValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<Kategori> call, Throwable t) {

            }
        });
    }
}
