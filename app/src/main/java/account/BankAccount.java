package account;

import transaction.Transaction;
import transaction.TransactionList;

/**
 * Diese Klasse ist ein Platzhalter für Bankkonten
 * Sie vererbt die Variablen "Name" "Guthaben" und "Währung" aus der Klasse Account
 * Außerdem beinhaltet sie auch eine Liste der Umsätze.
 */
public class BankAccount extends Account {

    //Deklaration der Variablen "Bankname" "IBAN" "BIC"  und "Umsatzliste"
    private String bankName;
    private String iban;
    private String bic;
    private TransactionList transactionList = new TransactionList();

    /**
     * Konstruktor der Klasse. Hier werden die Werte für "Name" "Guthaben" "Währung" "Bankname" "IBAN" und "BIC" übergeben
     * und den Variablen zugewiesen. Die Variablen "Name" "Guthaben" und "WÄhrung" werden von der Klasse Account vererbt
     * @param accountName
     * @param balance
     * @param currency
     * @param bankName
     * @param iban
     * @param bic
     */
    public BankAccount (String accountName,double balance, String currency, String bankName, String iban, String bic ){
        super(accountName,balance,currency);
        this.bankName=bankName;
        this.iban=iban;
        this.bic=bic;
    }

    /**
     * In dieser Funktion wird der Bankname gesetzt bzw. überarbeitet
     * @param bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * In dieser Funktion wird die IBAN gesetzt bzw. überarbeitet
     * @param iban
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * In dieser Funktion wird die BIC gesetzt bzw überarbeitet
     * @param bic
     */
    public void setBic(String bic) {
        this.bic = bic;
    }

    /**
     * Diese Funktion liefert den Banknamen zurück
     * @return bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Diese Funktion liefert die BIC zurück
     * @return BIC
     */
    public String getBic() {
        return bic;
    }

    /**
     * Diese Funktion liefert die IBAN zurück
     * @return iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * Diese Funktion liefert die Liste der Umsätze zurück
     * @return transactionList
     */
    public TransactionList getTransactionList(){
        return transactionList;
    }

    /**
     * In dieser Funktion wird ein Umsatz der Umsatzliste hinzugefügt.
     * Dabei wird das Guthaben des Kontos aktualisiert.
     * @param transaction
     */
    public void addTransaction(Transaction transaction){
        setBalance(getBalance()+transaction.getBalance());
        transactionList.addTransaction(transaction);
    }

    /**
     * In dieser Funktion wird ein Umsatz aus der Umsatzliste an der Position "i" gelöscht
     * Dabei wird das Guthaben des Kontos aktualisiert.
     * @param position
     */
    public void  deleteTransaction(int position){
        setBalance(getBalance()-transactionList.getTransactionFromList(position).getBalance());
        transactionList.deleteTransactionFromList(position);
    }

    /**
     * In dieser Funktion werden die Daten eines Umsatzes an der Position "i" überarbeitet.
     * Dabei wird das Guthaben des Kontos mit Hilfe der Variable "balance" aktualisiert.
     * @param position
     * @param name
     * @param balance
     */
    public void setTransactionData(int position, String name, double balance){
        transactionList.getTransactionFromList(position).setName(name);
        setBalance(getBalance()-transactionList.getTransactionFromList(position).getBalance());
        transactionList.getTransactionFromList(position).setBalance(balance);
        setBalance(getBalance()+balance);
    }

}
