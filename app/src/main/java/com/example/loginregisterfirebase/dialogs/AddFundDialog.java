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
import com.example.loginregisterfirebase.logic.Fund;
import com.example.loginregisterfirebase.managers.DatabaseManager;

public class AddFundDialog extends DialogFragment {
    EditText fund_name_et;
    EditText fund_company_et;
    EditText fund_value_et;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_add_fund, null))
                .setTitle(R.string.add_asset_btn)
                .setPositiveButton(R.string.done_btn, (dialog, which) -> {
                    fund_name_et = ((Dialog) dialog).findViewById(R.id.fund_name_et);
                    fund_company_et = ((Dialog) dialog).findViewById(R.id.fund_company_et);
                    fund_value_et = ((Dialog) dialog).findViewById(R.id.fund_value_et);

                    Fund f = new Fund(
                            fund_name_et.getText().toString(),
                            fund_company_et.getText().toString(),
                            Double.parseDouble(fund_value_et.getText().toString())
                    );

                    DatabaseManager.getInstance().addNewFund(f);

                })
                .setNegativeButton(R.string.cancel_btn, (dialog, which) -> AddFundDialog.this.getDialog().cancel());
        return builder.create();
    }
}
