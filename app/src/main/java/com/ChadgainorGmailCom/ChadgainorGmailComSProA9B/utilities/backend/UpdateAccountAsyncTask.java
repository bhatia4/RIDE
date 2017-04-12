package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by bhatia on 4/10/2017.
 */

public class UpdateAccountAsyncTask extends AsyncTask<Transaction, Void, Account> {

    @Override
    protected Account doInBackground(Transaction... transaction) {
        Account newAccount = null;
        Transaction currTransaction = transaction[0];
        try {
            Account oldAccount = BackendUtilitySingleton.getInstance().getAccountDetails(currTransaction.getAccountID());
            if (oldAccount!=null) {
                BackendUtilitySingleton.getInstance().addTransactionToAccount(currTransaction);

                double newBalance = oldAccount.getBalance() - currTransaction.getAmtCharged();
                newAccount = BackendUtilitySingleton.getInstance().updateBalanceForAccount(oldAccount, newBalance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return newAccount;
    }
}
