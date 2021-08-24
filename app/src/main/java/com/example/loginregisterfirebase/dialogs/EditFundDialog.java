package com.example.loginregisterfirebase.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.logic.Asset;
import com.example.loginregisterfirebase.logic.Fund;
import com.example.loginregisterfirebase.managers.DatabaseManager;

public class EditFundDialog extends DialogFragment {

    EditText fund_new_name_et;
    EditText fund_new_company_et;
    EditText fund_new_value_et;

    private String old_fund_name;
    private String old_fund_company;
    private String old_fund_value;

    public void setOld_fund_name(String old_fund_name) {
        this.old_fund_name = old_fund_name;
    }

    public void setOld_fund_company(String old_fund_company) {
        this.old_fund_company = old_fund_company;
    }

    public void setOld_fund_value(String old_fund_value) {
        this.old_fund_value = old_fund_value;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_edit_fund, null);
        builder.setView(dialogView);

        fund_new_name_et = dialogView.findViewById(R.id.fund_new_name_et);
        fund_new_company_et = dialogView.findViewById(R.id.fund_new_company_et);
        fund_new_value_et = dialogView.findViewById(R.id.fund_new_value_et);

        fund_new_name_et.setText(this.old_fund_name);
        fund_new_company_et.setText(this.old_fund_company);
        fund_new_value_et.setText(this.old_fund_value);


        builder.setTitle(R.string.edit_btn)
                .setPositiveButton(R.string.done_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fund oldFund = new Fund(
                                old_fund_name,
                                old_fund_company,
                                Double.parseDouble(old_fund_value)
                        );
                        Fund newFund = new Fund(
                                fund_new_name_et.getText().toString(),
                                fund_new_company_et.getText().toString(),
                                Double.parseDouble(fund_new_value_et.getText().toString())
                        );

                        DatabaseManager.getInstance().updateFund(oldFund, newFund);

                    }
                })
                .setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditFundDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
