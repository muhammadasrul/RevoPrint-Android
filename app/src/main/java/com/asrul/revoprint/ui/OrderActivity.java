package com.asrul.revoprint.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.asrul.revoprint.R;
import com.asrul.revoprint.SessionManager;
import com.asrul.revoprint.api.ApiClient;
import com.asrul.revoprint.api.ApiService;
import com.asrul.revoprint.model.order.Order;
import com.asrul.revoprint.model.produk.DataProduk;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ORDER = "extra_order";
    public static final String EXTRA_ADDRESS = "address";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int ALL_FILE_REQUEST = 102;
    private DataProduk dataProduk;
    private TextView tvTotal, tvAddress, tvCourier, tvTime;
    private EditText edtAmount, edtUpload, edtNotes;
    private ImageView btnDecrease;
    private ArrayList<String> address;
    private Button btnBeli, btnUpload;
    private ProgressBar progressBar;
    String file_path=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setTitle("Barang yang dipesan");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dataProduk = getIntent().getParcelableExtra(EXTRA_ORDER);
        address = getIntent().getStringArrayListExtra(EXTRA_ADDRESS);

        btnBeli = findViewById(R.id.btn_bayar);
        btnBeli.setOnClickListener(this);
        btnUpload = findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(this);
        tvAddress = findViewById(R.id.tv_address);
        tvCourier = findViewById(R.id.tv_courier);
        tvTime = findViewById(R.id.tv_time);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvPrice = findViewById(R.id.tv_price);
        edtAmount = findViewById(R.id.edt_amount);
        tvTotal = findViewById(R.id.tv_total);
        btnDecrease = findViewById(R.id.btn_decrease);
        ImageView btnIncrease = findViewById(R.id.btn_increase);
        ImageView imgProduct = findViewById(R.id.img_product);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        edtUpload = findViewById(R.id.edt_upload);
        progressBar = findViewById(R.id.progress_bar);
        edtNotes = findViewById(R.id.edt_notes);

        tvAddress.setTag("");
        tvCourier.setTag("");
        tvTime.setTag("");

        tvName.setText(dataProduk.getNama());
        tvPrice.setText(dataProduk.getHarga());
        tvTotal.setText(dataProduk.getHarga());

        if (address != null) {
            tvAddress.setText(address.get(0) + ", " + address.get(1) + ", " + address.get(2));
            tvAddress.setTag(address.get(0));
            tvCourier.setText(address.get(3)+" "+address.get(4));
            tvCourier.setTag(address.get(3));
            tvTime.setText(address.get(6) + " hari" + " (Rp "+address.get(5) +")");
            int total = Integer.parseInt(String.valueOf(dataProduk.getHarga()))+Integer.parseInt(address.get(5));
            tvTotal.setText(String.valueOf(total));
        }

        Glide.with(this)
                .load(dataProduk.getGambar1())
                .placeholder(R.color.colorPrimaryDark)
                .into(imgProduct);

        CardView cvAddress = findViewById(R.id.cv_address);
        cvAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int awal = Integer.parseInt(String.valueOf(edtAmount.getText()));
        int akhir;
        int harga = Integer.parseInt(String.valueOf(dataProduk.getHarga()));
        int total;

        switch (v.getId()) {
            case R.id.btn_decrease:
                if (awal > 1) {
                    akhir = awal - 1;
                    edtAmount.setText(String.valueOf(akhir));
                    total = akhir*harga;
                    tvTotal.setText(String.valueOf(total));
                    if (address != null) {
                        int ongkir = Integer.parseInt(address.get(5));
                        total = akhir*harga+ongkir;
                        tvTotal.setText(String.valueOf(total));
                    }
                }
                break;
            case R.id.btn_increase:
                akhir = awal + 1;
                edtAmount.setText(String.valueOf(akhir));
                total = akhir*harga;
                tvTotal.setText(String.valueOf(total));
                if (address != null) {
                    int ongkir = Integer.parseInt(address.get(5));
                    total = akhir*harga+ongkir;
                    tvTotal.setText(String.valueOf(total));
                }
                btnDecrease.setBackgroundResource(R.drawable.ic_decrease_circle);
                break;
            case R.id.cv_address:
                Intent intent = new Intent(OrderActivity.this, AddressActivity.class);
                intent.putExtra(AddressActivity.EXTRA_DATA, dataProduk);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_upload:
                if (Build.VERSION.SDK_INT>=23) {
                    if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        filePicker();
                    } else {
                        requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                } else {
                    filePicker();
                }
                break;
            case R.id.btn_bayar:
                if (tvAddress.getTag().toString().isEmpty() && tvCourier.getTag().toString().isEmpty()) {
                    Toast.makeText(this, "Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (edtUpload.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Pilih file terlebih dahulu!", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
                Intent i = new Intent(OrderActivity.this, PayActivity.class);
                ArrayList<String> data = new ArrayList<>();
                data.add(tvTotal.getText().toString());
                data.add(edtAmount.getText().toString());
                i.putExtra(PayActivity.EXTRA_PAY, data);
                startActivity(i);
                finish();
                break;
        }
    }

    private void filePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Choose file to upload"), ALL_FILE_REQUEST);
    }

    private boolean checkPermission(String permission) {
        int result = ContextCompat.checkSelfPermission(OrderActivity.this, permission);
        if (result== PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    private void requestPermissions(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(OrderActivity.this, permission)) {
            Toast.makeText(this, "Please allow permission", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(OrderActivity.this, new String[]{permission}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    filePicker();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ALL_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            String selectedFile = getRealPathFromUri(data.getData(), OrderActivity.this);
            this.file_path=selectedFile;
            File file=new File(selectedFile);
            edtUpload.setText(file.getName());
        }
    }

    private void uploadFile() {
        UploadTask uploadTask = new UploadTask();
        uploadTask.execute(new String[]{file_path});
    }

    public class UploadTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (upload(strings[0])){
                return "true";
            } else {
                return "failed";
            }
        }

        private boolean upload(String path) {
            File file = new File(path);
            try {
                SessionManager sessionManager = new SessionManager(getApplicationContext());

                String order_id = "";
                String product_id = dataProduk.getIdProduk();
                String user_id = sessionManager.getUserDetail().get(SessionManager.ID);
                String quantity = edtAmount.getText().toString();
                String total = tvTotal.getText().toString();
                String notes = edtNotes.getText().toString();
                String address = tvAddress.getText().toString();
                String courier = tvCourier.getText().toString();
                MultipartBody.Part part = MultipartBody.Part.createFormData("file_path", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

                HashMap<String, RequestBody> order = new HashMap<>();
                order.put("order_id", createPartFromString(order_id));
                order.put("product_id", createPartFromString(product_id));
                order.put("user_id", createPartFromString(user_id));
                order.put("quantity", createPartFromString(quantity));
                order.put("total", createPartFromString(total));
                order.put("notes", createPartFromString(notes));
                order.put("address", createPartFromString(address));
                order.put("courier", createPartFromString(courier));

                ApiService service = ApiClient.getRetrofit().create(ApiService.class);
                Call<Order> call = service.postOrder(part, order);

                call.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {

                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    private RequestBody createPartFromString(String orderString) {
        return RequestBody.create(MultipartBody.FORM, orderString);
    }

    private String getRealPathFromUri (Uri uri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor==null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int id=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
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