package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B;

import android.app.Application;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Account;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.ConfigurationsAndSettings;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.GetAccountDetailsAsyncTask;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.GetConfigurationsAsyncTask;
import com.estimote.sdk.EstimoteSDK;
import java.util.concurrent.ExecutionException;

public class MyApplication extends Application {
    private Account userAccount = null;
    private double chargeValue;
    private long chargeEveryIntervalInMSecs;

    public Account getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }

    public double getChargeValue() { return chargeValue; }
    public void setChargeValue(double chargeValue) {
        this.chargeValue = chargeValue;
    }

    public long getChargeEveryIntervalInMSecs() { return chargeEveryIntervalInMSecs; }
    public void setChargeEveryIntervalInMSecs(long chargeEveryIntervalInSecs) {
        this.chargeEveryIntervalInMSecs = chargeEveryIntervalInSecs*1000;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setChargeValue(this.getResources().getInteger(R.integer.charge_value));
        setChargeEveryIntervalInMSecs(this.getResources().getInteger(R.integer.charge_every_interval_in_secs));

        try {
            ConfigurationsAndSettings config = new GetConfigurationsAsyncTask().execute().get();
            if (config!=null)
            {
                setChargeValue(config.getChargeValue());
                setChargeEveryIntervalInMSecs(config.getChargeEveryIntervalInSecs());
            }

            String accountID = getText(R.string.default_account_id).toString();
            setUserAccount(new GetAccountDetailsAsyncTask().execute(accountID).get());
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
