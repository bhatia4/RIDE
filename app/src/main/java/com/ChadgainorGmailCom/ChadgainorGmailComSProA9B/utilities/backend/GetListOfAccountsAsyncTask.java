package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

/**
 * Created by bhatia on 4/10/2017.
 */

public class GetListOfAccountsAsyncTask extends AsyncTask<Void, Void, List<Account>> {

    @Override
    protected List<Account> doInBackground(Void... params) {
        try {
            return BackendUtilitySingleton.getInstance().getListOfAccounts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
