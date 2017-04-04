package com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bhatia on 4/1/2017.
 */

public interface MongolabApiService {
    /*
        Get list of accounts
        https://api.mongolab.com/api/1/databases/swdetsi2017/collections
        /accounts?apiKey=VxUDzy-whQi2yALDCd4PXD9WvNWgOLga
     */
    @GET("accounts")
    Call<List<Account>> getListOfAccounts(@Query("apiKey") String apiKey);

    /*
        lookup by account ID:
        https://api.mongolab.com/api/1/databases/swdetsi2017/collections/accounts/58cdd126734d1d2ca858083c?apiKey=VxUDzy-whQi2yALDCd4PXD9WvNWgOLga
     */
    @GET("accounts/{oid}")
    Call<Account> getAccountDetails(@Path("oid") String oid, @Query("apiKey") String apiKey);

    /*
        Add transaction to account:
        curl -H "Content-Type: application/json" -X POST -d '{"account_id":"58cde0b6734d1d2ca8580a55", "date":"2017-18-03", "time":"23:11:00:000", "amt_charged":10.00, "gps_long":42.33140, "gps_lat":83.04581}' https://api.mongolab.com/api/1/databases/swdetsi2017/collections/transactions/?apiKey=VxUDzy-whQi2yALDCd4PXD9WvNWgOLga
     */
    @POST("transactions")
    Call<Transaction> addTransactionToAccount(@Body Transaction transaction, @Query("apiKey") String apiKey);

    /*
        Update account (using AccountID):
        One example, Update balance for account:
        curl -H "Content-Type: application/json" -X PUT -d '{"name": "Yash", "address": "333 technology hwy, Detroit, MI 48000", "phone": "(313) 817-6714", "age": "19", "gender": "M", "race": "asian", "balance": 200.00, "avg_travel_time": "2 hrs", "avg_travel_freq": "2 times / week"}' https://api.mongolab.com/api/1/databases/swdetsi2017/collections/accounts/58cdd126734d1d2ca858083c/?apiKey=VxUDzy-whQi2yALDCd4PXD9WvNWgOLga
     */
    @PUT("accounts/{oid}")
    Call<Account> updateAccount(@Path("oid") String oid, @Body Account updatedAccount, @Query("apiKey") String apiKey);

    /*
        Get list of active beacons:
        https://api.mongolab.com/api/1/databases/swdetsi2017/collections/beacons?q={%22active%22:true}&apiKey=VxUDzy-whQi2yALDCd4PXD9WvNWgOLga
     */
    @GET("beacons?q={\"active\":true}")
    Call<List<Beacon>> getListOfActiveBeacons(@Query("apiKey") String apiKey);


    /*
        Get Beacon by its estimote beacon IDs:
        https://api.mongolab.com/api/1/databases/swdetsi2017/collections/beacons?q={%22estimote_beacon_id%22:{%22proximity_uuid%22%20:%20%22B9407F30-F5F8-466E-AFF9-25556B57FE6D%22%20,%20%22major%22%20:%20%2221183%22%20,%20%22minor%22%20:%20%2238897%22}}&apiKey=VxUDzy-whQi2yALDCd4PXD9WvNWgOLga
     */
    @GET("beacons")
    Call<List<Beacon>> getBeaconByID(@Query("q") String q, @Query("apiKey") String apiKey);
}
