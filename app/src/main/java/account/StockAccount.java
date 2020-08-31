package account;

/**
 * Diese Klasse ist ein Platzhalter für Aktienkonten
 * Sie vererbt die Variablen der Klasse Account
 */

public class StockAccount extends Account{
    //Deklaration der privaten Variablen Einkaufspreis und Börsenname
    private double buyprice;
    private String exchangeName;

    /**
     * Konstruktor der Klasse in dem die Werte für Kontoname, Guthaben, Börsenname und Einkaufspreis übergeben werden
     * Diese Werte werden den Variablen der Klasse zugewiesen
     * Die Variablen Währung, Guthaben und Kontoname sind in der Klasse Account deklariert und werden von der vererbt
     * @param accountName
     * @param balance
     * @param exchangeName
     * @param buyprice
     */
    public StockAccount (String accountName,double balance, String exchangeName, double buyprice){

        super(accountName,balance,"Anteile");
        this.exchangeName=exchangeName;
        this.buyprice=buyprice;
    }

    /**
     * Funktion in der der Einkaufspreis gesetzt werden kann.
     * @param buyprice
     */
    public void setBuyprice(double buyprice){
        this.buyprice=buyprice;
    }

    /**
     * Funktion in der die investierte Summe aus Einkaufspreis und
     * Anzahl an Anteile ermittelt und zurückgegeben wird
     * @return investBalance
     */
    public double getInvestBalance(){
        return buyprice*getBalance();
    }

    /**
     * Funktion in der der Einkaufspreis der Aktie zurückgegeben wird
     * @return buyPrice
     */
    public double getBuyprice(){
        return buyprice;
    }

    /**
     * Funktion in der der Börsenname gesetzt bzw. geändert werden kann.
     * @param exchangeName
     */
    public void setExchangeName(String exchangeName){
        this.exchangeName=exchangeName;
    }

    /**
     * Funktion in der der Börsenname zurückgegeben wird
     * @return exchangeName
     */
    public String getExchangeName(){
        return exchangeName;
    }

}
