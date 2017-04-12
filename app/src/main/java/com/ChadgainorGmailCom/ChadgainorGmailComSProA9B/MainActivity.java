package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.estimote.BeaconID;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.estimote.EstimoteCloudBeaconDetails;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.estimote.EstimoteCloudBeaconDetailsFactory;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.estimote.ProximityContentManager;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Account;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Beacon;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.GetActiveBeaconsListAsyncTask;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Transaction;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.UpdateAccountAsyncTask;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
        BACKGROUND_COLORS.put(Color.LEMON_TART, android.graphics.Color.rgb(204, 204, 0));
        BACKGROUND_COLORS.put(Color.SWEET_BEETROOT, android.graphics.Color.rgb(153, 0, 76));
        BACKGROUND_COLORS.put(Color.CANDY_FLOSS, android.graphics.Color.rgb(255, 204, 229));
    }

    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);

    private ProximityContentManager proximityContentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateInfoTextViews(((MyApplication) this.getApplication()).getUserAccount());

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: add GPS & beaconID
                Transaction transc = new Transaction(((MyApplication) MainActivity.this.getApplication()).getUserAccount().getAccountID().getOid(), new Date(),
                                                    ((MyApplication) MainActivity.this.getApplication()).getRandomDbl(), 0.0d, 0.0d, null);
                new UpdateAccountAsyncTask() {
                    @Override
                    protected void onPostExecute(Account updatedAccount) {
                        ((MyApplication) MainActivity.this.getApplication()).setUserAccount(updatedAccount);
                        updateInfoTextViews(((MyApplication) MainActivity.this.getApplication()).getUserAccount());

                        ((TextView) findViewById(R.id.lastChargeTextView)).setText(getText(R.string.label_last_charge).toString() + new DecimalFormat("#0.00").format(((MyApplication) MainActivity.this.getApplication()).getRandomDbl()));
                    }
                }.execute(transc);

                showHideTransactionButton(false);
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

            proximityContentManager = new ProximityContentManager(this, Arrays.asList(beaconsToScan),
                    new EstimoteCloudBeaconDetailsFactory());
            proximityContentManager.setListener(new ProximityContentManager.Listener() {
                @Override
                public void onContentChanged(Object content) {
                    String text;
                    Integer backgroundColor;
                    if (content != null) {
                        EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                        text = "You're in " + beaconDetails.getBeaconName() + "'s range!";
                        backgroundColor = BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());

                        if (backgroundColor == BACKGROUND_COLORS.get(Color.LEMON_TART))
                            showHideTransactionButton(true);
                        else
                            showHideTransactionButton(false);
                    } else {
                        text = "No beacons in range.";
                        backgroundColor = null;
                    }
                    ((TextView) findViewById(R.id.textView)).setText(text);
                    findViewById(R.id.relativeLayout).setBackgroundColor(
                            backgroundColor != null ? backgroundColor : BACKGROUND_COLOR_NEUTRAL);
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
        updateInfoTextViews(((MyApplication) this.getApplication()).getUserAccount());
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

    private void updateInfoTextViews(Account account)
    {
        if (account!=null) {
            ((TextView) findViewById(R.id.textViewAccountName)).setText(getText(R.string.label_account_name).toString() + " " + account.getName());
            ((TextView) findViewById(R.id.textViewAccountBalance)).setText(getText(R.string.label_balance).toString() + " " + new DecimalFormat("#0.00").format(account.getBalance()));
        }
    }


    private void showHideTransactionButton(boolean visible) {
        Button chargeButton = (Button)findViewById(R.id.button);
        if (visible) {
            //random charge amount between $1 & $10
            ((MyApplication) this.getApplication()).setRandomDbl(new Random().nextDouble() * 10);

            chargeButton.setText(getText(R.string.label_charge_transaction_button).toString() + new DecimalFormat("#0.00").format(((MyApplication) this.getApplication()).getRandomDbl()));
            chargeButton.setVisibility(View.VISIBLE);
        }
        else
        {
            chargeButton.setVisibility(View.INVISIBLE);
        }
    }
}
