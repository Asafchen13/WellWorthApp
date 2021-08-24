package com.example.loginregisterfirebase.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.loginregisterfirebase.dialogs.AddCoinDialog;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.viewModels.UserViewModel;
import com.example.loginregisterfirebase.adapters.CryptocurrencyAdapter;
import com.example.loginregisterfirebase.logic.Cryptocurrency;
import com.example.loginregisterfirebase.managers.CryptoAPIManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CryptoCurrenciesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CryptoCurrenciesFragment extends Fragment {
    private UserViewModel userViewModel;
    private CryptocurrencyAdapter adapter;
    private RecyclerView recyclerView;
    private CryptoAPIManager cryptoAPIManager;

    apiRequestExecutor executor;
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());


    private Button refresh_btn;
    private Button add_coin_btn;


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
        executor = new apiRequestExecutor();
        cryptoAPIManager = new CryptoAPIManager(executor, mainThreadHandler, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crypto_currencies, container, false);
        recyclerView = root.findViewById(R.id.crypto_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refresh_btn = root.findViewById(R.id.refresh_btn);
        add_coin_btn = root.findViewById(R.id.add_coin_btn);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRefreshCoinsClicked();
            }
        });

        add_coin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addCoinDialog = new AddCoinDialog();
                addCoinDialog.setCancelable(true);
                addCoinDialog.show(getActivity().getSupportFragmentManager(), "add coin dialog");
            }
        });

        userViewModel.getCryptocurrencies().observe(getViewLifecycleOwner(), cryptocurrencies -> {
            if (adapter == null) {
                adapter = new CryptocurrencyAdapter((ArrayList) cryptocurrencies, getContext());
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void handleRefreshCoinsClicked() {

        userViewModel.updateCryptoData(cryptoAPIManager);

    }

    public static class apiRequestExecutor implements Executor {
        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }

    }

}