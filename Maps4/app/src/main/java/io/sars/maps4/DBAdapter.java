package io.sars.maps4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
    public static final String KEY_PRODUCTNAME = "productname";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PRICE = "price";

    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "beverages";
    private static final String DATABASE_TABLE = "products";
    private static int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table products (productname integer primary key autoincrement, "
                    + "productname text not null, type text not null, latitude/longitude doubles not null"
                    + "price double not null);";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
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
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
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

    //---insert a product into the database---
    public long insertProduct(String productname, String type, double latitude, double longitude, double price)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PRODUCTNAME, productname);
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_LATITUDE, latitude);
        initialValues.put(KEY_LONGITUDE, longitude);
        initialValues.put(KEY_PRICE, price);


        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular product---
    public boolean deleteProduct(String productname)
    {
        return db.delete(DATABASE_TABLE, KEY_PRODUCTNAME + "=" + productname, null) > 0;
    }

    //---retrieves all the products---
    public Cursor getAllProducts()
    {
        return db.query(DATABASE_TABLE, new String[] {
                        KEY_PRODUCTNAME,
                        KEY_TYPE,
                        KEY_PRICE,
                        KEY_LATITUDE,
                        KEY_LONGITUDE
        },


                null,
                null,
                null,
                null,
                null);
    }

    //---retrieves a particular product---
    public Cursor getProduct(String productname) throws SQLException
    {
        Cursor mCursor =
                db.query(DATABASE_TABLE, new String[] {
                        KEY_PRODUCTNAME,
                        KEY_TYPE,
                        KEY_PRICE,
                        KEY_LATITUDE,
                        KEY_LONGITUDE
                },

                        KEY_PRODUCTNAME + "=" + productname,
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
    public boolean updateProduct(String productname, String type,
                               double latitude, double longitude, double price)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_TYPE, type);
        args.put(KEY_PRICE, price);
        args.put(KEY_LATITUDE, latitude);
        args.put(KEY_LONGITUDE, longitude);
        return db.update(DATABASE_TABLE, args,
                KEY_PRODUCTNAME + "=" + productname, null) > 0;
    }

    public void upgrade () {
        DATABASE_VERSION++;
    }
}

