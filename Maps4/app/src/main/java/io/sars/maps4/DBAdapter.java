package io.sars.maps4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/*************************************************************************************************
 * File:   DBAdapter.java
 * Author: Nicole Lane, Will Suchanek
 *
 * Purpose: This file is for the creation and manipulation of a SQLite Database. This database stores information about
 * beverages purchased by the user--product name and type, location purchased name and type, price, extra information, and latitude/longitide.
 *
 *
 *
 *************************************************************************************************/
public class DBAdapter {

    public static final String KEY_ROWID = "_id"; //ID number
    public static final String KEY_PRODUCTNAME = "productname"; //name of beverage
    public static final String KEY_TYPE = "type"; //Either beer or wine
    public static final String KEY_LOCATIONTYPE = "locationtype"; //Bar or shop
    public static final String KEY_LOCATIONNAME = "locationname"; //name of location
    public static final String KEY_PRICE = "price"; //Price of beverage


    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_EXTRAS = "extras"; //additional information about the drink

    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "beverages"; //name of the database itself
    private static final String DATABASE_TABLE = "products"; //the name of the table in beverages

    private static int DATABASE_VERSION = 2; //make sure that this gets changed any time information about the database gets changed

    private static final String DATABASE_CREATE = //Determine format of database
            "create table products (_id integer primary key autoincrement, "
                    + "productname text not null, locationtype text not null, locationname text not null, type text not null, " +
                    "price double not null, latitude double not null, longitude double not null, extras text not null);";

    private final Context context; //Context this database is being used in

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) //Create the database helper within the context it's being used in
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, //Upgrade database from previous version
                              int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS products");
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a product into the database.
    public long insertProduct(String productname, String type, String locationname, String locationtype, double price, double latitude, double longitude, String extras)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PRODUCTNAME, productname);
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_LOCATIONNAME, locationname);
        initialValues.put(KEY_LOCATIONTYPE, locationtype);
        initialValues.put(KEY_PRICE, price);
        initialValues.put(KEY_LATITUDE, latitude);
        initialValues.put(KEY_LONGITUDE, longitude);
        initialValues.put(KEY_EXTRAS, extras);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular product by its row ID
    public boolean deleteProduct(long rowID)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowID, null) > 0;
    }

    //---retrieves all the products---
    public Cursor getAllProducts()
    {
        return db.query(DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_PRODUCTNAME,
                        KEY_TYPE,
                        KEY_LOCATIONNAME,
                        KEY_LOCATIONTYPE,
                        KEY_PRICE,
                        KEY_LATITUDE,
                        KEY_LONGITUDE,
                        KEY_EXTRAS
        },


                null,
                null,
                null,
                null,
                null);
    }

    //---retrieves a particular product---
    public Cursor getProduct(long rowID) throws SQLException
    {
        Cursor mCursor =
                db.query(DATABASE_TABLE, new String[] {
                        KEY_PRODUCTNAME,
                        KEY_TYPE,
                        KEY_LOCATIONNAME,
                        KEY_LOCATIONTYPE,
                        KEY_PRICE,
                        KEY_LATITUDE,
                        KEY_LONGITUDE,
                        KEY_EXTRAS
                },

                        KEY_ROWID + "=" + rowID,
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a product---
    public boolean updateProduct(long rowID, String productname, String type, String locationname, String locationtype,
                                 double price, double latitude, double longitude,  String extras)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_PRODUCTNAME, productname);
        args.put(KEY_TYPE, type);
        args.put(KEY_LOCATIONNAME, locationname);
        args.put(KEY_LOCATIONTYPE, locationtype);
        args.put(KEY_PRICE, price);
        args.put(KEY_LATITUDE, latitude);
        args.put(KEY_LONGITUDE, longitude);
        args.put(KEY_EXTRAS, extras);
        return db.update(DATABASE_TABLE, args,
                KEY_ROWID + "=" + rowID, null) > 0;
    }

    public void upgrade () {
        DATABASE_VERSION++;
    }

    //Returns how many entries are currently in the database
    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
        db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

}

