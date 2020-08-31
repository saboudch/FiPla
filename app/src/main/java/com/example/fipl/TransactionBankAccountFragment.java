package com.example.fipl;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

import Controler.AppControler;
import adapter.TransactionListAdapter;
import transaction.TransactionList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionBankAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment werden die Umsätze eines Bankkontos in einer Liste angezeigt
 * Klick auf ein Umsatz öffnet das EditTransactionFragment.
 */
public class TransactionBankAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppControler appControler= new AppControler();
    private int positionAccount;
    private int positionTransaction;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    public TransactionBankAccountFragment() {
        // Required empty public constructor
    }

    public TransactionBankAccountFragment(AppControler appControler, int position){
        this.appControler=appControler;
        this.positionAccount=position;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionBankAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionBankAccountFragment newInstance(String param1, String param2) {
        TransactionBankAccountFragment fragment = new TransactionBankAccountFragment();
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
        View v = inflater.inflate(R.layout.fragment_transaction_bank_account, container, false);
        final TextView headerTextView = v.findViewById(R.id.textView_transaction_bankaccount_header);
        headerTextView.setText(appControler.getBankAccountList().getBankAccountFromList(positionAccount).getAccountName());
        final ListView transactionListView = v.findViewById(R.id.listViewTransactionsBankaccount);

        final TransactionList transactionList = appControler.getBankAccountList().getBankAccountFromList(positionAccount).getTransactionList();


        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(Objects.requireNonNull(getActivity()), R.layout.list_view_transaction_row, transactionList.getTransactionList() );

        transactionListView.setAdapter(transactionListAdapter);
        transactionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openFragment(new EditTransactionFragment(appControler,positionAccount,position));
            }
        });
        Button addButton;
        addButton = v.findViewById(R.id.add_transaction_bankaccount_button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openFragment(new AddTransactionBankAccountFragment(appControler,positionAccount));
            }
        });


        return v;
    }
}