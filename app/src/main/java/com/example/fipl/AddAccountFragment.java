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
 * Fragment in dem ein Bankkonto überarbeitet werden kann
 */
public class AddAccountFragment extends Fragment {

    private AppControler appControler = new AppControler();

    /**
     * Diese Funktion sichert alle Daten in der Cache, damit
     * diese Später beim erneuten Öffnen der App wieder zugönglich sind
     */
    private void saveAccountList() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedLists", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonBankAccountList = gson.toJson(appControler.getBankAccountList());
        editor.putString("bankAccountList",jsonBankAccountList);
        editor.apply();

    }

    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird
     * @param appControler
     */
    public AddAccountFragment(AppControler appControler) {
        this.appControler=appControler;
    }

    /**
     * Diese Funktion öffnet das übergebene Fragment
     * @param fragment
     */
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    public AddAccountFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_bankaccount, container, false);

        //Deklaration aller Elemente im Fragment
        final EditText editAccountName = v.findViewById(R.id.editTextAccountName);
        final EditText editBalance = v.findViewById(R.id.editTextBalance);
        final EditText editCurrency= v.findViewById(R.id.editTextAccountCurrency);
        final EditText editBankWalletExchangeName= v.findViewById(R.id.editTextExchangeOrBankName);
        final EditText editIBAN=v.findViewById(R.id.editTextBankIBAN);
        final EditText editBIC=v.findViewById(R.id.editTextBankBIC);
        final TextView addBankAccountWarning = v.findViewById(R.id.addBankAccountWarning);

        //Hinweis-Test setzen und unsichtbar machen.
        //Dieser wird später sichtbar, wenn nicht notwendige Felder befüllt und auf Add-Button geklickt wird
        addBankAccountWarning.setText("Bitte vervollständigen Sie die Felder \"Name\" und \"Guthaben\"");
        addBankAccountWarning.setVisibility(View.INVISIBLE);

        //Add account button
        final Button addAccountButton = v.findViewById(R.id.buttonAddAccount);

        /**
         * Diese On-Click Funktion prüft ob alle notwendigen Felder befüllt wurden.
         * Wenn ja, werden die Eingaben übernommen und ein neues Bankkonto angelegt und gespeichert.
         * Anschließend AccountFragment geöffnet
         * Wenn nein, erscheint ein Hinweistext, der darauf hinweist, dass nicht alle Daten befüllt sind.
         */
        addAccountButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                if (editAccountName.getText().length() >0 &&
                            String.valueOf(editBalance.getText()).length() >0){

                        String name= String.valueOf(editAccountName.getText());
                        String bankOrExchangeName=String.valueOf(editBankWalletExchangeName.getText());
                        String balance = String.valueOf(editBalance.getText());
                        String currency= String.valueOf(editCurrency.getText());
                        String IBAN= String.valueOf(editIBAN.getText());
                        String BIC=String.valueOf(editBIC.getText());
                        BankAccount bankAccount = new BankAccount(name,Double.valueOf(balance),currency,bankOrExchangeName,IBAN,BIC);
                        appControler.getBankAccountList().addBankAccount(bankAccount);
                        saveAccountList();
                        openFragment(new AccountsHomeFragment(appControler));
                    }
                else {
                    addBankAccountWarning.setVisibility(View.VISIBLE);
                }
                }
            });

        return v;
    }
}