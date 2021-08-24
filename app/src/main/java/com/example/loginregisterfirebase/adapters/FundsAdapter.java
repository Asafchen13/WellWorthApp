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
import com.example.loginregisterfirebase.dialogs.EditFundDialog;
import com.example.loginregisterfirebase.interfaces.mClickListener;
import com.example.loginregisterfirebase.logic.Asset;
import com.example.loginregisterfirebase.logic.Fund;
import com.example.loginregisterfirebase.managers.DatabaseManager;

import java.text.DecimalFormat;
import java.util.List;

public class FundsAdapter extends RecyclerView.Adapter<FundsAdapter.FundsVH> {

    private static DecimalFormat df = new DecimalFormat("###,###,####.##");
    private List<Fund> funds;
    private Context context;

    public FundsAdapter(List<Fund> funds, Context context) {
        this.funds = funds;
        this.context = context;
    }

    @NonNull
    @Override
    public FundsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_fund, parent, false);

        FundsVH fundsVH = new FundsVH(view, new mClickListener() {
            @Override
            public void onDelete(int p) {
                new AlertDialog.Builder(parent.getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Fund f = funds.get(p);
                                DatabaseManager.getInstance().deleteFund(f);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }

            @Override
            public void onEdit(int p) {

                Fund f = funds.get(p);
                EditFundDialog editFundDialog = new EditFundDialog();
                editFundDialog.setOld_fund_name(f.getName());
                editFundDialog.setOld_fund_company(f.getCompany());
                editFundDialog.setOld_fund_value(String.valueOf(f.getValue()));
                editFundDialog.setCancelable(true);
                editFundDialog.show(((AppCompatActivity)parent.getContext()).getSupportFragmentManager(), "edit fund dialog");

            }
        });
        return fundsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull FundsVH holder, int position) {

        Fund fund = this.funds.get(position);
        holder.name_tv.setText(fund.getName());
        holder.company_tv.setText(fund.getCompany());
        holder.value_tv.setText(df.format(fund.getValue()));

    }


    @Override
    public int getItemCount() {
        return this.funds.size();
    }

    public class FundsVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private mClickListener listener;

        TextView name_tv, company_tv, value_tv;
        Button delete_btn, edit_btn;

        public FundsVH(@NonNull View itemView, mClickListener listener) {
            super(itemView);
            this.listener = listener;

            name_tv = itemView.findViewById(R.id.fund_name_tv);
            company_tv = itemView.findViewById(R.id.fund_company_tv);
            value_tv = itemView.findViewById(R.id.fund_value_tv);
            delete_btn = itemView.findViewById(R.id.delete_fund_btn);
            edit_btn = itemView.findViewById(R.id.edit_fund_btn);

            delete_btn.setOnClickListener(this);
            edit_btn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.delete_fund_btn:
                    listener.onDelete(this.getLayoutPosition());
                    break;
                case R.id.edit_fund_btn:
                    listener.onEdit(this.getLayoutPosition());
                    break;
                default:
                    break;
            }

        }
    }

}
