package com.example.loginregisterfirebase.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.adapters.AssetsAdapter;
import com.example.loginregisterfirebase.adapters.FundsAdapter;
import com.example.loginregisterfirebase.dialogs.AddAssetDialog;
import com.example.loginregisterfirebase.dialogs.AddFundDialog;
import com.example.loginregisterfirebase.viewModels.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FundsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FundsFragment extends Fragment {

    private UserViewModel userViewModel;
    private FundsAdapter adapter;
    private RecyclerView recyclerView;

    private Button add_fund_btn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FundsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FundsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FundsFragment newInstance(String param1, String param2) {
        FundsFragment fragment = new FundsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_funds, container, false);
        recyclerView = view.findViewById(R.id.funds_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        add_fund_btn = view.findViewById(R.id.add_fund_btn);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        add_fund_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addFunDialog = new AddFundDialog();
                addFunDialog.setCancelable(true);
                addFunDialog.show(getActivity().getSupportFragmentManager(), "add fund dialog");
            }
        });
        userViewModel.getFunds().observe(getViewLifecycleOwner(), funds -> {
            if (adapter == null) {
                adapter = new FundsAdapter(funds, getContext());
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        });
    }
}