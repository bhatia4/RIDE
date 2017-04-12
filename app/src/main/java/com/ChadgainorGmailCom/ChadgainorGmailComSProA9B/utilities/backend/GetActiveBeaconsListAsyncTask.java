package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

/**
 * Created by bhatia on 4/10/2017.
 */

public class GetActiveBeaconsListAsyncTask extends AsyncTask<Void, Void, List<Beacon>> {

    @Override
    protected List<Beacon> doInBackground(Void... params) {
        try {
            return BackendUtilitySingleton.getInstance().getListOfActiveBeacons();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
