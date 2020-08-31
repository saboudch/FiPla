package account;

import java.util.ArrayList;

/**
 * Diese Klasse ist eine Liste aller Bankkonten
 */
public class BankAccountList {
    //Deklaration der Liste aller Bankkonten
    private ArrayList<BankAccount>bankAccountArrayList = new ArrayList<>();

    /**
     * In dieser Funktion wird der Liste ein Element hinzugefügt.
     * Übergeben wird hier ein Bankkonto das der Liste hinzugefügt wird.
     * @param bankAccount
     */
    public void addBankAccount(BankAccount bankAccount){
        this.bankAccountArrayList.add(bankAccount);
    }

    /**
     * Diese Funktion liefert ein Element der Liste an der Position "i" zurück
     * @param i
     * @return bankAccount
     */
    public BankAccount getBankAccountFromList(int i){
        return bankAccountArrayList.get(i);
    }

    /**
     * Diese Funktion löscht ein Element aus der Liste an der Position "i"
     * @param i
     */
    public void deleteBankAccountFromList(int i){
        bankAccountArrayList.remove(i);
    }

    /**
     * Diese Funktion liefert die Anzahl der Elemente der Liste zurück
     * @return size
     */
    public int getSizeOfBankAccountList(){
        return bankAccountArrayList.size();
    }

    /**
     * Diese Funktion gibt die Liste der Bankkonten zurück
     * @return list
     */
    public ArrayList<BankAccount> getBankAccountList() {
        return bankAccountArrayList;
    }

    /**
     * In dieser Funktion werden die Guthaben aller Konten der Liste addiert und zurückgegeben.
     * @return sumBalanceAllAccounts
     */
    public double getSumBalanceOfBankAccounts(){
        double sum=0;
        for (int i=0;i<getSizeOfBankAccountList();i++){
            sum+=getBankAccountFromList(i).getBalance();
        }
        return sum;
    }
}
