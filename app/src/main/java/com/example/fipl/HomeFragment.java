package com.example.fipl;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import Controler.AppControler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * In diesem Fragment wird eine Übersicht der Finanzen angezeigt
 * Dabei werden zwei Diagramme angezeigt. Eins für Guthaben und eins für Umsätze
 */
public class HomeFragment extends Fragment {

    private AppControler appControler = new AppControler();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public HomeFragment(AppControler appControler) {
        this.appControler=appControler;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v ;
        v = inflater.inflate(R.layout.fragment_home, container, false);
        setupPieChartAllAccounts(v);
        setupBarChartTransactions(v);
        return v;
    }

    /**
     * In dieser Funktion wird das Säulendiagramm Umsätze initialisiert.
     * Dieses Diagramm soll zeigen wie hoch die Ausgaben und die Einnahmen im Verhältnis zueinander sind.
     * Der Parameter v ist dabei die View die übergeben wird, damit es auch im richtigen Fragment abgebildet wird
     * @param v
     */
    private void setupBarChartTransactions(View v){
        BarChart barChart = v.findViewById(R.id.barChartTransactions);
        List<BarEntry> barEntryList = new ArrayList<>();
        ArrayList<String>labelnames = new ArrayList<>();

        int sumTransactionPlus=0;
        int sumTransactionMinus=0;
        for (int i=0;i<appControler.getBankAccountList().getSizeOfBankAccountList();i++){
            for (int j=0;j<appControler.getBankAccountList().getBankAccountFromList(i).getTransactionList().getSizeOfTransactionList();j++){
                if (appControler.getBankAccountList().getBankAccountFromList(i).getTransactionList().getTransactionFromList(j).getBalance() <0){
                    sumTransactionMinus-=appControler.getBankAccountList().getBankAccountFromList(i).getTransactionList().getTransactionFromList(j).getBalance();
                }
                else {
                    sumTransactionPlus+=appControler.getBankAccountList().getBankAccountFromList(i).getTransactionList().getTransactionFromList(j).getBalance();
                }
            }
        }

        labelnames.add("Ausgaben");
        labelnames.add("Einnahmen");
        barEntryList.add(new BarEntry(0,sumTransactionMinus));
        barEntryList.add(new BarEntry(1,sumTransactionPlus));


        BarDataSet barDataSet = new BarDataSet(barEntryList,"");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        Description description = new Description();
        description.setText("");

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelnames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelnames.size());


        barChart.animateY(2000);
        barChart.invalidate();

        barChart.setDescription(description);


    }

    /**
     * In dieser Funktion wird das Kreisdiagramm für alle Konten initialisiert.
     * Dabei wird für jedes Konto das Guthaben als eigener Eintrag ausgegeben.
     * Hier werden sowohl Bankkoten als auch Aktien und Kryptowährungen ausgegeben.
     * Wobei hier der Investitionsbetrag der einzelnen Aktien und Kryptowährungen abgebildet wird.
     * Der Parameter v ist dabei die View die übergeben wird, damit es auch im richtigen Fragment abgebildet wird
     * @param v
     */
    private void setupPieChartAllAccounts(View v) {

        List<PieEntry> pieEntryList = new ArrayList<>();

       int size = appControler.getStockAccountList().getSizeOfStockAccountList()+
                  appControler.getBankAccountList().getSizeOfBankAccountList()+
                  appControler.getCryptoAccountList().getSizeOfCryptoAccountList();

        float[] balance = new float[size];
        String[] name = new String[size];

        for (int i = 0; i< appControler.getBankAccountList().getSizeOfBankAccountList(); i++){
            balance[i]=(float) appControler.getBankAccountList().getBankAccountList().get(i).getBalance();
            name [i]= appControler.getBankAccountList().getBankAccountList().get(i).getAccountName();
            pieEntryList.add(new PieEntry(balance[i],name[i]));
        }

        for (int j= 0;j<appControler.getCryptoAccountList().getSizeOfCryptoAccountList();j++){
            balance[j+appControler.getBankAccountList().getSizeOfBankAccountList()]=
                    (float) appControler.getCryptoAccountList().getCryptoAccountFromList(j).getInvestBalance();
            name [j+appControler.getBankAccountList().getSizeOfBankAccountList()]=
                    appControler.getCryptoAccountList().getCryptoAccountFromList(j).getAccountName();
            pieEntryList.add(new PieEntry(balance[j+appControler.getBankAccountList().getSizeOfBankAccountList()
                    ],name[j+appControler.getBankAccountList().getSizeOfBankAccountList()]));
        }

        for (int k=0;k<appControler.getStockAccountList().getSizeOfStockAccountList();k++){
            balance[k+appControler.getBankAccountList().getSizeOfBankAccountList()
                        +appControler.getCryptoAccountList().getSizeOfCryptoAccountList()]=(float)
                        appControler.getStockAccountList().getStockAccountFromList(k).getInvestBalance();
            name[k+appControler.getBankAccountList().getSizeOfBankAccountList()
                        +appControler.getCryptoAccountList().getSizeOfCryptoAccountList()]=
                        appControler.getStockAccountList().getStockAccountFromList(k).getAccountName();
            pieEntryList.add(new PieEntry(
                    balance[k+appControler.getBankAccountList().getSizeOfBankAccountList()
                            +appControler.getCryptoAccountList().getSizeOfCryptoAccountList()],
                    name[k+appControler.getBankAccountList().getSizeOfBankAccountList()
                            +appControler.getCryptoAccountList().getSizeOfCryptoAccountList()]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntryList,"");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(10f);
        dataSet.setValueTextColor(Color.WHITE);
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(20f);
        PieChart chart = v.findViewById(R.id.pieChartBankAccounts);
        chart.setData(pieData);
        Description description= new Description();
        description.setText("");
        chart.setDescription(description);
        chart.invalidate();
        chart.setHoleColor(ColorTemplate.COLOR_NONE);
        chart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        chart.setTransparentCircleRadius(55f);

    }

}