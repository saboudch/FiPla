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

import java.util.Objects;

import Controler.AppControler;
import account.StockAccountList;
import adapter.StockListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StockHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment werden alle AktienKonten angezeigt
 * Außerdem wird beim Klick auf ein Listenelement der Header mit Daten des Kontos befüllt
 */
public class StockHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppControler appControler = new AppControler();
    private int pos;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StockHomeFragment(AppControler appControler) {
        this.appControler=appControler;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    public StockHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StockHomeFragment newInstance(String param1, String param2) {
        StockHomeFragment fragment = new StockHomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_stock_home, container, false);
        final TextView stockNameTextView = v.findViewById(R.id.textView_stockAccount_header_AccountName);
        final TextView balanceTextView=v.findViewById(R.id.textView_stockAccount_header_balance);
        final TextView stockExchangeTextView= v.findViewById(R.id.textView_stockAccount_header_exchange);
        final TextView stockBuyPriceTextView= v.findViewById(R.id.textView_stockAccount_header_buyPrice);

        final ListView stockListView = v.findViewById(R.id.listViewAccountsStock);

        final Button buttonEditStock = v.findViewById(R.id.buttonEditStock);
        buttonEditStock.setVisibility(View.INVISIBLE);
        buttonEditStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new EditStockAccountFragment(appControler,pos));
            }
        });

        StockListAdapter stockListAdapter = new StockListAdapter(Objects.requireNonNull(getActivity()), R.layout.list_view_stock_accounts_row, appControler.getStockAccountList().getStockAccountList() );
        stockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buttonEditStock.setVisibility(View.VISIBLE);
                pos=position;
                stockNameTextView.setText(appControler.getStockAccountList().getStockAccountFromList(position).getAccountName());
                balanceTextView.setText(Double.toString(appControler.getStockAccountList().getStockAccountFromList(position).getBalance())+ " " + appControler.getStockAccountList().getStockAccountFromList(position).getCurrency());
                stockExchangeTextView.setText("Börse / Depot: "+appControler.getStockAccountList().getStockAccountFromList(position).getExchangeName());
                stockBuyPriceTextView.setText("Einkaufspreis pro Aktie: " + Double.toString(appControler.getStockAccountList().getStockAccountFromList(position).getBuyprice()) + " EUR");

            }
        });

        stockListView.setAdapter(stockListAdapter);
        Button addButton;
        addButton = v.findViewById(R.id.add_stock_button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openFragment(new AddStockFragment(appControler));
            }
        });


        return v;
    }
}