package com.example.loginregisterfirebase.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.UserViewModel;
import com.example.loginregisterfirebase.adapters.CryptocurrencyAdapter;
import com.example.loginregisterfirebase.logic.Cryptocurrency;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CryptoCurrenciesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CryptoCurrenciesFragment extends Fragment {
    private UserViewModel userViewModel;
    private CryptocurrencyAdapter adapter;
    private RecyclerView recyclerView;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Cryptocurrency> c = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CryptoCurrenciesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CryptoCurrenciesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CryptoCurrenciesFragment newInstance(String param1, String param2) {
        CryptoCurrenciesFragment fragment = new CryptoCurrenciesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crypto_currencies, container, false);
        recyclerView = root.findViewById(R.id.crypto_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userViewModel.getCryptocurrencies().observe(getViewLifecycleOwner(), cryptocurrencies -> {
            if (adapter == null) {
                adapter = new CryptocurrencyAdapter((ArrayList) cryptocurrencies, getContext());
                recyclerView.setAdapter(adapter);
            }
            else {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void handleDeleteCoinClicked() {

    }

}