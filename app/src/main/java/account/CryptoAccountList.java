package account;

import java.util.ArrayList;

/**
 *
 * Diese Klasse ist eine Liste aller Konten für Kryptowährungen
 */
public class CryptoAccountList {
    //Liste aller Cryptokonten
    private ArrayList<CryptoAccount> cryptoAccountArrayList = new ArrayList<>();

    /**
     * In dieser Funktion wird ein Kryptokonto der ArrayList hinzugefügt
     * @param cryptoAccount
     */
    public void addCryptoAccount(CryptoAccount cryptoAccount){
        cryptoAccountArrayList.add(cryptoAccount);
    }

    /**
     * Diese Funktion gibt die Liste aller Konten für Kryptowährungen zurück
     * @return cryptoAccountArrayList
     */
    public ArrayList<CryptoAccount> getCryptoAccountList() {
        return cryptoAccountArrayList;
    }

    /**
     * Diese Funktion gibt ein Konto aus der Liste zurück.
     * Dabei wird der Parameter "i" übergeben. Das Konto an der Position "i" wird zurückgegeben
     * @param i
     * @return cryptoAccount
     */
    public CryptoAccount getCryptoAccountFromList(int i){
        return cryptoAccountArrayList.get(i);
    }

    /**
     * Diese Funktion löscht ein Element aus der Liste an der Stelle "i"
     * @param i
     */
    public void deleteCryptoAccountFromList(int i){
        cryptoAccountArrayList.remove(i);
    }

    /**
     * Diese Funktion gibt die Größe der Liste zurück
     * @return size
     */
    public int getSizeOfCryptoAccountList(){
        return cryptoAccountArrayList.size();
    }

    /**
     * Diese Funktion addiert alle Guthaben der Konten aus der Liste zusammen und gibt die Summe zurück
     * @return
     */
    public double getSumBalanceOfCryptoAccounts(){
        double sum=0;
        for (int i=0;i<getSizeOfCryptoAccountList();i++){
            sum+=getCryptoAccountFromList(i).getBalance();
        }
        return sum;
    }
}
