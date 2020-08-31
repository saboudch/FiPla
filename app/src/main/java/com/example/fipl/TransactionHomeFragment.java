package com.example.fipl;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.Objects;

import Controler.AppControler;
import account.StockAccountList;
import adapter.StockListAdapter;
import adapter.TransactionListAdapter;
import transaction.TransactionList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment werden alle Umsätze aller Konten angezeigt
 * Hier wird nur eine Liste angzeigt es gibt keine Buttons.
 */
public class TransactionHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppControler appControler = new AppControler();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionHomeFragment(AppControler appControler) {
        this.appControler=appControler;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    public TransactionHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionHomeFragment newInstance(String param1, String param2) {
        TransactionHomeFragment fragment = new TransactionHomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_transaction_home, container, false);

        final ListView stockListView = v.findViewById(R.id.listViewTransactions);

        final TransactionList transactionList = new TransactionList();

        for (int i=0;i<appControler.getBankAccountList().getSizeOfBankAccountList();i++){
            for (int j=0;j<appControler.getBankAccountList().getBankAccountFromList(i).getTransactionList().getSizeOfTransactionList();j++){
                transactionList.addTransaction(appControler.getBankAccountList().getBankAccountFromList(i).getTransactionList().getTransactionFromList(j));
            }
        }

        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(Objects.requireNonNull(getActivity()), R.layout.list_view_transaction_row, transactionList.getTransactionList());
        stockListView.setAdapter(transactionListAdapter);
        return v;
    }
}