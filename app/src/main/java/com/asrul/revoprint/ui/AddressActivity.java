package com.asrul.revoprint.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.asrul.revoprint.model.rajaongkir.Common;
import com.asrul.revoprint.R;
import com.asrul.revoprint.adapter.rajaongkir.CityAdapter;
import com.asrul.revoprint.adapter.rajaongkir.CostAdapter;
import com.asrul.revoprint.adapter.rajaongkir.ExpedisiAdapter;
import com.asrul.revoprint.adapter.rajaongkir.ProvinceAdapter;
import com.asrul.revoprint.api.ApiClient;
import com.asrul.revoprint.api.ApiService;
import com.asrul.revoprint.model.produk.DataProduk;
import com.asrul.revoprint.model.rajaongkir.Expedisi;
import com.asrul.revoprint.model.rajaongkir.city.City;
import com.asrul.revoprint.model.rajaongkir.cost.Cost;
import com.asrul.revoprint.model.rajaongkir.cost.CostsItem;
import com.asrul.revoprint.model.rajaongkir.province.Province;
import com.asrul.revoprint.model.rajaongkir.province.ResultsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtProvince, edtCity, edtCourir, edtAddress;
    private Button btnSave;
    private ProgressBar progressBar;

    private AlertDialog.Builder alert;
    private AlertDialog alertDialog;
    private RecyclerView recyclerView;

    private ProvinceAdapter provinceAdapter;
    private List<ResultsItem> listProvince;

    private CityAdapter cityAdapter;
    private List<com.asrul.revoprint.model.rajaongkir.city.ResultsItem> listCity;

    private ArrayList<Expedisi> listExpedisi = new ArrayList<>();

    private CostAdapter costAdapter;
    private List<CostsItem> serviceList;
    private ProgressDialog progressDialog;

    public ArrayList<String> address = new ArrayList<>();

    public static final String EXTRA_DATA = "extra_data";
    private DataProduk dataProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        setTitle("Address");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dataProduk = getIntent().getParcelableExtra(EXTRA_DATA);

        edtProvince = findViewById(R.id.edt_province);
        edtProvince.setOnClickListener(this);
        edtCity = findViewById(R.id.edt_city);
        edtCity.setOnClickListener(this);
        edtCourir = findViewById(R.id.edt_courir);
        edtCourir.setOnClickListener(this);
        edtAddress = findViewById(R.id.edt_address);

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

        progressBar = findViewById(R.id.progress_bar);
    }

    public void popUpProvince() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(AddressActivity.this);
        alert.setTitle("Province");
        alert.setMessage("select your province");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        alertDialog = alert.show();

        recyclerView = (RecyclerView) alertLayout.findViewById(R.id.listItem);

        progressDialog = new ProgressDialog(AddressActivity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        getProvince();
    }

    public void popUpCity() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(AddressActivity.this);
        alert.setTitle("City");
        alert.setMessage("select your city");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        alertDialog = alert.show();

        recyclerView = (RecyclerView) alertLayout.findViewById(R.id.listItem);

        progressDialog = new ProgressDialog(AddressActivity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        getCity();
    }

    public void popUpCourier() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(AddressActivity.this);
        alert.setTitle("Courier");
        alert.setMessage("select your courier");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        alertDialog = alert.show();

        recyclerView = (RecyclerView) alertLayout.findViewById(R.id.listItem);

        listExpedisi.clear();
        listExpedisi.addAll(getCourier());

        ExpedisiAdapter expedisiAdapter = new ExpedisiAdapter(getApplicationContext(), listExpedisi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(expedisiAdapter);

        expedisiAdapter.setOnItemClickCallback(new ExpedisiAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Expedisi expedisi) {
                edtCourir.setText(expedisi.getName());
                alertDialog.dismiss();

                recyclerView = findViewById(R.id.rvService);
                progressBar.setVisibility(View.VISIBLE);
                getExpedisi();
            }
        });
    }

    private void getProvince() {
        ApiService service = ApiClient.getRajaOngkir().create(ApiService.class);
        Call<Province> call = service.getProvince();

        call.enqueue(new Callback<Province>() {
            @Override
            public void onResponse(Call<Province> call, Response<Province> response) {

                progressDialog.dismiss();

                listProvince = response.body().getRajaongkir().getResults();
                provinceAdapter = new ProvinceAdapter(getApplicationContext(), listProvince);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(provinceAdapter);

                provinceAdapter.setOnItemClickCallback(new ProvinceAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(ResultsItem province) {
                        edtProvince.setText(province.getProvince());
                        edtProvince.setTag(province.getProvinceId());
                        alertDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(Call<Province> call, Throwable t) {
                Toast.makeText(AddressActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure :", t.getLocalizedMessage());
            }
        });

    }

    private void getCity() {
        String province_id = String.valueOf(edtProvince.getTag());
        ApiService service = ApiClient.getRajaOngkir().create(ApiService.class);
        Call<City> call = service.getCity(province_id);

        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {

                progressDialog.dismiss();

                listCity = response.body().getRajaongkir().getResults();
                cityAdapter = new CityAdapter(getApplicationContext(), listCity);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(cityAdapter);

                cityAdapter.setOnItemClickCallback(new CityAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(com.asrul.revoprint.model.rajaongkir.city.ResultsItem city) {
                        edtCity.setText(city.getCityName());
                        edtCity.setTag(city.getCityId());
                        alertDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(AddressActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure :", t.getLocalizedMessage());
            }
        });
    }

    private ArrayList<Expedisi> getCourier() {

        ArrayList<Expedisi> listExpesisi = new ArrayList<>();
        String[] dataCourier = getResources().getStringArray(R.array.data_courier);
        for (String s : dataCourier) {
            Expedisi expedisi = new Expedisi();
            expedisi.setName(s);
            listExpesisi.add(expedisi);
        }
        return listExpesisi;
    }

    private void getExpedisi() {
        String origin = "398";
        String destination = String.valueOf(edtCity.getTag());
        String weight = "1000";
        String courier = edtCourir.getText().toString().toLowerCase();

        final ApiService service = ApiClient.getRajaOngkir().create(ApiService.class);
        Call<Cost> call = service.getCost(
                origin,
                destination,
                weight,
                courier
        );

        call.enqueue(new Callback<Cost>() {
            @Override
            public void onResponse(Call<Cost> call, Response<Cost> response) {
                progressBar.setVisibility(View.GONE);
                serviceList = response.body().getRajaongkir().getResults().get(0).getCosts();

                costAdapter = new CostAdapter(getApplicationContext(), serviceList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(costAdapter);

                costAdapter.setOnItemClickCallback(new CostAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(CostsItem service) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Cost> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_province:
                popUpProvince();
                edtCity.setText("");
                edtCity.setTag("");
                edtCourir.setText("");
                break;
            case R.id.edt_city:
                if (edtProvince.getText().toString().trim().isEmpty()) {
                    edtProvince.setError("Province is required");
                    edtProvince.requestFocus();
                    return;
                }
                popUpCity();
                break;
            case R.id.edt_courir:
                if (edtCity.getText().toString().trim().isEmpty()) {
                    edtCity.setError("City is required");
                    edtCity.requestFocus();
                    return;
                }
                if (edtProvince.getText().toString().trim().isEmpty()) {
                    edtProvince.setError("Province is required");
                    edtProvince.requestFocus();
                    return;
                }
                popUpCourier();
                break;
            case R.id.btn_save:

                if (edtCity.getText().toString().trim().isEmpty()) {
                    edtCity.setError("City is required");
                    edtCity.requestFocus();
                    return;
                }
                if (edtProvince.getText().toString().trim().isEmpty()) {
                    edtProvince.setError("Province is required");
                    edtProvince.requestFocus();
                    return;
                }
                if (edtAddress.getText().toString().trim().isEmpty()) {
                    edtAddress.setError("Address is required");
                    edtAddress.requestFocus();
                    return;
                }
                if (edtCourir.getText().toString().trim().isEmpty()) {
                    edtCourir.setError("Courier is required");
                    edtCourir.requestFocus();
                    return;
                }

                if (Common.currentItem == null) {
                    Toast.makeText(this, "Courier is required", Toast.LENGTH_SHORT).show();
                } else {
                    String service = String.valueOf(Common.currentItem.getService());
                    String value = String.valueOf(Common.currentItem.getCost().get(0).getValue());
                    String etd = String.valueOf(Common.currentItem.getCost().get(0).getEtd());

                    address.add(edtAddress.getText().toString());
                    address.add(edtCity.getText().toString());
                    address.add(edtProvince.getText().toString());
                    address.add(edtCourir.getText().toString());
                    address.add(service);
                    address.add(value);
                    address.add(etd);

                    Intent intent = new Intent(AddressActivity.this, OrderActivity.class);
                    intent.putStringArrayListExtra(OrderActivity.EXTRA_ADDRESS, address);
                    intent.putExtra(OrderActivity.EXTRA_ORDER, dataProduk);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}