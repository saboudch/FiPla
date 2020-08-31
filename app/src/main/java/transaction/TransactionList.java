package transaction;

import java.util.ArrayList;

/**
 * Diese Klasse ist eine Liste aller Umsätze für ein Bankkonto
 */
public class TransactionList {
    //Deklaration der Liste aller Umsätze
    private ArrayList<Transaction> transactionArrayList = new ArrayList<>();

    /**
     * Hier wird ein Umsatz der Liste hinzugefügt
     * @param bankAccount
     */
    public void addTransaction(Transaction bankAccount){
        transactionArrayList.add(bankAccount);
    }

    /**
     * Diese Funktion liefert ein Umsatz aus der Liste an der Stelle "i" zurück
     * @param i
     * @return transaction
     */
    public Transaction getTransactionFromList(int i){
        return transactionArrayList.get(i);
    }

    /**
     * Diese Funktion löscht einen Umsatz aus der Liste an der Stelle "i"
     * @param i
     */
    public void deleteTransactionFromList(int i){
        transactionArrayList.remove(i);
    }

    /**
     * Diese Funktion liefert die Anzahl aller Elemente der Liste zurück
     * @return size
     */
    public int getSizeOfTransactionList(){
        return transactionArrayList.size();
    }

    /**
     * Diese Funktion liefert die Liste aller Umsätze zurück
     * @return list
     */
    public ArrayList<Transaction> getTransactionList() {
        return transactionArrayList;
    }
}
