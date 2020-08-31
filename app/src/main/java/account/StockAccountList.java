package account;

import java.util.ArrayList;

/**
 *
 * Diese Klasse ist ein Platzhalter für die Liste aller Aktienkonten
 */
public class StockAccountList {

    /**
     * Deklaration der Liste für Aktienkonten
     */
    private ArrayList<StockAccount> stockAccountArrayList = new ArrayList<>();

    /**
     * Funktion für das Hinzufügen eines Aktiekontos in die Liste
     * @param stockAccount
     */
    public void addStockAccount(StockAccount stockAccount){
        stockAccountArrayList.add(stockAccount);
    }

    /**
     * Funktion die die Liste der Aktienkonten zurückgibt
     * @return ArrayList
     */
    public ArrayList<StockAccount> getStockAccountList() {
        return stockAccountArrayList;
    }

    /**
     * Funktion die ein Element der Liste für Aktienkonten zurückgibt.
     * Hier wird "i" übergeben um an der Stelle "i" der Liste ein Aktienkonto zu bekommen
     * @param i
     * @return stockAccount
     */
    public StockAccount getStockAccountFromList(int i){
        return stockAccountArrayList.get(i);
    }

    /**
     * Funktion die ein Element der Liste für Aktienkonten löscht.
     * Hier wird "i" übergeben um an der Stelle "i" das Konto zu löschen
     * @param i
     */
    public void deleteStockAccountFromList(int i){
        stockAccountArrayList.remove(i);
    }

    /**
     * Funktion die die Anzahl der Elemente in der Liste zurückgibt
     * @return size
     */
    public int getSizeOfStockAccountList(){
        return stockAccountArrayList.size();
    }

    /**
     * Funktion die das Guthaben aller Aktienkonten der Liste summiert und zurückgibt.
     * @return sum
     */
    public double getSumBalanceOfStockAccounts(){
        double sum=0;
        for (int i=0;i<getSizeOfStockAccountList();i++){
            sum+=getStockAccountFromList(i).getBalance();
        }
        return sum;
    }
}
