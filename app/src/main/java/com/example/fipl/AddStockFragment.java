package com.example.fipl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Objects;

import Controler.AppControler;
import account.StockAccount;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment kann ein Aktienaccount hinzugefügt werden
 */
public class AddStockFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppControler appControler = new AppControler();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Funktion zum sichern der Daten in der Cache
    private void saveAccountList() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedLists", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonStockAccountList = gson.toJson(appControler.getStockAccountList());
        editor.putString("stockAccountList",jsonStockAccountList);
        editor.apply();
    }

    public AddStockFragment() {
                // Required empty public constructor
    }

    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird
     * @param appControler
     */
    public AddStockFragment(AppControler appControler) {
        this.appControler=appControler;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDepotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStockFragment newInstance(String param1, String param2) {
        AddStockFragment fragment = new AddStockFragment();
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

    //Funktion zum öffnen des übergebenem Fragment
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_add_stock, container, false);

        //Deklaration aller Elemente im Fragment
        final EditText editAccountName = v.findViewById(R.id.editTextAccountNameDepot);
        final EditText editBalance = v.findViewById(R.id.editTextBalanceDepot);
        final EditText editDepotOrExchangeName = v.findViewById(R.id.editTextExchangeOrBankNameDepot);
        final EditText editBuyPrice=v.findViewById(R.id.editTextAccountCryptoBuyPriceDepot);
        final TextView addStockAccountWarning= v.findViewById(R.id.addStockAccountWarning);
        //Add account button
        final Button addAccountButton = v.findViewById(R.id.buttonAddAccountDepot);

        //Unsichtbarmachen des Hinweistextes und setzen des Textes
        addStockAccountWarning.setText("Bitte vervollständigen Sie die Felder \"Name\" , \"Anzahl\" und \"Einkaufspreis\"");
        addStockAccountWarning.setVisibility(View.INVISIBLE);

        /**
         * Beim Klick auf Hinzufügen wird geprüft ob alle Notwendigen Daten eingegeben wurden.
         * Wenn nein erscheint ein Hinweistext.
         * Wenn ja wird ein neues Aktienkonto angelegt in der Cache gesichert und
         * AktienFragment geöffnet
         */
        addAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               if (editAccountName.getText().length() >0 &&
                        String.valueOf(editBalance.getText()).length() >0 && String.valueOf(editBuyPrice.getText()).length()>0){

                    String name= String.valueOf(editAccountName.getText());
                    String balance = String.valueOf(editBalance.getText());
                    String buyPrice= String.valueOf(editBuyPrice.getText());
                    String exchangeName= String.valueOf(editDepotOrExchangeName.getText());
                    StockAccount stockAccount = new StockAccount(name,Double.valueOf(balance),exchangeName,Double.valueOf(buyPrice));
                    appControler.getStockAccountList().addStockAccount(stockAccount);
                    saveAccountList();
                    openFragment(new StockHomeFragment(appControler));
                }
               else {
                   addStockAccountWarning.setVisibility(View.VISIBLE);
               }
            }
        });

        return v;
    }
}