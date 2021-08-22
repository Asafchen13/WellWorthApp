package com.example.loginregisterfirebase.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.UserViewModel;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private UserViewModel userViewModel;
    private PieChart pieChart;
    private TextView coins_amount_tv;
    private TextView assets_amount_tv;
    private TextView funds_amount_tv;
    private TextView stocks_amount_tv;
    private TextView total_balance_TV;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        total_balance_TV = view.findViewById(R.id.total_balance);
        coins_amount_tv = view.findViewById(R.id.crypto_amount_tv);
        assets_amount_tv = view.findViewById(R.id.assets_amount_tv);
        funds_amount_tv = view.findViewById(R.id.funds_amount_tv);
        stocks_amount_tv = view.findViewById(R.id.stocks_amount_tv);

        pieChart = view.findViewById(R.id.piechart);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userViewModel.getCryptocurrencies().observe(getViewLifecycleOwner(), cryptocurrencies -> {
            coins_amount_tv.setText(String.valueOf(cryptocurrencies.size()));
            pieChart.addPieSlice(new PieModel("Crypto", cryptocurrencies.size() % 100, Color.parseColor("#FFA726")));
        });

        userViewModel.getAssets().observe(getViewLifecycleOwner(), assets -> {
            assets_amount_tv.setText(String.valueOf(assets.size()));
            pieChart.addPieSlice(new PieModel("Crypto", assets.size() % 100, Color.parseColor("#66BB6A")));
        });
    }


}