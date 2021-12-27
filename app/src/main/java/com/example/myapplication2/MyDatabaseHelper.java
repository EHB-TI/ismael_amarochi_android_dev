package com.example.myapplication2;


import static java.sql.DriverManager.println;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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

    private ArrayList<String> content;
    private static String apiVersion = "";

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


    public void addParcel(String parcelnumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

//        System.out.println(getContent(parcelnumber));

//        String apiResult = getContent(parcelnumber);
//        System.out.println(apiResult);
//
        cv.put(COLUMN_PARCELNUMBER, parcelnumber);
        cv.put(COLUMN_PARCELNAME, "Test");
        cv.put(COLUMN_STATUS, "Test");
//
//        content = getContent(parcelnumber);
//        if (content.size() != 0){
//
//            Log.d("dd", Arrays.toString(new ArrayList[]{content}));
//
//            cv.put(COLUMN_PARCELNUMBER, content.get(0));
//            cv.put(COLUMN_PARCELNAME, content.get(1));
//            cv.put(COLUMN_STATUS, content.get(2));
//        }
//        else {
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        }

        long result = db.insert(TABLE_NAME,null, cv);

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<String> getContent(String number){
        RequestQueue queue = Volley.newRequestQueue(context);
//        String url ="https://tracking.bring.com/api/v2/tracking.json";
//        String url = "https://tracking.bring.com/api/v2/tracking.json?version=2&X-Bring-Client-URL=https://www.erasmushogeschool.be/nl&X-MyBring-API-Key=4ac3d105-8aed-4494-a4e4-83bdbbcb574f&X-MyBring-API-Uid=amarochiismael@gmail.com&lang=en&q=" + number;
          String url = "https://tracking.bring.com/api/v2/tracking.json?version=2&X-Bring-Client-URL=https://www.erasmushogeschool.be/nl&X-MyBring-API-Key=4ac3d105-8aed-4494-a4e4-83bdbbcb574f&X-MyBring-API-Uid=amarochiismael@gmail.com&lang=en&q=TESTPACKAGEATPICKUPPOINT";

//          Map<String, String> params = new HashMap<String, String>();
//        params.put("version", "2");
//        params.put("X-Bring-Client-URL", "https://www.erasmushogeschool.be/nl");
//        params.put("X-MyBring-API-Key", "4ac3d105-8aed-4494-a4e4-83bdbbcb574f");
//        params.put("X-MyBring-API-Uid", "amarochiismael@gmail.com");
//        params.put("lang", "en");
//        params.put("q", number);
//
//        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
//                            content.add(response.getJSONArray("consignmentSet").getJSONObject(0)
//                                    .getJSONArray("packageSet").getJSONObject(0)
//                                    .getString("packageNumber"));
//                            Log.d("-", "Number retrieved");
//
//                            content.add(response.getJSONArray("packageSet").getJSONObject(0)
//                                    .getJSONArray("packageSet").getJSONObject(0)
//                                    .getString("productName"));
//                            Log.d("--", "Name retrieved");
//
//                            content.add(response.getJSONArray("packageSet").getJSONObject(0)
//                                    .getJSONArray("packageSet").getJSONObject(0)
//                                    .getJSONArray("eventSet").getJSONObject(0)
//                                    .getString("status"));
//                            Log.d("---", "Status retrieved");


                            String number = response.getString("apiVersion");
                            System.out.println(number);
                            content.add(number);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("version", "2");
                params.put("X-Bring-Client-URL", "https://www.erasmushogeschool.be/nl");
                params.put("X-MyBring-API-Key", "4ac3d105-8aed-4494-a4e4-83bdbbcb574f");
                params.put("X-MyBring-API-Uid", "amarochiismael@gmail.com");
                params.put("q", number);
                params.put("lang", "en");

                return params;
            }
        };

        queue.add(jsonObjRequest);
        if (content == null){
            System.out.println("content == empty");
        }else
        {
            System.out.println("content != empty");
        }
        return content;
    }


//    public String getContent(String barcode) {
//        RequestQueue queue = Volley.newRequestQueue(context);
////        String url = "https://tracking.bring.com/api/v2/tracking.json";
//        String url = "https://tracking.bring.com/api/v2/tracking.json?version=2&X-Bring-Client-URL=https://www.erasmushogeschool.be/nl&X-MyBring-API-Key=4ac3d105-8aed-4494-a4e4-83bdbbcb574f&X-MyBring-API-Uid=amarochiismael@gmail.com&q=" + barcode + "&lang=en";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//
//                            String apiVersion = response.getJSONArray("consignmentSet").getJSONObject(0).getJSONArray("packageSet")
//                                    .getJSONObject(0).getJSONArray("eventSet").getJSONObject(0).getString("status").toString();
//
//                        } catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        Log.d("ERROR","error => "+error.toString());
//                    }
//                }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("version", "2");
//                params.put("X-Bring-Client-URL", "https://www.erasmushogeschool.be/nl");
//                params.put("X-MyBring-API-Key", "4ac3d105-8aed-4494-a4e4-83bdbbcb574f");
//                params.put("X-MyBring-API-Uid", "amarochiismael@gmail.com");
//                params.put("q", barcode);
//                params.put("lang", "en");
//
//                return params;
//            }
//        };
//        queue.add(jsonObjectRequest);
//        return apiVersion;
//    }


    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData(String row_id, String number, String name, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PARCELNUMBER, number);
        cv.put(COLUMN_PARCELNAME, name);
        cv.put(COLUMN_STATUS, status);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
