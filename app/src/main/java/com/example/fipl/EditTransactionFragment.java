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
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Objects;

import Controler.AppControler;
import transaction.Transaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment kann ein Umsatz überarbeitet oder gelöscht werden
 */
public class EditTransactionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AppControler appControler = new AppControler();
    private int positionAccount;
    private int positionTransaction;

    //Funktion zum sichern der Daten in der Cache
    private void saveAccountList() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedLists", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonBankAccountList = gson.toJson(appControler.getBankAccountList());
        editor.putString("bankAccountList",jsonBankAccountList);
        editor.apply();

    }
    // Funktion zum öffnen eines Fragment
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    public EditTransactionFragment() {
        // Required empty public constructor
    }
    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird.
     * Position wird ebenfalls übergeben, damit das Konto an der Position "Position" der Liste überarbeitet werden kann
     * Außerdem noch positionTransaction, in der die Position der Transaktion in der Liste festgelegt wird
     * @param appControler, position , positionTransaction
     */

    public EditTransactionFragment(AppControler appControler, int positionAccount, int positionTransaction) {
        this.appControler=appControler;
        this.positionAccount=positionAccount;
        this.positionTransaction=positionTransaction;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditTransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTransactionFragment newInstance(String param1, String param2) {
        EditTransactionFragment fragment = new EditTransactionFragment();
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
        View v= inflater.inflate(R.layout.fragment_edit_transaction, container, false);

        //Deklarieren der TextViews im Fragment
        final TextView editTransactionTextViewHeader= v.findViewById(R.id.editTransactionTextViewHeader);
        editTransactionTextViewHeader.setText(appControler.getBankAccountList().getBankAccountFromList(positionAccount)
                .getTransactionList().getTransactionFromList(positionTransaction).getName());

        final EditText editTextBankAccountTransactionNameEdit =v.findViewById(R.id.editTextBankAccountTransactionNameEdit);
        editTextBankAccountTransactionNameEdit.setText(appControler.getBankAccountList().getBankAccountFromList(positionAccount).getTransactionList().getTransactionFromList(positionTransaction).getName());

        final EditText editTextBankAccountTransactionBalanceEdit = v.findViewById(R.id.editTextBankAccountTransactionBalanceEdit);
        final double balance =appControler.getBankAccountList().getBankAccountFromList(positionAccount).getTransactionList().getTransactionFromList(positionTransaction).getBalance();
        editTextBankAccountTransactionBalanceEdit.setText(String.valueOf(balance));

        //Switch false setzen, wenn Umsatz eine Einnahme ist
        //Wenn Ausgabe auf true
        final Switch switchInOutEditTransactionBankaccount = v.findViewById(R.id.switchInOutEditTransactionBankaccount);
        if (balance > 0){
            switchInOutEditTransactionBankaccount.setChecked(false);
        }
        else if (balance < 0){
            switchInOutEditTransactionBankaccount.setChecked(true);
        }

        //Hinweis und Buttons deklarieren und auf unsichtbar setzen
        final TextView editTransactionWarning = v.findViewById(R.id.editTransactionWarning);
        editTransactionWarning.setVisibility(View.INVISIBLE);

        final Button deleteTransactionYesButton = v.findViewById(R.id.deleteTransactionYesButton);
        deleteTransactionYesButton.setVisibility(View.INVISIBLE);

        final Button deleteTransactionNoButton = v.findViewById(R.id.deleteTransactionNoButton);
        deleteTransactionNoButton.setVisibility(View.INVISIBLE);

        final Button deleteTransactionButton = v.findViewById(R.id.deleteTransaction);

        final Button buttonEditTransactionBankAccount = v.findViewById(R.id.buttonEditTransactionBankAccount);

        /**
         * Klick auf Sichern-Button prüft ob Daten korrekt angegeben wurden.
         * Wenn ja werden Änderungen übernommen, Daten gesichert und Fragment Accounts geöffnet.
         * Außerdem wird auch durch die Switch geprüft ob Umsatz eine Einnahme oder Ausgabe ist.
         * Wenn nein, erscheint ein Hinweistext
         */
        buttonEditTransactionBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextBankAccountTransactionBalanceEdit.getText().length() > 0 &&
                        String.valueOf(editTextBankAccountTransactionNameEdit.getText()).length() > 0) {
                    Transaction transaction;
                    if (switchInOutEditTransactionBankaccount.isChecked()) {
                        transaction = new Transaction(String.valueOf(editTextBankAccountTransactionNameEdit.getText()), 0 - Double.parseDouble(String.valueOf(editTextBankAccountTransactionBalanceEdit.getText())));
                    } else {
                        transaction = new Transaction(String.valueOf(editTextBankAccountTransactionNameEdit.getText()), Double.parseDouble(String.valueOf(editTextBankAccountTransactionBalanceEdit.getText())));
                    }
                    appControler.getBankAccountList().getBankAccountFromList(positionAccount).setTransactionData(positionTransaction, transaction.getName(),transaction.getBalance());
                    saveAccountList();
                    openFragment(new TransactionBankAccountFragment(appControler, positionAccount));
                }
                else {
                    editTransactionWarning.setText("Bitte Füllen Sie die Felder aus");
                    editTransactionWarning.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * Klick auf Löschen zeigt Warnhinweis und Buttons "Ja" und "Nein"
         */

        deleteTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTransactionWarning.setText("Wollen Sie wirklich diesen Umsatz vom Konto löschen?");
                editTransactionWarning.setVisibility(View.VISIBLE);
                deleteTransactionYesButton.setVisibility(View.VISIBLE);
                deleteTransactionNoButton.setVisibility(View.VISIBLE);
                deleteTransactionButton.setVisibility(View.INVISIBLE);
                buttonEditTransactionBankAccount.setVisibility(View.INVISIBLE);
            }
        });

        /**
         * Klick auf nein bricht Löschvorgang ab und setzt die versteckten Elemente wieder VISIBLE
         */
        deleteTransactionNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTransactionWarning.setVisibility(View.INVISIBLE);
                deleteTransactionYesButton.setVisibility(View.INVISIBLE);
                deleteTransactionNoButton.setVisibility(View.INVISIBLE);
                deleteTransactionButton.setVisibility(View.VISIBLE);
                buttonEditTransactionBankAccount.setVisibility(View.VISIBLE);
            }
        });

        /**
         * Klick auf "Ja" löscht den Umsatz und speichert die Daten in der Cache
         * Anschließend wird Fragment AccountsHome geöffnet
         */
        deleteTransactionYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appControler.getBankAccountList().getBankAccountFromList(positionAccount).deleteTransaction(positionTransaction);
                saveAccountList();
                openFragment(new TransactionBankAccountFragment(appControler,positionAccount));
            }
        });
        return v;
    }
}