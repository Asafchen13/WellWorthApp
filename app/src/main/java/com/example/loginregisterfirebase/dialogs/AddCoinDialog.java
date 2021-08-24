package com.example.loginregisterfirebase.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.fragments.CryptoCurrenciesFragment;
import com.example.loginregisterfirebase.logic.Cryptocurrency;
import com.example.loginregisterfirebase.managers.CryptoAPIManager;
import com.example.loginregisterfirebase.managers.DatabaseManager;

import java.util.Map;

public class AddCoinDialog extends DialogFragment {

    EditText coin_name_et;
    Spinner coin_type_spinner;
    EditText coins_amount_et;

    CryptoCurrenciesFragment.apiRequestExecutor executor;
    Handler handler = new Handler(Looper.myLooper());

    CryptoAPIManager apiManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        executor = new CryptoCurrenciesFragment.apiRequestExecutor();

        apiManager= new CryptoAPIManager(executor, handler, getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_add_coin, null))
                .setTitle(R.string.add_coin_btn)
                .setPositiveButton(R.string.done_btn, (dialog, which) -> {
                    // add the coin
                    coin_name_et = ((Dialog) dialog).findViewById(R.id.coin_name_et);
                    coin_type_spinner = ((Dialog) dialog).findViewById(R.id.coin_type_spinner);
                    coins_amount_et = ((Dialog) dialog).findViewById(R.id.coins_amount_et);



                    Cryptocurrency c = new Cryptocurrency(
                            coin_type_spinner.getSelectedItem().toString(),
                            coin_name_et.getText().toString(),
                            Double.parseDouble(coins_amount_et.getText().toString())
                    );

                    apiManager.makeCoinDataRequest(c, map -> {
                        if (map != null) {
                            c.setPriceUsd(Double.parseDouble((String) map.get(CryptoAPIManager.PRICE)));
                            c.setChangePercent24Hr(Double.parseDouble((String) map.get(CryptoAPIManager.CHANGE_PERCENT)));

                            DatabaseManager.getInstance().addNewCryptocurrency(c);
                        } else {
                            apiManager.makeCoinDataRequest(c, m -> {
                                if (map != null) {
                                    c.setPriceUsd(Double.parseDouble((String) m.get(CryptoAPIManager.PRICE)));
                                    c.setChangePercent24Hr(Double.parseDouble((String) m.get(CryptoAPIManager.CHANGE_PERCENT)));

                                    DatabaseManager.getInstance().addNewCryptocurrency(c);
                                } else {

                                }
                            });
                        }
                    });
                }). setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddCoinDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}
