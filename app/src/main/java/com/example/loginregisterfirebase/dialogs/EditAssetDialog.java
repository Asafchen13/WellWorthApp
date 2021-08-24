package com.example.loginregisterfirebase.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.logic.Asset;
import com.example.loginregisterfirebase.managers.DatabaseManager;

public class EditAssetDialog extends DialogFragment {
    private EditText asset_new_name_et;
    private EditText asset_new_type_et;
    private EditText asset_new_value_et;

    private String old_asset_name;
    private String old_asset_type;
    private String old_asset_value;

    public void setOld_asset_name(String old_asset_name) {
        this.old_asset_name = old_asset_name;
    }

    public void setOld_asset_type(String old_asset_type) {
        this.old_asset_type = old_asset_type;
    }

    public void setOld_asset_value(String old_asset_value) {
        this.old_asset_value = old_asset_value;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_edit_asset, null);
        builder.setView(dialogView);

        asset_new_name_et = dialogView.findViewById(R.id.asset_new_name_et);
        asset_new_type_et = dialogView.findViewById(R.id.asset_new_type_et);
        asset_new_value_et = dialogView.findViewById(R.id.asset_new_value_et);

        asset_new_name_et.setText(this.old_asset_name);
        asset_new_type_et.setText(this.old_asset_type);
        asset_new_value_et.setText(this.old_asset_value);


        builder.setTitle(R.string.edit_btn)
                .setPositiveButton(R.string.done_btn, (dialog, which) -> {
                    Asset oldAsset = new Asset(
                            old_asset_name,
                            old_asset_type,
                            Double.parseDouble(old_asset_value)
                    );
                    Asset newAsset = new Asset(
                            asset_new_name_et.getText().toString(),
                            asset_new_type_et.getText().toString(),
                            Double.parseDouble(asset_new_value_et.getText().toString())
                    );

                    DatabaseManager.getInstance().updateAsset(oldAsset, newAsset);

                })
                .setNegativeButton(R.string.cancel_btn, (dialog, which) -> EditAssetDialog.this.getDialog().cancel());
        return builder.create();
    }
}
