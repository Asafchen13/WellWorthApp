package com.example.loginregisterfirebase.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.dialogs.EditAssetDialog;
import com.example.loginregisterfirebase.interfaces.mClickListener;
import com.example.loginregisterfirebase.logic.Asset;
import com.example.loginregisterfirebase.managers.DatabaseManager;

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
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_asset, parent, false);

        AssetsVH assetsVH = new AssetsVH(view, new mClickListener() {
            @Override
            public void onDelete(int p) {
                new AlertDialog.Builder(parent.getContext())
                        .setTitle("Title")
                        .setMessage("Are you sure?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Asset a = assets.get(p);
                                DatabaseManager.getInstance().deleteAsset(a);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }

            @Override
            public void onEdit(int p) {
                Asset a = assets.get(p);
                EditAssetDialog editAssetDialog = new EditAssetDialog();
                editAssetDialog.setOld_asset_name(a.getName());
                editAssetDialog.setOld_asset_type(a.getType());
                editAssetDialog.setOld_asset_value(String.valueOf(a.getValue()));
                editAssetDialog.setCancelable(true);
                editAssetDialog.show(((AppCompatActivity)parent.getContext()).getSupportFragmentManager(), "edit asset dialog");
            }
        });
        return assetsVH;
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

    public class AssetsVH extends RecyclerView.ViewHolder implements View.OnClickListener{

        private mClickListener listener;
        private TextView name_tv, type_tv, value_tv;

        Button delete_btn, edit_btn;
        public AssetsVH(@NonNull @NotNull View itemView, mClickListener listener) {
            super(itemView);
            this.listener = listener;

            name_tv = itemView.findViewById(R.id.asset_name_tv);
            type_tv = itemView.findViewById(R.id.asset_type_tv);
            value_tv = itemView.findViewById(R.id.asset_value_tv);
            delete_btn = itemView.findViewById(R.id.delete_asset_btn);
            edit_btn = itemView.findViewById(R.id.edit_asset_btn);

            delete_btn.setOnClickListener(this);
            edit_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_asset_btn:
                    listener.onDelete(this.getLayoutPosition());
                    break;
                case R.id.edit_asset_btn:
                    listener.onEdit(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }
    }

}
