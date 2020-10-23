package com.asrul.revoprint.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.asrul.revoprint.R;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {

    private TextView tvPay;
    public static final String EXTRA_PAY = "extra_pay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        tvPay = findViewById(R.id.pay);
        getOrder();
    }

    private void getOrder() {
        ArrayList<String> data = getIntent().getStringArrayListExtra(EXTRA_PAY);
        tvPay.setText("Jumlah barang yang dipesan = "+data.get(1) + ", total yang harus dibayar = " +data.get(0));
    }
}