package com.example.myapplication2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "parcels";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PARCELNUMBER = "parcel_number";
    private static final String COLUMN_PARCELNAME = "parcel_name";
    private static final String COLUMN_STATUS = "parcel_status";

    private ArrayList<String> content = new ArrayList<>();
    private JSONObject object = new JSONObject();
    private JSONArray array = new JSONArray();
    private static String result1, result2, result3 = "";
    private static String name, status, number;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PARCELNUMBER + " TEXT, " +
                COLUMN_PARCELNAME + " TEXT, " +
                COLUMN_STATUS + " TEXT);";
        db.execSQL(query);
    }


    public void addParcel(String parcelnumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        number = apiRequestForNumber(parcelnumber);
        status = apiRequestForStatus(parcelnumber);
        name = apiRequestForName(parcelnumber);


        cv.put(COLUMN_PARCELNUMBER, number);
        cv.put(COLUMN_STATUS, status);
        cv.put(COLUMN_PARCELNAME, name);

        if (name == null || status == null || number == null){
            Toast.makeText(context, context.getString(R.string.error_occured_insert_db), Toast.LENGTH_SHORT).show();
        }
        else {
            long result = db.insert(TABLE_NAME, null, cv);

            if (result == -1) {
                Toast.makeText(context, context.getString(R.string.failed_insert_db), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, context.getString(R.string.success_add_db), Toast.LENGTH_SHORT).show();
            }
            db.close();
        }


    }

    public String apiRequestForStatus(String barcode) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://tracking.bring.com/api/v2/tracking.json?version=2&X-Bring-Client-URL=https://www.erasmushogeschool.be/nl&X-MyBring-API-Key=4ac3d105-8aed-4494-a4e4-83bdbbcb574f&X-MyBring-API-Uid=amarochiismael@gmail.com&q=" + barcode + "&lang=en";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            result1 = response.getJSONArray("consignmentSet").getJSONObject(0).getJSONArray("packageSet")
                                    .getJSONObject(0).getJSONArray("eventSet").getJSONObject(0).getString("status").toString();

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        );
        queue.add(jsonObjectRequest);
        return result1;
    }

    public String apiRequestForName(String barcode) {
        RequestQueue queue = Volley.newRequestQueue(context);
//        String url = "https://tracking.bring.com/api/v2/tracking.json";
        String url = "https://tracking.bring.com/api/v2/tracking.json?version=2&X-Bring-Client-URL=https://www.erasmushogeschool.be/nl&X-MyBring-API-Key=4ac3d105-8aed-4494-a4e4-83bdbbcb574f&X-MyBring-API-Uid=amarochiismael@gmail.com&q=" + barcode + "&lang=en";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            result2 = response.getJSONArray("consignmentSet").getJSONObject(0)
                                        .getJSONArray("packageSet").getJSONObject(0)
                                        .getString("productName").toString();

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        );

        queue.add(jsonObjectRequest);
        return result2;
    }

    public String apiRequestForNumber(String barcode) {
        RequestQueue queue = Volley.newRequestQueue(context);
//        String url = "https://tracking.bring.com/api/v2/tracking.json";
        String url = "https://tracking.bring.com/api/v2/tracking.json?version=2&X-Bring-Client-URL=https://www.erasmushogeschool.be/nl&X-MyBring-API-Key=4ac3d105-8aed-4494-a4e4-83bdbbcb574f&X-MyBring-API-Uid=amarochiismael@gmail.com&q=" + barcode + "&lang=en";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            result3 = response.getJSONArray("consignmentSet").getJSONObject(0)
                                    .getJSONArray("packageSet").getJSONObject(0)
                                    .getString("packageNumber").toString();

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        );

        queue.add(jsonObjectRequest);
        return result3;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData(String row_id, String number, String name, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PARCELNUMBER, number);
        cv.put(COLUMN_PARCELNAME, name);
        cv.put(COLUMN_STATUS, status);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, context.getString(R.string.update_fail_db), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.success_update_db), Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, context.getString(R.string.failed_delete_db), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.success_deleted_db), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

}
