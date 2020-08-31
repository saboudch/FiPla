package account;

/**
 * Diese Klasse ist Elternklasse für alle Typen von Konten
 * Die Variablen "Name" "Guthaben" und "Währung" sind in allen Unterklasse automatisch enthalten
 */
public class Account {
    //Deklaration der Variablen "Name" "Guthaben" und "Währung"
    private String accountName;
    private double balance;
    private String currency;

    /**
     * In diesem Konstruktor werden die Werte für "Name" "Währung" und "Guthaben" übergeben und
     * den Variablen zugewiesen
     * @param accountName
     * @param balance
     * @param currency
     */
    public Account(String accountName, double balance, String currency){
        this.currency=currency;
        this.accountName=accountName;
        this.balance= balance;
    }

    /**
     * Diese Funktion gibt den Namen des Kontos zurück
     * @return accountName
     */
    public String getAccountName(){
        return accountName;
    }

    /**
     * Diese Funktion gibt das Guthaben des Kontos zurück
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Diese Funktion gibt die Währung des Kontos zurück
     * @return currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Diese Funktion überarbeitet die Variable "Guthaben"
     * @param balance
     */
    public void setBalance(double balance){
        this.balance=balance;
    }

    /**
     * Diese Funktion überarbeitet die Variable "Name"
     * @param accountName
     */
    public void setAccountName(String accountName){
        this.accountName=accountName;
    }

    /**
     * Diese Funktion überarbeitet die Variable "Währung"
     * @param currency
     */
    public void setCurrency(String currency){
        this.currency=currency;
    }
}
