package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bhatia on 4/1/2017.
 */

public class BackendUtilitySingleton {
    public static final String API_KEY = "VxUDzy-whQi2yALDCd4PXD9WvNWgOLga";

    private BackendUtilitySingleton() {}
    private static class BackendUtilitySingletonHelper
    {
        private static final BackendUtilitySingleton INSTANCE = new BackendUtilitySingleton();
    }
    public static BackendUtilitySingleton getInstance()
    {
        return BackendUtilitySingletonHelper.INSTANCE;
    }

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.mongolab.com/api/1/databases/swdetsi2017/collections/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private MongolabApiService service = retrofit.create(MongolabApiService.class);
    public List<Account> getListOfAccounts() throws IOException
    {
        return service.getListOfAccounts(API_KEY).execute().body();
    }

    public Account getAccountDetails(String accountID) throws IOException
    {
        return service.getAccountDetails(accountID, API_KEY).execute().body();
    }

    public Transaction addTransactionToAccount(Transaction transaction) throws IOException
    {
        return service.addTransactionToAccount(transaction, API_KEY).execute().body();
    }

    public Account updateBalanceForAccount(Account originalAccount, double newBalance) throws IOException, CloneNotSupportedException {
        Account newAccount = (Account) originalAccount.clone();
        newAccount.setBalance(newBalance);

        return service.updateAccount(originalAccount.getAccountID().getOid(), newAccount, API_KEY).execute().body();
    }

    public List<Beacon> getListOfActiveBeacons() throws IOException {
        return service.getListOfActiveBeacons(API_KEY).execute().body();
    }

    public Beacon getBeaconByID(Beacon.EstimoteBeaconID estimoteBeaconID) throws IOException, ArrayIndexOutOfBoundsException {
        return service.getBeaconByID(estimoteBeaconID.toString(), API_KEY).execute().body().get(0);
    }

    public ConfigurationsAndSettings getConfigurations() throws IOException
    {
        return service.getConfigurations(API_KEY).execute().body().get(0);
    }
}
