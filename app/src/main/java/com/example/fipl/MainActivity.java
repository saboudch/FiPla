package com.example.fipl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import Controler.AppControler;
import account.BankAccountList;
import account.CryptoAccountList;
import account.StockAccountList;

/**
 * In der MainActivity wird als Hauptfragment das HomeFragment geöffnet
 * Außerdem wird am unteren Rand ein Menu angezeigt der beim Klick auf die Menuitems das Fragment wechselt
 * Zu Beginn werden auch die in der Cache gespeicherten Daten geladen und die Listen gesetzt.
 */
public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    private AppControler appControler = new AppControler();

    //Daten werden geladen und die Listen gesetzt.
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedLists",MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonBankAccountList = sharedPreferences.getString("bankAccountList","");
        String jsonCryptoWalletList = sharedPreferences.getString("cryptoAccountList","");
        String jsonStockList= sharedPreferences.getString("stockAccountList","");
        if (jsonBankAccountList.length()>0){
            appControler.setBankAccountList(gson.fromJson(jsonBankAccountList, BankAccountList.class));
        }
        if (jsonCryptoWalletList.length()>0){
            appControler.setCryptoAccountList(gson.fromJson(jsonCryptoWalletList, CryptoAccountList.class));
        }
        if (jsonStockList.length()>0){
            appControler.setStockAccountList(gson.fromJson(jsonStockList,StockAccountList.class));
        }
    }

    //Funktion zum öffnen eines Fragments
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFragment(new HomeFragment(appControler));
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("RestrictedApi")
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_menu_home:
                            openFragment(new HomeFragment(appControler));
                            return true;
                        case R.id.navigation_accounts_home:
                            openFragment(new AccountsHomeFragment(appControler));
                            return true;
                        case R.id.navigation_wallet_home:
                            openFragment(new StockHomeFragment(appControler));
                            return true;
                        case R.id.navigation_transaction_home:
                            openFragment(new TransactionHomeFragment(appControler));
                            return true;
                        case R.id.navigation_crypto_home:
                            openFragment(new CryptosHomeFragment(appControler));
                            return true;
                    }
                    return false;
                }
            };
}