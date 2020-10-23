package com.asrul.revoprint.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.asrul.revoprint.R;
import com.asrul.revoprint.model.produk.DataProduk;
import com.bumptech.glide.Glide;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private Cursor cursor;
    private Context mCtx;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public FavoriteAdapter(Context context) {
        this.mCtx = context;
    }

    public void setProductCursor(Cursor listCursor) {
        this.cursor = listCursor;
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.produk_item, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteAdapter.FavoriteHolder holder, int position) {
        final DataProduk produk = getItem(position);

        holder.tvName.setText(produk.getNama());
        holder.tvPrice.setText(produk.getHarga());
        Glide.with(mCtx)
                .load(produk.getGambar1())
                .placeholder(R.color.colorPrimaryDark)
                .into(holder.imgProduct);
        holder.cvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(produk);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    private DataProduk getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Invalid position");
        }
        return new DataProduk(cursor);
    }

    public static class FavoriteHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvName, tvPrice;
        private final CardView cvProduct;
        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);

            cvProduct = itemView.findViewById(R.id.cv_product);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(DataProduk dataProduk);
    }
}
