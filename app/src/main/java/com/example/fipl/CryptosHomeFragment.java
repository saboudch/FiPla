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
import account.CryptoAccount;
import account.CryptoAccountList;
import adapter.AccountListAdapter;
import adapter.CryptoListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CryptosHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment wird eine Liste aller KryptoAccounts angezeigt.
 * Außerdem werden die Daten eines Accounts beim Klick auf ein Item im Header angezeigt.
 */
public class CryptosHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppControler appControler= new AppControler();
    private int pos;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird
     * @param appControler
     */
    public CryptosHomeFragment(AppControler appControler) {
        this.appControler=appControler;
    }

    //Diese Funktion öffnet ein Fragment
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    public CryptosHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CryptosHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CryptosHomeFragment newInstance(String param1, String param2) {
        CryptosHomeFragment fragment = new CryptosHomeFragment();
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
        View v= inflater.inflate(R.layout.fragment_cryptos_home, container, false);

        //Deklaration aller Elemente im Fragment
        final TextView cryptoNameTextView = v.findViewById(R.id.textView_cryptoAccount_header_AccountName);
        final TextView balanceTextView=v.findViewById(R.id.textView_cryptoAccount_header_balance);
        final TextView cryptoAdressTextView= v.findViewById(R.id.textView_cryptoAccount_header_adress);
        final TextView cryptoBuyPriceTextView= v.findViewById(R.id.textView_cryptoAccount_header_buyPrice);
        final ListView cryptoListView = v.findViewById(R.id.listViewAccountsCrypto);
        final Button buttoneditcryptoAccount = v.findViewById(R.id.button_edit_cryptoAccount);

        //Unsichtbarmachen solange kein Account ausgewählt
        buttoneditcryptoAccount.setVisibility(View.INVISIBLE);

        //Beim Klick auf diesen Button wird das BEarbeitenFragment geöffnet für Konto an der Position "pos"
        buttoneditcryptoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new EditCryptoAccountFragment(appControler,pos));
            }
        });

        //Adapter für ListView
        CryptoListAdapter cryptoListAdapter = new CryptoListAdapter(Objects.requireNonNull(getActivity()), R.layout.list_view_crypto_accounts_row, appControler.getCryptoAccountList().getCryptoAccountList() );

        /**
         * Klick auf ein Element der Liste
         * zeigt die Daten des Kontos im Header an
         */
        cryptoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buttoneditcryptoAccount.setVisibility(View.VISIBLE);
                pos=position;
                cryptoNameTextView.setText(appControler.getCryptoAccountList().getCryptoAccountFromList(position).getAccountName());
                balanceTextView.setText(Double.toString(appControler.getCryptoAccountList().getCryptoAccountFromList(position).getBalance())+ " " + appControler.getCryptoAccountList().getCryptoAccountFromList(position).getCurrency());
                cryptoAdressTextView.setText("Adresse: "+appControler.getCryptoAccountList().getCryptoAccountFromList(position).getWalletAddress());
                cryptoBuyPriceTextView.setText("Einkaufspreis pro Coin: " + Double.toString(appControler.getCryptoAccountList().getCryptoAccountFromList(position).getBuyprice()) + " EUR");

            }
        });

        //Adapter der Listview übergeben
        cryptoListView.setAdapter(cryptoListAdapter);
        //Addbutton
        Button addButton;
        addButton = v.findViewById(R.id.add_crypto_button);

        /**
         *
         * Klick auf Addbutton öffnet Fragment für das Hinzufügen eines Kryptokontos
         * AppControler wird für das weitere Bearbeiten der Listen übergeben
         */
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openFragment(new AddCryptoAccountFragment(appControler));
            }
        });

        return v;
    }
}