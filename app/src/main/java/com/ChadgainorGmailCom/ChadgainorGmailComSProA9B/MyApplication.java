package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Account;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.BackendUtilitySingleton;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.GetAccountDetailsAsyncTask;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.GetListOfAccountsAsyncTask;
import com.estimote.sdk.EstimoteSDK;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MyApplication extends Application {
    private Account userAccount = null;
    private double randomDbl;

    public Account getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }

    public double getRandomDbl() {
        return randomDbl;
    }
    public void setRandomDbl(double randomDbl) {
        this.randomDbl = randomDbl;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            String phoneNumber = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
            String accountID = getText(R.string.default_account_id).toString();

            if (phoneNumber!=null && phoneNumber.trim().length()>0)
            {
                phoneNumber = phoneNumber.trim();
                List<Account> listOfAccounts = new GetListOfAccountsAsyncTask().execute().get();
                for (Account currAccount : listOfAccounts)
                {
                    if (currAccount.getPhone().equals(phoneNumber))
                    {
                        accountID = currAccount.getAccountID().getOid();
                        break;
                    }
                }
            }
            MyApplication.this.userAccount = new GetAccountDetailsAsyncTask().execute(accountID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        EstimoteSDK.initialize(getApplicationContext(), getText(R.string.appId2).toString(), getText(R.string.appToken2).toString());

        // uncomment to enable debug-level logging
        // it's usually only a good idea when troubleshooting issues with the Estimote SDK
//        EstimoteSDK.enableDebugLogging(true);
    }
}
