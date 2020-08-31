package account;

/**
 * Diese Klasse ist ein PLatzhalter für Cryptokonten
 * Sie vererbt die Variablen der Klasse Account
 */
public class CryptoAccount extends Account {
    // Deklaration der Variablen Einkaufspreis und Walletadresse
        private double buyprice;
        private String walletAddress;

    /**
     * Das ist der Konstruktor der Klasse. Darin werden die Werte für die
     * Variablen "Name" "Guthaben" "Währung" "Wallet-Adresse" und "Einkaufspreis" übergeben und gesetzt.
     * Variablen "Name" "Guthaben" und "Währung" werden von der Klasse Account vererbt
     * @param accountName
     * @param balance
     * @param currency
     * @param walletAddress
     * @param buyprice
     */
    public CryptoAccount(String accountName, double balance, String currency, String walletAddress, double buyprice){
            super(accountName, balance,currency);
            this.walletAddress=walletAddress;
            this.buyprice=buyprice;
        }

    /**
     * Diese Funktion gibt die investierte Summe zurück.
     * Diese errechnet sich aus Einkaufspreis*Guthaben
     * @return investBalance
     */
    public double getInvestBalance(){
        return buyprice*getBalance();
    }

    /**
     * Diese Funktion gibt den Einkaufspreis für einen Coin der Cryptowährung zurück
     * @return buyprice
     */
    public double getBuyprice() {
        return buyprice;
    }

    /**
     * In dieser Funktion wird der Einkaufspreis gesetzt bzw. bearbeitet
     * @param buyprice
     */
    public void setBuyprice(double buyprice) {
        this.buyprice = buyprice;
    }

    /**
     * Diese Funktion gibt die Wallet-Adresse zurück
     * @return walletAdress
     */
    public String getWalletAddress(){
        return walletAddress;
    }

    /**
     * In dieser Funktion wird die Walletadresse gesetzt bzw. überarbeitet
     * @param walletAddress
     */
    public void setWalletAddress(String walletAddress){
            this.walletAddress=walletAddress;
        }
}
