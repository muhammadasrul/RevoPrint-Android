package com.asrul.revoprint.adapter.rajaongkir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asrul.revoprint.R;
import com.asrul.revoprint.model.rajaongkir.Expedisi;

import java.util.ArrayList;
public class ExpedisiAdapter extends RecyclerView.Adapter<ExpedisiAdapter.ExpedisiViewHolder> {

    private Context mCtx;
    private ArrayList<Expedisi> expedisiList;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public ExpedisiAdapter(Context mCtx, ArrayList<Expedisi> expedisiList) {
        this.mCtx = mCtx;
        this.expedisiList = expedisiList;
    }

    @NonNull
    @Override
    public ExpedisiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.ongkir_item, parent, false);
        return new ExpedisiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpedisiViewHolder holder, int position) {
        final Expedisi expedisi = expedisiList.get(position);
        holder.tvName.setText(expedisi.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(expedisi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expedisiList.size();
    }

    public static class ExpedisiViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public ExpedisiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Expedisi expedisi);
    }
}