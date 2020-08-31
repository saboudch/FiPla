package transaction;

/**
 * Diese Klasse ist ein Platzhalter für Umsätze
 * eines Bankkontos
 */
public class Transaction {
    //Deklaration der Variablen "Summe" und "Name"
    private double balance;
    private String name;

    /**
     * Konstruktor der Klasse. In der werden die Werte "Name" und "Summe" übergeben und zugewiesen
     * @param name
     * @param balance
     */
    public Transaction(String name, double balance){
        this.balance=balance;
        this.name=name;
    }

    /**
     * Diese Funktion liefert die Summe des Umsatzes zurück
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Diese Funktion liefert den Namen des Umsatzes zurück
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * In dieser Funktion wird die Summe überarbeitet
     * @param balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * In dieser Funktion wird der Name überarbeitet
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
