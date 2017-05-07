package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

/**
 * Created by bhatia on 4/10/2017.
 */

public class GetConfigurationsAsyncTask extends AsyncTask<Void, Void, ConfigurationsAndSettings> {

    @Override
    protected ConfigurationsAndSettings doInBackground(Void... params) {
        try {
            return BackendUtilitySingleton.getInstance().getConfigurations();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
