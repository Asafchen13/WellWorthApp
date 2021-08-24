package com.example.loginregisterfirebase.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.fragments.CryptoCurrenciesFragment;
import com.example.loginregisterfirebase.logic.Cryptocurrency;
import com.example.loginregisterfirebase.managers.CryptoAPIManager;
import com.example.loginregisterfirebase.managers.DatabaseManager;

public class EditCoinDialog extends DialogFragment {
    private EditText coin_new_name_et, coins_new_amount_et;
    private TextView coin_type_lbl;

    private String old_coin_name, coin_type, old_coin_amount;

    private CryptoCurrenciesFragment.apiRequestExecutor executor;
    private Handler handler = new Handler(Looper.myLooper());

    private CryptoAPIManager apiManager;

    public void setOld_coin_name(String old_coin_name) {
        this.old_coin_name = old_coin_name;
    }

    public void setCoin_type(String coin_type) {
        this.coin_type = coin_type;
    }

    public void setOld_coin_amount(String old_coin_amount) {
        this.old_coin_amount = old_coin_amount;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        executor = new CryptoCurrenciesFragment.apiRequestExecutor();

        apiManager= new CryptoAPIManager(executor, handler, getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_edit_coin, null);
        builder.setView(dialogView);
        coin_new_name_et = dialogView.findViewById(R.id.coin_new_name_et);
        coins_new_amount_et = dialogView.findViewById(R.id.coins_new_amount_et);
        coin_type_lbl = dialogView.findViewById(R.id.coin_type_lbl);

        coin_new_name_et.setText(this.old_coin_name);
        coins_new_amount_et.setText(this.old_coin_amount);
        coin_type_lbl.setText(this.coin_type);

        builder.setTitle(R.string.add_coin_btn)
                .setPositiveButton(R.string.done_btn, (dialog, which) -> {
                    Cryptocurrency c = new Cryptocurrency(
                            coin_type_lbl.getText().toString(),
                            coin_new_name_et.getText().toString(),
                            Double.parseDouble(coins_new_amount_et.getText().toString())
                    );

                    apiManager.makeCoinDataRequest(c, map -> {
                        if (map != null) {
                            c.setPriceUsd(Double.parseDouble((String) map.get(CryptoAPIManager.PRICE)));
                            c.setChangePercent24Hr(Double.parseDouble((String) map.get(CryptoAPIManager.CHANGE_PERCENT)));

                            DatabaseManager.getInstance().editCryptocurrency(c);
                        } else {
                            apiManager.makeCoinDataRequest(c, m -> {
                                if (map != null) {
                                    c.setPriceUsd(Double.parseDouble((String) m.get(CryptoAPIManager.PRICE)));
                                    c.setChangePercent24Hr(Double.parseDouble((String) m.get(CryptoAPIManager.CHANGE_PERCENT)));

                                    DatabaseManager.getInstance().editCryptocurrency(c);
                                } else {

                                }
                            });
                        }
                    });
                }).setNegativeButton(R.string.cancel_btn, (dialog, which) -> EditCoinDialog.this.getDialog().cancel());


        return builder.create();
    }
}
