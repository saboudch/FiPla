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
 * Use the {@link AddTransactionBankAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment kann ein Umsatz zu einem Bankkonto hinzugefügt werden
 */
public class AddTransactionBankAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int position;
    private AppControler appControler= new AppControler();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Funktion zum Sichern der Daten in der Cache
    private void saveAccountList() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedLists", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonBankAccountList = gson.toJson(appControler.getBankAccountList());
        editor.putString("bankAccountList",jsonBankAccountList);
        editor.apply();

    }

    //Funktion zum Öffnen des übergebenem Fragment
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    public AddTransactionBankAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird
     * Position des Kontos in Bankkontenliste wird ebenfalls übergeben, damit für das ausgewählte BAnkkonto die Transaktion
     * hinzugefügt werden kann
     * @param appControler, position
     */
    public AddTransactionBankAccountFragment(AppControler appControler, int position) {
        this.appControler=appControler;
        this.position=position;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTransactionBankAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTransactionBankAccountFragment newInstance(String param1, String param2) {
        AddTransactionBankAccountFragment fragment = new AddTransactionBankAccountFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_transaction_bank_account, container, false);

        //Deklaration aller Elemente im Fragment
        final Switch inOutTransactionSwitch = v.findViewById(R.id.switchInOutTransactionBankaccount);
        final EditText transactionNameEditText = v.findViewById(R.id.editTextBankAccountTransactionName);
        final EditText transactionBalanceEditText = v.findViewById(R.id.editTextBankAccountTransactionBalance);
        final TextView addTransactionWarning = v.findViewById(R.id.addTransactionWarning);

        //Warntext unsichtbar machen
        addTransactionWarning.setVisibility(View.INVISIBLE);

        //Addbutton
        Button addTransactionBankAccountButton = v.findViewById(R.id.buttonAddTransactionBankAccount);

        /**
         * Beim Klicken auf Hinzufügen wird zunächst geprüft ob alle notwendigen Daten eingegeben wurden.
         * Wenn nein, erscheint ein Hinweistext.
         * Wenn ja, wird geprüft ob der Umsatz eine Einnahme oder eine Ausgabe ist.
         * Entsprechend der Switch wird aus dem Umsatz eine Einnahme bzw. Ausgabe gemacht
         * und diese Transaktion wird anschließend angelegt und dem Konto an der Position "position" angelegt.
         * Anschließend werden die Daten gesichert und ein Fragment geöffnet
         */
        addTransactionBankAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               if (transactionBalanceEditText.getText().length() > 0 &&
                        String.valueOf(transactionNameEditText.getText()).length() > 0) {
                    Transaction transaction;
                    if (inOutTransactionSwitch.isChecked()) {
                        transaction = new Transaction(String.valueOf(transactionNameEditText.getText()), 0 - Double.parseDouble(String.valueOf(transactionBalanceEditText.getText())));
                    } else {
                        transaction = new Transaction(String.valueOf(transactionNameEditText.getText()), Double.parseDouble(String.valueOf(transactionBalanceEditText.getText())));
                    }
                    appControler.getBankAccountList().getBankAccountFromList(position).addTransaction(transaction);
                    saveAccountList();
                    openFragment(new TransactionBankAccountFragment(appControler, position));
                }
               else {
                   addTransactionWarning.setVisibility(View.VISIBLE);
               }
            }
        });
        return v;
    }
}