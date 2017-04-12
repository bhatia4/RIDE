package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by bhatia on 4/10/2017.
 */

public class GetAccountDetailsAsyncTask extends AsyncTask<String, Void, Account> {

    @Override
    protected Account doInBackground(String... params) {
        try {
            return BackendUtilitySingleton.getInstance().getAccountDetails(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
