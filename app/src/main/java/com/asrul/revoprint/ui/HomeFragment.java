package com.asrul.revoprint.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.asrul.revoprint.R;
import com.asrul.revoprint.adapter.KategoriAdapter;
import com.asrul.revoprint.adapter.ProdukGridAdapter;
import com.asrul.revoprint.api.ApiClient;
import com.asrul.revoprint.api.ApiService;
import com.asrul.revoprint.model.kategori.DataKategori;
import com.asrul.revoprint.model.produk.DataProduk;
import com.asrul.revoprint.model.produk.Produk;
import com.asrul.revoprint.viewmodel.KategoriViewModel;
import com.asrul.revoprint.viewmodel.ProdukViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment{

    private RecyclerView rvProduk, rvKategori;
    private ProdukGridAdapter produkGridAdapter;
    private KategoriAdapter kategoriAdapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        rvProduk = view.findViewById(R.id.rv_produk);
        rvProduk.setHasFixedSize(true);
        rvProduk.setLayoutManager(new LinearLayoutManager(getContext()));

        rvKategori = view.findViewById(R.id.rv_category);
        rvKategori.setHasFixedSize(true);

        observeKategori();
        observeProdukGrid();
    }

    private void observeProdukGrid() {
        rvProduk.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ProdukViewModel produkViewModel = ViewModelProviders.of(this).get(ProdukViewModel.class);
        produkViewModel.getDataProduk().observe(getViewLifecycleOwner(), new Observer<List<DataProduk>>() {
            @Override
            public void onChanged(List<DataProduk> dataProduks) {
                produkGridAdapter = new ProdukGridAdapter(getContext(), dataProduks);
                rvProduk.setAdapter(produkGridAdapter);
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void observeKategori() {
        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvKategori.setLayoutManager(horizontalLayoutManager);
        KategoriViewModel kategoriViewModel = ViewModelProviders.of(this).get(KategoriViewModel.class);
        kategoriViewModel.getKategori().observe(getViewLifecycleOwner(), new Observer<List<DataKategori>>() {
            @Override
            public void onChanged(List<DataKategori> dataKategoris) {
                kategoriAdapter = new KategoriAdapter(getContext(), dataKategoris);
                rvKategori.setAdapter(kategoriAdapter);

                kategoriAdapter.setOnItemClickCallback(new KategoriAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(DataKategori dataKategori) {
                        progressBar.setVisibility(View.VISIBLE);
                        getProdukByKategoriId(dataKategori);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void getProdukByKategoriId(DataKategori dataKategori) {
        int kategori_id = Integer.parseInt(dataKategori.getIdKategori());
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<Produk> call = apiService.getProdukByKategoriId(kategori_id);

        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                List<DataProduk> produkList = response.body().getData();
                produkGridAdapter = new ProdukGridAdapter(getContext(), produkList);
                rvProduk.setLayoutManager(new GridLayoutManager(getContext(), 2));
                rvProduk.setAdapter(produkGridAdapter);
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}