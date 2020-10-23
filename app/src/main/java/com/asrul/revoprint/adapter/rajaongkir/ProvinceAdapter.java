package com.asrul.revoprint.adapter.rajaongkir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asrul.revoprint.R;
import com.asrul.revoprint.model.rajaongkir.province.ResultsItem;

import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder> {

    private Context mCtx;
    private List<ResultsItem> provinceList;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public ProvinceAdapter(Context mCtx, List<ResultsItem> provinceList) {
        this.mCtx = mCtx;
        this.provinceList = provinceList;
    }

    @NonNull
    @Override
    public ProvinceAdapter.ProvinceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.ongkir_item, parent, false);
        return new ProvinceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProvinceAdapter.ProvinceViewHolder holder, int position) {
        final ResultsItem provinceItems = provinceList.get(position);

        holder.tvName.setText(provinceItems.getProvince());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(provinceItems);
            }
        });
    }

    @Override
    public int getItemCount() {
        return provinceList.size();
    }

    public static class ProvinceViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public ProvinceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(ResultsItem province);
    }
}
