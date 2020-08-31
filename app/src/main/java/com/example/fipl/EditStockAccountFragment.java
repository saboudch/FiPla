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
 * Use the {@link EditStockAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment kann ein Aktienkonto überarbeitet oder gelöscht werden.
 */
public class EditStockAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppControler appControler = new AppControler();
    private int position;

    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird.
     * Position wird ebenfalls übergeben, damit das Konto an der Position "Position" der Liste überarbeitet werden kann
     * @param appControler, position
     */
    public EditStockAccountFragment(AppControler appControler, int pos) {
        this.appControler=appControler;
        this.position=pos;
    }

    //Funktion zum öffnen eines Fragments

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    //Funktion die alle Daten in der Cache sichert

    private void saveAccountList() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedLists", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonStockAccountList = gson.toJson(appControler.getStockAccountList());
        editor.putString("stockAccountList",jsonStockAccountList);
        editor.apply();

    }

    public EditStockAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditStockAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditStockAccountFragment newInstance(String param1, String param2) {
        EditStockAccountFragment fragment = new EditStockAccountFragment();
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
        View v =  inflater.inflate(R.layout.fragment_edit_stock_account, container, false);

        //Deklaration und Setzen aller TextViews im Fragment

        TextView editStockAccountTextViewHeader = v.findViewById(R.id.editStockAccountTextViewHeader);
        editStockAccountTextViewHeader.setText(appControler.getStockAccountList().getStockAccountFromList(position).getAccountName());

        final EditText editTextAccountNameDepotEdit = v.findViewById(R.id.editTextAccountNameDepotEdit);
        editTextAccountNameDepotEdit.setText(appControler.getStockAccountList().getStockAccountFromList(position).getAccountName());

        final EditText editTextBalanceDepotEdit = v.findViewById(R.id.editTextBalanceDepotEdit);
        editTextBalanceDepotEdit.setText(String.valueOf(appControler.getStockAccountList().getStockAccountFromList(position).getBalance()));

        final EditText editTextExchangeOrBankNameDepotEdit = v.findViewById(R.id.editTextExchangeOrBankNameDepotEdit);
        editTextExchangeOrBankNameDepotEdit.setText(appControler.getStockAccountList().getStockAccountFromList(position).getExchangeName());

        final EditText editTextAccountCryptoBuyPriceDepotEdit = v.findViewById(R.id.editTextAccountCryptoBuyPriceDepotEdit);
        editTextAccountCryptoBuyPriceDepotEdit.setText(String.valueOf(appControler.getStockAccountList().getStockAccountFromList(position).getBuyprice()));

        //Deklaration der Buttons und einige Unsichtbar machen sofern sie nicht benötigt werden.

        final Button deleteStockAccountButton = v.findViewById(R.id.deleteStockAccountButton);

        final Button buttonSaveEditsStockAccount = v.findViewById(R.id.buttonSaveEditsStockAccount);

        final TextView editStockAccountWarning = v.findViewById(R.id.editStockAccountWarning);
        editStockAccountWarning.setVisibility(View.INVISIBLE);


        final Button deleteStockAccountYesButton = v.findViewById(R.id.deleteStockAccountYesButton);
        deleteStockAccountYesButton.setVisibility(View.INVISIBLE);

        final Button deleteStockAccountNoButton = v.findViewById(R.id.deleteStockAccountNoButton);
        deleteStockAccountNoButton.setVisibility(View.INVISIBLE);

        /**
         * Klick auf Löschen zeigt Warnhinweis und Buttons "Ja" und "Nein"
         */
        deleteStockAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStockAccountWarning.setText("Wollen Sie wirklich die Aktie \""+
                        appControler.getStockAccountList().getStockAccountFromList(position).getAccountName()+
                        "\" aus Ihrem Depot löschen?");
                deleteStockAccountButton.setVisibility(View.INVISIBLE);
                buttonSaveEditsStockAccount.setVisibility(View.INVISIBLE);
                editStockAccountWarning.setVisibility(View.VISIBLE);
                deleteStockAccountNoButton.setVisibility(View.VISIBLE);
                deleteStockAccountYesButton.setVisibility(View.VISIBLE);
            }
        });

        /**
         * Klick auf Sichern-Button prüft ob Daten korrekt angegeben wurden.
         * Wenn ja werden Änderungen übernommen, Daten gesichert und Fragment Accounts geöffnet
         * Wenn nein, erscheint ein Hinweistext
         */
        buttonSaveEditsStockAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextAccountNameDepotEdit.getText().length() >0 &&
                        String.valueOf(editTextBalanceDepotEdit.getText()).length() >0 && String.valueOf(editTextAccountCryptoBuyPriceDepotEdit).length()>0){

                    String name= String.valueOf(editTextAccountNameDepotEdit.getText());
                    String balance = String.valueOf(editTextBalanceDepotEdit.getText());
                    String buyPrice= String.valueOf(editTextAccountCryptoBuyPriceDepotEdit.getText());
                    String exchangeName= String.valueOf(editTextExchangeOrBankNameDepotEdit.getText());

                    appControler.getStockAccountList().getStockAccountFromList(position).setAccountName(name);
                    appControler.getStockAccountList().getStockAccountFromList(position).setBalance(Double.parseDouble(balance));
                    appControler.getStockAccountList().getStockAccountFromList(position).setBuyprice(Double.parseDouble(buyPrice));
                    appControler.getStockAccountList().getStockAccountFromList(position).setExchangeName(exchangeName);

                    saveAccountList();
                    openFragment(new StockHomeFragment(appControler));
                }
                else {
                    editStockAccountWarning.setText("Bitte vervollständigen Sie die Felder \"Name\" , \"Anzahl\" und \"Einkaufspreis\"");
                    editStockAccountWarning.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * Klick auf "Ja" löscht das Konto und speichert die Daten in der Cache
         * Anschließend wird Fragment AccountsHome geöffnet
         */
        deleteStockAccountYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appControler.getStockAccountList().deleteStockAccountFromList(position);
                saveAccountList();
                openFragment(new StockHomeFragment(appControler));
            }
        });

        /**
         * Klick auf nein bricht Löschvorgang ab und setzt die versteckten Elemente wieder VISIBLE
         */
        deleteStockAccountNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStockAccountButton.setVisibility(View.VISIBLE);
                buttonSaveEditsStockAccount.setVisibility(View.VISIBLE);
                editStockAccountWarning.setVisibility(View.INVISIBLE);
                deleteStockAccountNoButton.setVisibility(View.INVISIBLE);
                deleteStockAccountYesButton.setVisibility(View.INVISIBLE);
            }
        });
        return v;
    }
}