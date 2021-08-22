package com.example.loginregisterfirebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.logic.Asset;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AssetsAdapter extends RecyclerView.Adapter<AssetsAdapter.AssetsVH>{

    private List<Asset> assets;
    private Context context;
    public AssetsAdapter(List<Asset> assets, Context context) {
        this.assets = assets;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AssetsVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.asset_rv_item, parent, false);
        return new AssetsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AssetsAdapter.AssetsVH holder, int position) {
        Asset asset = this.assets.get(position);
        holder.name_tv.setText(asset.getName());
        holder.type_tv.setText(asset.getType());
        holder.value_tv.setText(String.valueOf(asset.getValue()));


    }

    @Override
    public int getItemCount() {
        return this.assets.size();
    }

    public class AssetsVH extends RecyclerView.ViewHolder {
        TextView name_tv, type_tv, value_tv;

        Button delete_btn, edit_btn;
        public AssetsVH(@NonNull @NotNull View itemView) {
            super(itemView);
            this.name_tv = itemView.findViewById(R.id.asset_name_tv);
            this.type_tv = itemView.findViewById(R.id.asset_type_tv);
            this.value_tv = itemView.findViewById(R.id.asset_value_tv);

            this.delete_btn = itemView.findViewById(R.id.delete_asset_btn);
            this.edit_btn = itemView.findViewById(R.id.edit_asset_btn);
        }
    }

}
