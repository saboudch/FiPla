package Controler;

import account.BankAccountList;
import account.CryptoAccountList;
import account.StockAccountList;
import transaction.TransactionList;

/**
 * Diese Klasse dient als Controller für die App
 * Über diese Klasse kann auf alle Listen zugegriffen werden.
 */
public class AppControler {

    private BankAccountList bankAccountList = new BankAccountList();
    private CryptoAccountList cryptoAccountList = new CryptoAccountList();
    private StockAccountList stockAccountList = new StockAccountList();

    public BankAccountList getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(BankAccountList bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public CryptoAccountList getCryptoAccountList() {
        return cryptoAccountList;
    }

    public void setCryptoAccountList(CryptoAccountList cryptoAccountList) {
        this.cryptoAccountList = cryptoAccountList;
    }

    public StockAccountList getStockAccountList() {
        return stockAccountList;
    }

    public void setStockAccountList(StockAccountList stockAccountList) {
        this.stockAccountList = stockAccountList;
    }
}
