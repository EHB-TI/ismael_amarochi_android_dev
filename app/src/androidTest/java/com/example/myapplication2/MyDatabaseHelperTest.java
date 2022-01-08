package com.example.myapplication2;

import static org.junit.Assert.*;

import android.content.Context;

import org.junit.Test;

public class MyDatabaseHelperTest {

    Context context;


    @Test
    public void onCreate() {
    }

    @Test
    public void addParcel() {
    }

    @Test
    public void apiRequestForStatus() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
        assertEquals("READY_FOR_PICKUP", myDatabaseHelper.apiRequestForStatus("TESTPACKAGEATPICKUPPOINT") );
    }

    @Test
    public void apiRequestForName() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
        assertEquals("Pick-up parcel", myDatabaseHelper.apiRequestForName("TESTPACKAGEATPICKUPPOINT") );
    }

    @Test
    public void apiRequestForNumber() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
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