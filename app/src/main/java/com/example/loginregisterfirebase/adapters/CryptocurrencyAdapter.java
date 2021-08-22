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
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregisterfirebase.HomePage;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.fragments.CryptoCurrenciesFragment;
import com.example.loginregisterfirebase.logic.Cryptocurrency;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CryptocurrencyAdapter extends RecyclerView.Adapter<CryptocurrencyAdapter.CryptocurrencyVH> {

    private static DecimalFormat df = new DecimalFormat("#.##");
    private ArrayList<Cryptocurrency> cryptocurrencies;
    private Context context;
    private ViewGroup parent;

    public CryptocurrencyAdapter(ArrayList<Cryptocurrency> cryptocurrencies, Context context) {
        this.cryptocurrencies = cryptocurrencies;
        this.context = context;

    }

    public void filterList(ArrayList<Cryptocurrency> cryptocurrencies) {
        this.cryptocurrencies = cryptocurrencies;
        this.notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CryptocurrencyVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cryptyo_curr_rv_item, parent, false);
        CryptocurrencyVH vh = new CryptocurrencyVH(view, new mClickListener() {
            @Override
            public void onDelete(int p) {
                new AlertDialog.Builder(parent.getContext())
                        .setTitle("Title")
                        .setMessage("Are you sure?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }

            @Override
            public void onEdit(int p) {

            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CryptocurrencyAdapter.CryptocurrencyVH holder, int position) {
        Cryptocurrency cryptocurrency = this.cryptocurrencies.get(position);
        holder.name_tv.setText("Name: " + cryptocurrency.getName());
        holder.price_tv.setText("USD Price : " + df.format(cryptocurrency.getPriceUsd()));
        holder.symbol_tv.setText("Symbol: " + cryptocurrency.getId());
        holder.amount_tv.setText("Amount " + String.valueOf(cryptocurrency.getAmount()));

        double val = cryptocurrency.getAmount() * cryptocurrency.getPriceUsd();
        holder.value_tv.setText("Value: " + df.format(val));


    }

    @Override
    public int getItemCount() {
        return cryptocurrencies.size();
    }



    public class CryptocurrencyVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private mClickListener listener;
        private TextView symbol_tv, name_tv, price_tv, value_tv, amount_tv;
        Button delete_btn, edit_btn;

        public CryptocurrencyVH(@NonNull @NotNull View itemView, mClickListener listener) {
            super(itemView);
            this.listener = listener;

            name_tv = itemView.findViewById(R.id.coin_name_tv);
            symbol_tv = itemView.findViewById(R.id.coin_symbol_tv);
            price_tv = itemView.findViewById(R.id.coin_price_tv);
            value_tv = itemView.findViewById(R.id.coins_value_tv);
            amount_tv = itemView.findViewById(R.id.coin_amount_tv);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            edit_btn = itemView.findViewById(R.id.edit_btn);

            delete_btn.setOnClickListener(this);
            edit_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_btn:
                    listener.onDelete(this.getLayoutPosition());
                    break;
                case R.id.edit_btn:
                    listener.onEdit(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface mClickListener {
        void onDelete(int p);

        void onEdit(int p);
    }
}
