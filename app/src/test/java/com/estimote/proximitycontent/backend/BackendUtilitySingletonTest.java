package com.estimote.proximitycontent.backend;

import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.BackendUtilitySingleton;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Account;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.MonglabApiServiceCallException;
import com.ChadgainorGmailCom.ChadgainorGmailComSProA9B.utilities.backend.Transaction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by bhatia on 4/1/2017.
 */

public class BackendUtilitySingletonTest {
    BackendUtilitySingleton instance;

    @Before
    public void setUp() {
        instance = BackendUtilitySingleton.getInstance();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetListOfAccounts() {
        try {
            List<Account> accountList = instance.getListOfAccounts("58cdcfef734d1d2ca858081e");
            Assert.assertNotEquals(0, accountList.size());
        } catch (IOException ioe) {
            throw new MonglabApiServiceCallException("JUnit test testGetListOfAccounts() failed", ioe);
        }
    }

    @Test
    public void testGetAccountDetails() {
        try {
            Account accountData = instance.getAccountDetails("58cdcfef734d1d2ca858081e");
            Assert.assertEquals("Kat's phone", accountData.getName());
        } catch (IOException ioe) {
            throw new MonglabApiServiceCallException("JUnit test testGetAccountDetails() failed", ioe);
        }
    }


    @Test
    public void testAddTransactionToAccount() {
        Transaction inputTransaction = new Transaction("XYZ", new Date(), 50.01, 49.00143, 56.0004);
        Assert.assertNull(inputTransaction.getTransactionID());
        try {
            Transaction outpotTransaction = instance.addTransactionToAccount(inputTransaction);
            Assert.assertNotNull(outpotTransaction.getTransactionID());
            Assert.assertNotNull(outpotTransaction.getTransactionID().getOid());
            Assert.assertNotEquals(0, outpotTransaction.getTransactionID().getOid().length());
        } catch (IOException ioe) {
            throw new MonglabApiServiceCallException("JUnit test testAddTransactionToAccount() failed", ioe);
        }
    }

    @Test
    public void testUpdateBalanceForAccount()
    {
        double newBalance = 555.55;
        String accountOID = "58cdcfef734d1d2ca858081e";
        Account testAccount = new Account(accountOID, "Kat's phone" , "333 kats way, Detroit, MI 48000" ,
                                                                "(313) 817-6714" , "16 ", 'F' , "african american" , 100 , "2 hrs" , "2 times / week");
        Assert.assertNotEquals(newBalance, testAccount.getBalance());
        try {
            Account updatedAccount = instance.updateBalanceForAccount(testAccount, newBalance);
            Assert.assertEquals(accountOID, updatedAccount.getAccountID().getOid());
            Assert.assertEquals(newBalance, updatedAccount.getBalance(), 0);
        } catch (IOException ioe) {
        throw new MonglabApiServiceCallException("JUnit test testUpdateBalanceForAccount() failed", ioe);
        } catch (CloneNotSupportedException cnse) {
            throw new MonglabApiServiceCallException("JUnit test testUpdateBalanceForAccount() failed", cnse);
        }
    }
}