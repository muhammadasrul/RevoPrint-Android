package com.asrul.revoprint.adapter.rajaongkir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asrul.revoprint.model.rajaongkir.Common;
import com.asrul.revoprint.R;
import com.asrul.revoprint.model.rajaongkir.cost.CostsItem;

import java.util.List;

public class CostAdapter extends RecyclerView.Adapter<CostAdapter.CostViewHolder> {

    Context mCtx;
    List<CostsItem> serviceList;

    int row_index = -1;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public CostAdapter(Context mCtx, List<CostsItem> serviceList) {
        this.mCtx = mCtx;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public CostAdapter.CostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.service_item, parent, false);
        return new CostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CostAdapter.CostViewHolder holder, final int position) {
        final CostsItem service = serviceList.get(position);

        holder.tvValue.setText("Rp " + service.getCost().get(0).getValue());
        holder.tvEtd.setText(service.getCost().get(0).getEtd()+" hari");
        holder.tvService.setText(service.getService());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onItemClickCallback.onItemClicked(service);
                    row_index = position;
                    Common.currentItem = service;
                    notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class CostViewHolder extends RecyclerView.ViewHolder {
        private TextView tvService, tvValue, tvEtd;
        private RadioButton radioButton;
        public CostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvService = itemView.findViewById(R.id.tv_service);
            tvValue = itemView.findViewById(R.id.tv_value);
            tvEtd = itemView.findViewById(R.id.tv_edt);
            radioButton = itemView.findViewById(R.id.radio);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(CostsItem service);
    }
}
