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

import org.w3c.dom.Text;

import java.util.Objects;

import Controler.AppControler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditCryptoAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment kann ein CryptoKonto überarbeitet oder gelöscht werden
 */
public class EditCryptoAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppControler appControler = new AppControler();
    private int position;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird.
     * Position wird ebenfalls übergeben, damit das Konto an der Position "Position" der Liste überarbeitet werden kann
     * @param appControler, position
     */
    public EditCryptoAccountFragment(AppControler appControler, int pos) {
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
        String jsonCryptoAccountList = gson.toJson(appControler.getCryptoAccountList());
        editor.putString("cryptoAccountList",jsonCryptoAccountList);
        editor.apply();

    }

    public EditCryptoAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditCryptoAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditCryptoAccountFragment newInstance(String param1, String param2) {
        EditCryptoAccountFragment fragment = new EditCryptoAccountFragment();
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
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_crypto_account, container, false);

        //Deklaration und Setzen aller TextViews im Fragment

        TextView editCryptoAccountTextViewHeader = v.findViewById(R.id.editCryptoAccountTextViewHeader);
        editCryptoAccountTextViewHeader.setText(appControler.getCryptoAccountList().getCryptoAccountFromList(position).getAccountName());

        final EditText editTextAccountNameCryptoEdit = v.findViewById(R.id.editTextAccountNameCryptoEdit);
        editTextAccountNameCryptoEdit.setText(appControler.getCryptoAccountList().getCryptoAccountFromList(position).getAccountName());

        final EditText editTextBalanceCryptoEdit = v.findViewById(R.id.editTextBalanceCryptoEdit);
        editTextBalanceCryptoEdit.setText(String.valueOf(appControler.getCryptoAccountList().getCryptoAccountFromList(position).getBalance()));

        final EditText editTextAccountCurrencyCryptoEdit = v.findViewById(R.id.editTextAccountCurrencyCryptoEdit);
        editTextAccountCurrencyCryptoEdit.setText(appControler.getCryptoAccountList().getCryptoAccountFromList(position).getCurrency());

        final EditText editTextAccountCryptoBuyPriceCryptoEdit = v.findViewById(R.id.editTextAccountCryptoBuyPriceCryptoEdit);
        editTextAccountCryptoBuyPriceCryptoEdit.setText(String.valueOf(appControler.getCryptoAccountList().getCryptoAccountFromList(position).getBuyprice()));

        final EditText editTextCryptoAdressCryptoEdit = v.findViewById(R.id.editTextCryptoAdressCryptoEdit);
        editTextCryptoAdressCryptoEdit.setText(appControler.getCryptoAccountList().getCryptoAccountFromList(position).getWalletAddress());

        //Deklaration der Buttons und einige Unsichtbar machen sofern sie nicht benötigt werden.

        final Button buttonSaveEditsAccountCrypto = v.findViewById(R.id.buttonSaveEditsAccountCrypto);

        final Button deleteCryptoAccountButton = v.findViewById(R.id.deleteCryptoAccountButton);

        final Button deleteCryptoAccountYesButton = v.findViewById(R.id.deleteCryptoAccountYesButton);
        deleteCryptoAccountYesButton.setVisibility(View.INVISIBLE);

        final Button deleteCryptoAccountNoButton = v.findViewById(R.id.deleteCryptoAccountNoButton);
        deleteCryptoAccountNoButton.setVisibility(View.INVISIBLE);

        final TextView editCryptoAccountWarning= v.findViewById(R.id.editCryptoAccountWarning);
        editCryptoAccountWarning.setVisibility(View.INVISIBLE);

        /**
         * Klick auf Sichern-Button prüft ob Daten korrekt angegeben wurden.
         * Wenn ja werden Änderungen übernommen, Daten gesichert und Fragment Accounts geöffnet
         * Wenn nein, erscheint ein Hinweistext
         */

        buttonSaveEditsAccountCrypto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              if (editTextAccountNameCryptoEdit.getText().length() > 0 &&
                        String.valueOf(editTextBalanceCryptoEdit.getText()).length() > 0 && String.valueOf(editTextAccountCryptoBuyPriceCryptoEdit
                        .getText()).length() > 0) {

                    appControler.getCryptoAccountList().getCryptoAccountFromList(position).
                            setAccountName(String.valueOf(editTextAccountNameCryptoEdit.getText()));

                    appControler.getCryptoAccountList().getCryptoAccountFromList(position).
                            setBalance(Double.parseDouble(String.valueOf(editTextBalanceCryptoEdit.getText())));

                    appControler.getCryptoAccountList().getCryptoAccountFromList(position).
                            setCurrency(String.valueOf(editTextAccountCurrencyCryptoEdit.getText()));

                    appControler.getCryptoAccountList().getCryptoAccountFromList(position).
                            setBuyprice(Double.parseDouble(String.valueOf(editTextAccountCryptoBuyPriceCryptoEdit.getText())));

                    appControler.getCryptoAccountList().getCryptoAccountFromList(position).
                            setWalletAddress(String.valueOf(editTextCryptoAdressCryptoEdit.getText()));

                    saveAccountList();
                    openFragment(new CryptosHomeFragment(appControler));
                }
              else {
                  editCryptoAccountWarning.setText("Bitte vervollständigen Sie die Felder \"Name\" , \"Guthaben\" und \"Einkaufspreis\"");
                  editCryptoAccountWarning.setVisibility(View.VISIBLE);
              }
            }
        });


        /**
         * Klick auf Löschen zeigt Warnhinweis und Buttons "Ja" und "Nein"
         */
        deleteCryptoAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCryptoAccountWarning.setText("Wollen Sie wirklich das Konto \""
                        +appControler.getCryptoAccountList().getCryptoAccountFromList(position).getAccountName()
                        +"\" löschen?");
                deleteCryptoAccountNoButton.setVisibility(View.VISIBLE);
                deleteCryptoAccountYesButton.setVisibility(View.VISIBLE);
                editCryptoAccountWarning.setVisibility(View.VISIBLE);
                deleteCryptoAccountButton.setVisibility(View.INVISIBLE);
                buttonSaveEditsAccountCrypto.setVisibility(View.INVISIBLE);
            }
        });


        /**
         * Klick auf "Ja" löscht das Konto und speichert die Daten in der Cache
         * Anschließend wird Fragment AccountsHome geöffnet
         */
        deleteCryptoAccountYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appControler.getCryptoAccountList().deleteCryptoAccountFromList(position);
                saveAccountList();
                openFragment(new CryptosHomeFragment(appControler));
            }
        });

        /**
         * Klick auf nein bricht Löschvorgang ab und setzt die versteckten Elemente wieder VISIBLE
         */
        deleteCryptoAccountNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCryptoAccountNoButton.setVisibility(View.INVISIBLE);
                deleteCryptoAccountYesButton.setVisibility(View.INVISIBLE);
                editCryptoAccountWarning.setVisibility(View.INVISIBLE);
                deleteCryptoAccountButton.setVisibility(View.VISIBLE);
                buttonSaveEditsAccountCrypto.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }
}