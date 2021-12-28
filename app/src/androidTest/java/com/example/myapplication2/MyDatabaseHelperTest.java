package com.example.myapplication2;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyDatabaseHelperTest {



    @Test
    public void onCreate() {
    }

    @Test
    public void addParcel() {
    }

    @Test
    public void apiRequestForStatus() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(null);
        assertEquals("READY_FOR_PICKUP", myDatabaseHelper.apiRequestForStatus("READY_FOR_PICKUP") );
    }

    @Test
    public void apiRequestForName() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(null);
        assertEquals("Pick-up parcel", myDatabaseHelper.apiRequestForName("Pick-up parcel") );
    }

    @Test
    public void apiRequestForNumber() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(null);
        assertEquals("TESTPACKAGEATPICKUPPOINT", myDatabaseHelper.apiRequestForNumber("TESTPACKAGEATPICKUPPOINT") );
    }

    @Test
    public void onUpgrade() {
    }

    @Test
    public void readAllData() {
    }

    @Test
    public void updateData() {
    }

    @Test
    public void deleteOneRow() {
    }

    @Test
    public void deleteAllData() {
    }
}