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
import account.BankAccount;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditBankAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment können die Daten eines Bankkontos überarbeitet werden
 * Außerdem kann hier auch ein Bankkonto gelöscht werden
 */
public class EditBankAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int position;
    private AppControler appControler;

    //Funktion für das Sichern der Daten in der Cache
    private void saveAccountList() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedLists", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonBankAccountList = gson.toJson(appControler.getBankAccountList());
        editor.putString("bankAccountList",jsonBankAccountList);
        editor.apply();

    }

    //Funktion für das Öffnen eines Fragments
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    public EditBankAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird.
     * Position wird ebenfalls übergeben, damit das Konto an der Position "Position" der Liste überarbeitet werden kann
     * @param appControler, position
     */
    public EditBankAccountFragment(AppControler appControler, int position) {
        this.appControler=appControler;
        this.position=position;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditBankAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditBankAccountFragment newInstance(String param1, String param2) {
        EditBankAccountFragment fragment = new EditBankAccountFragment();
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
        View v = inflater.inflate(R.layout.fragment_edit_bank_account, container, false);

        //Deklaration und Setzen aller TextViews im Fragment
        final TextView editBankAccountHeaderTextView = v.findViewById(R.id.editBankAccountTextViewHeader);
        editBankAccountHeaderTextView.setText(appControler.getBankAccountList().getBankAccountFromList(position).getAccountName());

        final TextView editBankAccountWarning = v.findViewById(R.id.editBankAccountWarning);
        editBankAccountWarning.setVisibility(View.INVISIBLE);

        final EditText editTextBankAccountNameEdit = v.findViewById(R.id.editTextAccountNameEdit);
        editTextBankAccountNameEdit.setText(appControler.getBankAccountList().getBankAccountFromList(position).getAccountName());

        final EditText editTextBalanceEdit = v.findViewById(R.id.editTextBalanceEdit);
        editTextBalanceEdit.setText(String.valueOf(appControler.getBankAccountList().getBankAccountFromList(position).getBalance()));

        final EditText editTextAccountCurrencyEdit = v.findViewById(R.id.editTextAccountCurrencyEdit);
        editTextAccountCurrencyEdit.setText(appControler.getBankAccountList().getBankAccountFromList(position).getCurrency());

        final EditText editTextExchangeOrBankNameEdit = v.findViewById(R.id.editTextExchangeOrBankNameEdit);
        editTextExchangeOrBankNameEdit.setText(appControler.getBankAccountList().getBankAccountFromList(position).getBankName());

        final EditText editTextBankIBANEdit= v.findViewById(R.id.editTextBankIBANEdit);
        editTextBankIBANEdit.setText(appControler.getBankAccountList().getBankAccountFromList(position).getIban());

        final EditText editTextBankBICEdit = v.findViewById(R.id.editTextBankBICEdit);
        editTextBankBICEdit.setText(appControler.getBankAccountList().getBankAccountFromList(position).getBic());

        //Deklaration der Buttons und einige Unsichtbar machen sofern sie nicht benötigt werden.
        final Button deleteBankAccountButton = v.findViewById(R.id.deleteBankAccountButton);
        final Button saveEdits = v.findViewById(R.id.buttonSaveEditsBankAccount);

        final Button deleteBankAccountYesButton = v.findViewById(R.id.deleteBankAccountYesButton);
        deleteBankAccountYesButton.setVisibility(View.INVISIBLE);

        /**
         * Klick auf "Ja" löscht das Konto und speichert die Daten in der Cache
         * Anschließend wird Fragment AccountsHome geöffnet
         */
        deleteBankAccountYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appControler.getBankAccountList().deleteBankAccountFromList(position);
                saveAccountList();
                openFragment(new AccountsHomeFragment(appControler));
            }
        });

        /**
         * Klick auf nein bricht Löschvorgang ab und setzt die versteckten Elemente wieder VISIBLE
         */
        final Button deleteBankAccountNoButton = v.findViewById(R.id.deleteBankAccountNoButton);
        deleteBankAccountNoButton.setVisibility(View.INVISIBLE);
        deleteBankAccountNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdits.setVisibility(View.VISIBLE);
                deleteBankAccountButton.setVisibility(View.VISIBLE);
                deleteBankAccountNoButton.setVisibility(View.INVISIBLE);
                deleteBankAccountYesButton.setVisibility(View.INVISIBLE);
                editBankAccountWarning.setVisibility(View.INVISIBLE);
            }
        });

        /**
         * Klick auf Löschen zeigt Warnhinweis und Buttons "Ja" und "Nein"
         */
        deleteBankAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdits.setVisibility(View.INVISIBLE);
                deleteBankAccountButton.setVisibility(View.INVISIBLE);
                deleteBankAccountNoButton.setVisibility(View.VISIBLE);
                deleteBankAccountYesButton.setVisibility(View.VISIBLE);
                editBankAccountWarning.setVisibility(View.VISIBLE);
                editBankAccountWarning.setText("Wollen Sie das Konto "+
                        appControler.getBankAccountList().getBankAccountFromList(position).getAccountName() +
                        " wirklich löschen?");
            }
        });


        /**
         * Klick auf Sichern-Button prüft ob Daten korrekt angegeben wurden.
         * Wenn ja werden Änderungen übernommen, Daten gesichert und Fragment Accounts geöffnet
         * Wenn nein, erscheint ein Hinweistext
         */
        saveEdits.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if (editTextBankAccountNameEdit.getText().length() >0 &&
                        String.valueOf(editTextBalanceEdit.getText()).length() >0){

                    appControler.getBankAccountList().getBankAccountFromList(position).setAccountName(String.valueOf(editTextBankAccountNameEdit.getText()));
                    appControler.getBankAccountList().getBankAccountFromList(position).setBankName(String.valueOf(editTextExchangeOrBankNameEdit.getText()));
                    appControler.getBankAccountList().getBankAccountFromList(position).setBalance(Double.parseDouble(String.valueOf(editTextBalanceEdit.getText())));
                    appControler.getBankAccountList().getBankAccountFromList(position).setCurrency(String.valueOf(editTextAccountCurrencyEdit.getText()));
                    appControler.getBankAccountList().getBankAccountFromList(position).setIban(String.valueOf(editTextBankIBANEdit.getText()));
                    appControler.getBankAccountList().getBankAccountFromList(position).setBic(String.valueOf(editTextBankBICEdit.getText()));

                    saveAccountList();
                    openFragment(new AccountsHomeFragment(appControler));
                }
                else {
                    editBankAccountWarning.setVisibility(View.VISIBLE);
                    editBankAccountWarning.setText("Bitte vervollständigen Sie die Felder \"Name\" und \"Guthaben\" ");
                }
            }
        });


        return v;
    }
}