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
import account.CryptoAccount;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCryptoAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment kann ein Kryptoaccount hinzugefügt werden
 */
public class AddCryptoAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppControler appControler= new AppControler();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Funktion für das Speichern der Daten in der Cache
    private void saveAccountList() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedLists", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonCryptoAccountList = gson.toJson(appControler.getCryptoAccountList());
        editor.putString("cryptoAccountList",jsonCryptoAccountList);
        editor.apply();

    }

    public AddCryptoAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird
     * @param appControler
     */
    public AddCryptoAccountFragment(AppControler appControler) {
        this.appControler= appControler;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCryptoAccount.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCryptoAccountFragment newInstance(String param1, String param2) {
        AddCryptoAccountFragment fragment = new AddCryptoAccountFragment();
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

    //Funktion für das Öffnen eines übergenemen Fragment
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_crypto_account, container, false);

        //Deklaration der Elemente im Fragment
        final EditText editAccountName = v.findViewById(R.id.editTextAccountNameCrypto);
        final EditText editBalance = v.findViewById(R.id.editTextBalanceCrypto);
        final EditText editCurrency= v.findViewById(R.id.editTextAccountCurrencyCrypto);
        final EditText editCryptoAddress=v.findViewById(R.id.editTextCryptoAdressCrypto);
        final EditText editBuyPrice=v.findViewById(R.id.editTextAccountCryptoBuyPriceCrypto);
        final TextView addCryptoAccountWarning= v.findViewById(R.id.addCryptoAccountWarning);

        //Warntext setzen und unsichtbar machen. Wird später benötigt, wenn Felder nicht vollständig befüllt
        addCryptoAccountWarning.setVisibility(View.INVISIBLE);
        addCryptoAccountWarning.setText("Bitte vervollständigen Sie die Felder \"Name\", \"Guthaben\" und \"Einkaufspreis\"");
        //Add account button
        final Button addAccountButton = v.findViewById(R.id.buttonAddAccountCrypto);

        /**
         * Beim Klicken auf Hinzufügen-button wird geprüft ob alle Notwendigen Felder befüllt wurden.
         * Wenn ja wird ein neues Kryptokonto angelegt und in der Cache gesichert.
         * Außerdem wird das CryptoFragment geöffnet
         * Wenn nein, erscheint ein Warnhinweis, dass notwendige Felder noch befüllt werden müssen.
         */
        addAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            if (editAccountName.getText().length() >0 &&
                        String.valueOf(editBalance.getText()).length() >0 && String.valueOf(editBuyPrice.getText()).length()>0){

                    String name= String.valueOf(editAccountName.getText());
                    String balance = String.valueOf(editBalance.getText());
                    String currency= String.valueOf(editCurrency.getText());
                    String buyPrice= String.valueOf(editBuyPrice.getText());
                    String cryptoAdress=String.valueOf(editCryptoAddress.getText());
                    CryptoAccount cryptoAccount = new CryptoAccount(name,Double.valueOf(balance),currency,cryptoAdress,Double.valueOf(buyPrice));
                    appControler.getCryptoAccountList().addCryptoAccount(cryptoAccount);
                    saveAccountList();
                    openFragment(new CryptosHomeFragment(appControler));
                }
            else{
                addCryptoAccountWarning.setVisibility(View.VISIBLE);
            }
            }
        });

        return v;
    }

}