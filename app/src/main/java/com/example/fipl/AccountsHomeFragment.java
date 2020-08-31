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
import adapter.AccountListAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountsHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountsHomeFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  AppControler appControler = new AppControler();
    private int pos;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Konstruktor in dem der AppControler für die weitere Bearbeitung aller Listen übergeben wird
     * @param appControler
     */
    public AccountsHomeFragment(AppControler appControler) {
        this.appControler=appControler;
    }

    /**
     * Diese Funktion öffnet das mitgegebene Fragment
     * @param fragment
     */
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    /**
     * Leerer Konstruktor
     */
    public AccountsHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountsHome.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountsHomeFragment newInstance(String param1, String param2) {
        AccountsHomeFragment fragment = new AccountsHomeFragment();
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

        // Inflate the layout for this
        View v= inflater.inflate(R.layout.fragment_accounts_home, container, false);

        //Deklaration aller Elemente im Fragment
        final TextView banknameTextView = v.findViewById(R.id.textView_bankaccountHeader_bankname);
        final TextView ibanTextView=v.findViewById(R.id.textView_bankaccountHeader_bankiban);
        final TextView balanceBankTextView=v.findViewById(R.id.textView_bankaccountHeader_bankbalance);
        final TextView bicTextView=v.findViewById(R.id.textView_bankaccountHeader_bankbic);
        final TextView bankaccountnameTextView= v.findViewById(R.id.textView_bankaccountHeader_bankaccountname);
        final ListView accountListView = v.findViewById(R.id.listViewAccounts);
        final Button getTransactionBankaccountButton = v.findViewById(R.id.button_show_transactions_bankaccount);
        final Button addButton = v.findViewById(R.id.add_button);
        final Button editAccountButton = v.findViewById(R.id.edit_bankaccount_button);

        /**
         * Unsichtbarmachen von Elemente im Fragment.
         * Diese werden sichtbar gemacht, sobald ein Konto aus der Liste im Fragment
         * zur Ansicht ausgewählt wurde
         */
        getTransactionBankaccountButton.setVisibility(View.INVISIBLE);
        editAccountButton.setVisibility(View.INVISIBLE);

        //Adapter für Bankkontenliste
        AccountListAdapter accountListAdapter = new AccountListAdapter(Objects.requireNonNull(getActivity()), R.layout.list_view_accounts_row, appControler.getBankAccountList().getBankAccountList() );

        accountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * In dieser Funktion werden beim Klicken auf ein Element der ListView
             * Elemente (Buttons "Edit" und "Umsätze") sichtbar gemacht und die TextViews mit Daten
             * des ausgewählten Kontos befüllt
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                banknameTextView.setText(appControler.getBankAccountList().getBankAccountFromList(position).getBankName());
                ibanTextView.setText("IBAN: "+appControler.getBankAccountList().getBankAccountFromList(position).getIban());
                balanceBankTextView.setText(Double.toString(appControler.getBankAccountList().getBankAccountFromList(position).getBalance())+ " " + appControler.getBankAccountList().getBankAccountFromList(position).getCurrency());
                bicTextView.setText("BIC: "+appControler.getBankAccountList().getBankAccountFromList(position).getBic());
                bankaccountnameTextView.setText(appControler.getBankAccountList().getBankAccountFromList(position).getAccountName());
                getTransactionBankaccountButton.setVisibility(View.VISIBLE);
                editAccountButton.setVisibility(View.VISIBLE);

            }
        });

        //Adapter wird der ListView übergeben
        accountListView.setAdapter(accountListAdapter);

        //Beim Klicken auf den Hinzufügen-Button wird das KontoHinzufügenFragment geöffnet
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openFragment(new AddAccountFragment(appControler));
            }
        });


        //Klick auf den Bearbeiten-Button öffnet KontobearbeitenFragment
        editAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFragment(new EditBankAccountFragment(appControler,pos));
            }
        });

        //Klick auf Umsätze-Button öffnet UmsätzeFragment
        getTransactionBankaccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openFragment(new TransactionBankAccountFragment(appControler,pos));
            }
        });

        return v;
    }
}