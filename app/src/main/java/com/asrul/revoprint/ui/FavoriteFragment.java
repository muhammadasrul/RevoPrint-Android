package com.asrul.revoprint.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.asrul.revoprint.R;
import com.asrul.revoprint.adapter.FavoriteAdapter;
import com.asrul.revoprint.database.ProductHelper;
import com.asrul.revoprint.model.produk.DataProduk;

import java.util.Objects;

import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_URI;

public class FavoriteFragment extends Fragment {

    private FavoriteAdapter adapter;
    private ProductHelper productHelper;
    private Cursor cursorProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorite");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvProduk = view.findViewById(R.id.rv_fav);
        rvProduk.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProduk.setHasFixedSize(true);

        productHelper = ProductHelper.getInstance(getContext());
        productHelper.open();

        adapter = new FavoriteAdapter(getContext());
        rvProduk.setAdapter(adapter);

        adapter.setOnItemClickCallback(new FavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DataProduk dataProduk) {
                Intent intent = new Intent(getContext(), ProdukDetailActivity.class);
                intent.putExtra(ProdukDetailActivity.EXTRA_PRODUK, dataProduk);
                startActivity(intent);
            }
        });

        adapter.setProductCursor(cursorProduct);
        new LoadProductAsync().execute();
    }

    private class LoadProductAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            productHelper.open();
            return Objects.requireNonNull(getContext()).getContentResolver().query(
                    PRODUCT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            cursorProduct = cursor;
            adapter.setProductCursor(cursorProduct);
            adapter.notifyDataSetChanged();
        }
    }
}