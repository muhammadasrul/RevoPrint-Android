package com.asrul.revoprint.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asrul.revoprint.R;
import com.asrul.revoprint.database.ProductHelper;
import com.asrul.revoprint.model.produk.DataProduk;
import com.bumptech.glide.Glide;

import java.util.Objects;

import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_DESC;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_ID;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_IMAGE;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_JENIS;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_NAME;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_PRICE;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_URI;

public class ProdukDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_PRODUK = "extra_produk";

    private TextView tvName, tvDesc, tvPrice, tvJenis;
    private ImageView imgProduct, btnFav, btnDel;
    private Button btnPesan;
    private DataProduk dataProduk;
    private ProductHelper productHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        dataProduk = getIntent().getParcelableExtra(EXTRA_PRODUK);

        productHelper = ProductHelper.getInstance(getApplicationContext());
        productHelper.open();

        tvName = findViewById(R.id.tv_name);
        tvDesc = findViewById(R.id.tv_desc);
        tvJenis = findViewById(R.id.tv_jenis);
        tvPrice = findViewById(R.id.tv_price);
        imgProduct = findViewById(R.id.img_product);
        btnFav = findViewById(R.id.img_fav);
        btnDel = findViewById(R.id.img_fav_active);
        btnPesan = findViewById(R.id.btn_pesan);

        btnFav.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnPesan.setOnClickListener(this);

        if (productHelper.checkProduct(dataProduk.getIdProduk())) {
            btnFav.setVisibility(View.GONE);
            btnDel.setVisibility(View.VISIBLE);
        }

        getProductDetail();
    }

    private void getProductDetail() {

        setTitle("Detail Produk");
        tvName.setText(dataProduk.getNama());
        tvDesc.setText(dataProduk.getDeskripsi());
        tvPrice.setText(dataProduk.getHarga());
        tvJenis.setText(dataProduk.getJenis());

        Glide.with(this)
                .load(dataProduk.getGambar1())
                .placeholder(R.color.colorPrimaryDark)
                .into(imgProduct);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fav:
                addToFav();
                btnFav.setVisibility(View.GONE);
                btnDel.setVisibility(View.VISIBLE);
                Toast.makeText(this, "added to favorites", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_fav_active:
                deleteFromFav();
                btnDel.setVisibility(View.GONE);
                btnFav.setVisibility(View.VISIBLE);
                Toast.makeText(this, "deleted from favorites", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_pesan:
                Intent intent = new Intent(this, OrderActivity.class);
                intent.putExtra(OrderActivity.EXTRA_ORDER, dataProduk);
                startActivity(intent);
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

    public void addToFav() {
        ContentValues values = new ContentValues();
        values.put(PRODUCT_ID, dataProduk.getIdProduk());
        values.put(PRODUCT_NAME, dataProduk.getNama());
        values.put(PRODUCT_PRICE, dataProduk.getHarga());
        values.put(PRODUCT_IMAGE, dataProduk.getGambar1());
        values.put(PRODUCT_DESC, dataProduk.getDeskripsi());
        values.put(PRODUCT_JENIS, dataProduk.getJenis());

        getContentResolver().insert(PRODUCT_URI, values);
    }

    private void deleteFromFav() {
        getContentResolver().delete(Uri.parse(PRODUCT_URI + "/" + dataProduk.getIdProduk()), null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productHelper.close();
    }
}