package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.estimote.BeaconID;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.estimote.EstimoteCloudBeaconDetails;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.estimote.EstimoteCloudBeaconDetailsFactory;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.estimote.ProximityContentManager;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Account;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Beacon;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.GetAccountDetailsAsyncTask;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.GetActiveBeaconsListAsyncTask;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.GetListOfAccountsAsyncTask;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Transaction;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.UpdateAccountAsyncTask;
import com.estimote.sdk.SystemRequirementsChecker;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int BACKGROUND_COLOR_BLACK = android.graphics.Color.rgb(0, 0, 0);
    private static final int BACKGROUND_COLOR_ORANGE = android.graphics.Color.rgb(255, 78, 0);
    private static final int PERMISSION_READ_STATE = 1;

    private ProximityContentManager proximityContentManager;
    private Handler mHandler = new Handler();
    private boolean continueCharging = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // We do not have this permission. Let's ask the user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }
        else
            acquirePhoneNumber();

        Typeface type = Typeface.createFromAsset(this.getAssets(),"fonts/Loomis_Sans.ttf");
        ((TextView) findViewById(R.id.textViewAccountBalance)).setTypeface(type);
        ((TextView) findViewById(R.id.lastChargeTextView)).setTypeface(type);

        updateInfoTextViews(((MyApplication) this.getApplication()).getUserAccount(), null);

        final ImageButton button = (ImageButton) findViewById(R.id.imageViewButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                continueCharging = !continueCharging;

                if (continueCharging) {
                    setTitle(getText(R.string.app_name));
                    proximityContentManager.getNearestBeaconManager().updateNearestBeacon(null);
                }
                else
                    setTitle(getTitle()+" - disabled");
            }
        });

        final List<Beacon> listOfActiveBeaconsFromBackend;
        try {
            listOfActiveBeaconsFromBackend = new GetActiveBeaconsListAsyncTask().execute().get();

            BeaconID[] beaconsToScan = new BeaconID[listOfActiveBeaconsFromBackend.size()];
            int i =0;
            for(Beacon currBeacon : listOfActiveBeaconsFromBackend) {
                beaconsToScan[i++] = new BeaconID(currBeacon.getEstimoteBeaconID().getProximityUuid(),
                        Integer.parseInt(currBeacon.getEstimoteBeaconID().getMajor()),
                        Integer.parseInt(currBeacon.getEstimoteBeaconID().getMinor()));
            }

            final BeaconTrackingRunnable runnable = new BeaconTrackingRunnable(listOfActiveBeaconsFromBackend);
            proximityContentManager = new ProximityContentManager(this, Arrays.asList(beaconsToScan),
                    new EstimoteCloudBeaconDetailsFactory());
            proximityContentManager.getNearestBeaconManager().getBeaconManager().setForegroundScanPeriod(750, ((MyApplication) this.getApplication()).getChargeEveryIntervalInMSecs());

            proximityContentManager.setListener(new ProximityContentManager.Listener() {
                @Override
                public void onContentChanged(final Object content) {
                    if (content != null && continueCharging) {
                        runnable.setBeaconContent((EstimoteCloudBeaconDetails) content);

                        findViewById(R.id.relativeLayout).setBackgroundColor(BACKGROUND_COLOR_ORANGE);
                        findViewById(R.id.imageViewButton).setVisibility(View.GONE);
                        findViewById(R.id.imageViewAlt).setVisibility(View.VISIBLE);

                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.postDelayed(runnable, 1250);
                        proximityContentManager.getNearestBeaconManager().updateNearestBeacon(null);
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
        updateInfoTextViews(((MyApplication) this.getApplication()).getUserAccount(), null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proximityContentManager.destroy();
    }

    private void updateInfoTextViews(Account account, String charge)
    {
        if (account!=null) {
            ((TextView) findViewById(R.id.textViewAccountBalance)).setText(getText(R.string.label_balance).toString() + " " + new DecimalFormat("#0").format(account.getBalance()));
        }

        if (charge!=null) {
            ((TextView) findViewById(R.id.lastChargeTextView)).setText(getText(R.string.label_last_charge).toString() + " -" + charge );
        }
    }

    //override onRequestPermissionsResult in your activity and see if the permission was granted. If it was, you can go ahead and preform the dangerous action.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!

                    acquirePhoneNumber();
                } else {
                    // permission denied
                }
                return;
            }

        }
    }

    private void acquirePhoneNumber() {
        try {
            String phoneNumber = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
            if (phoneNumber != null && phoneNumber.trim().length() > 0) {
                phoneNumber = phoneNumber.trim();
                List<Account> listOfAccounts = new GetListOfAccountsAsyncTask().execute().get();
                for (Account currAccount : listOfAccounts) {
                    if (currAccount.getPhone().equals(phoneNumber)) {
                        String accountID = currAccount.getAccountID().getOid();
                        ((MyApplication) MainActivity.this.getApplication()).setUserAccount(new GetAccountDetailsAsyncTask().execute(accountID).get());
                        updateInfoTextViews(((MyApplication) this.getApplication()).getUserAccount(), null);

                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class BeaconTrackingRunnable implements Runnable
    {
        private List<Beacon> listOfActiveBeaconsFromBackend;
        private EstimoteCloudBeaconDetails beaconContent;

        public BeaconTrackingRunnable(List<Beacon> listOfActiveBeaconsFromBackend) {
            this.listOfActiveBeaconsFromBackend = listOfActiveBeaconsFromBackend;
        }

        public void run() {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

            BeaconID beaconDetails = ((EstimoteCloudBeaconDetails) beaconContent).getBeaconID();
            String currbeaconID = null;
            for (Beacon currBeacon: listOfActiveBeaconsFromBackend)
            {
                if ( currBeacon.getEstimoteBeaconID().equals(new Beacon().new EstimoteBeaconID(beaconDetails.getProximityUUID().toString().toUpperCase(), String.valueOf(beaconDetails.getMajor()), String.valueOf((beaconDetails.getMinor())))) )
                {
                    currbeaconID = currBeacon.getBeaconID().getOid();
                    break;
                }
            }

            GPSTracker gps = new GPSTracker(MainActivity.this);
            double gpsLat = 0.0d;
            double gpsLong = 0.0d;
            if (gps.canGetLocation())
            {
                gpsLat = gps.getLatitude();
                gpsLong = gps.getLongitude();
            }

            Transaction transc = new Transaction(((MyApplication) MainActivity.this.getApplication()).getUserAccount().getAccountID().getOid(), new Date(),
                    ((MyApplication) MainActivity.this.getApplication()).getChargeValue(), gpsLat, gpsLong, currbeaconID);
            new UpdateAccountAsyncTask() {
                @Override
                protected void onPostExecute(Account updatedAccount) {
                    ((MyApplication) MainActivity.this.getApplication()).setUserAccount(updatedAccount);
                    updateInfoTextViews(((MyApplication) MainActivity.this.getApplication()).getUserAccount(),
                            new DecimalFormat("#0").format(((MyApplication) MainActivity.this.getApplication()).getChargeValue()));
                }
            }.execute(transc);

            findViewById(R.id.relativeLayout).setBackgroundColor(BACKGROUND_COLOR_BLACK);
            findViewById(R.id.imageViewAlt).setVisibility(View.GONE);
            findViewById(R.id.imageViewButton).setVisibility(View.VISIBLE);
        }


        public void setBeaconContent(EstimoteCloudBeaconDetails beaconContent) {
            this.beaconContent = beaconContent;
        }
    }
}
