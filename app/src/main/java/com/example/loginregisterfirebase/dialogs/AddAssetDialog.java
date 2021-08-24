package com.example.loginregisterfirebase.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.logic.Asset;
import com.example.loginregisterfirebase.managers.DatabaseManager;

public class AddAssetDialog extends DialogFragment {

    EditText asset_name_et;
    EditText asset_type_et;
    EditText asset_value_et;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_add_asset, null))
                .setTitle(R.string.add_asset_btn)
                .setPositiveButton(R.string.done_btn, (dialog, which) -> {
                    asset_name_et = ((Dialog) dialog).findViewById(R.id.asset_name_et);
                    asset_type_et = ((Dialog) dialog).findViewById(R.id.asset_type_et);
                    asset_value_et = ((Dialog) dialog).findViewById(R.id.asset_value_et);

                    Asset a = new Asset(
                            asset_name_et.getText().toString(),
                            asset_type_et.getText().toString(),
                            Double.parseDouble(asset_value_et.getText().toString())
                    );

                    DatabaseManager.getInstance().addNewAsset(a);

                })
                .setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddAssetDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
