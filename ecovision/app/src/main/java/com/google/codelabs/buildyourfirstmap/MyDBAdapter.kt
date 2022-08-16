package com.google.codelabs.buildyourfirstmap

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDBAdapter(c : Context){


    private val DATABASE_NAME = "User.db"
    private val DATABASE_TABLE = "myRegisteredDb"
    private val DATABASE_VERSION = 1
    private var _db: SQLiteDatabase? = null
    private val context: Context?= null

    val KEY_ID = "_id"
    val COLUMN_KEY_ID = 0
    val ENTRY_NAME = "entry_name"
    val COLUMN_NAME_ID = 1
    val ENTRY_PASSWORD = "entry_password"
    val COLUMN_PASSWORD_ID = 2
    val ENTRY_PFP = "entry_pfp"
    val COLUMN_PFP_ID = 3


    protected val DATABASE_CREATE = ("create table " + DATABASE_TABLE + " " + "(" + KEY_ID + " integer primary key autoincrement, "
            + ENTRY_NAME + " Text, "
            + ENTRY_PASSWORD + " Text, "
            + ENTRY_PFP + " Bytearray);")

    private val MYDBADAPTER_LOG_CAT = "MY_LOG"

    private var dbHelper: MyDBOpenHelper? = null

    init {
        //TODO 1 : Create a MyDBOpenHelper object
        dbHelper = MyDBOpenHelper(c, DATABASE_NAME, DATABASE_VERSION)
    }

    fun close() {
        //TODO 2 : close the table using the SQLite database handler
        _db?.close()
    }


    fun open() {
        //TODO 3 : Open DB using the appropriate methods
        try {
            _db = dbHelper?.getWritableDatabase()
        }catch (e: SQLException){
            _db = dbHelper?.getReadableDatabase()
        }
    }

    fun insertEntry(entryName: String, entryPassw: String, entrypfp: ByteArray): Long? {
        //TODO 4 - insert record into table
        val newEntryValues = ContentValues()

        newEntryValues.put(ENTRY_NAME, entryName)
        newEntryValues.put(ENTRY_PASSWORD, entryPassw)
        newEntryValues.put(ENTRY_PFP, entrypfp)

        return _db?.insert(DATABASE_TABLE, null, newEntryValues)
    }

    fun removeEntry(_rowIndex: Int): Boolean {
        //TODO 5 - remove record from table
        if (_db!!.delete(DATABASE_TABLE, KEY_ID + "="+ _rowIndex, null) <= 0){
            Log.w(MYDBADAPTER_LOG_CAT, "Removing entry where id = $_rowIndex failed")
            return false
        }
        return true
    }

    fun updateEntry(_rowIndex: Int,entryName: String, entryPassw: String, entrypfp: ByteArray): Int? {
        val updateEntryValues = ContentValues()
//
        updateEntryValues.put(ENTRY_NAME, entryName)
        updateEntryValues.put(ENTRY_PASSWORD, entryPassw)
        updateEntryValues.put(ENTRY_PFP, entrypfp)

        return _db?.update(DATABASE_TABLE, updateEntryValues, _rowIndex.toString(), null )
    }

    fun retrieveAllEntriesCursor(): Cursor? {
        //TODO 6 - retrieve all records from table

        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE, arrayOf(KEY_ID, ENTRY_NAME, ENTRY_PASSWORD, ENTRY_PFP),
                null, null, null, null, null)
        }catch (e: SQLException){
            Log.w(DATABASE_TABLE, "Retrieve fail")
        }

        return c
    }

    fun getData(userID: Int): Cursor? {
        var c: Cursor? = null
        try {
            c = _db?.rawQuery("SELECT * FROM $DATABASE_TABLE where $KEY_ID= $userID LIMIT 1", null)
        }catch (e: SQLException){
            Log.w(DATABASE_TABLE, "Retrieve fail")
        }

        return c
    }

    fun checkUserLogin(username: String, password: String): Boolean {
        val Query =
            "select entry_name, entry_password from $DATABASE_TABLE where entry_name='$username' and entry_password='$password'"
        var cursor: Cursor? = null
        try {
            cursor = _db?.rawQuery(Query, null) //raw query always holds rawQuery(String Query,select args)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return if (cursor != null && cursor.count > 0) {
            cursor.close()
            true
        } else {
            cursor!!.close()
            false
        }
    }

    fun checkUserFace(image: ByteArray): Boolean {
        val Query =
            "select entry_pfp from $DATABASE_TABLE where entry_pfp='$image' "
        var cursor: Cursor? = null
        try {
            cursor = _db?.rawQuery(Query, null) //raw query always holds rawQuery(String Query,select args)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return if (cursor != null && cursor.count > 0) {
            cursor.close()
            true
        } else {
            cursor!!.close()
            false
        }
    }

    inner class MyDBOpenHelper(c: Context, db_name : String, ver_no : Int ):
        SQLiteOpenHelper(c, db_name, null, ver_no){

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(DATABASE_CREATE)
            Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE created!")

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }
    }


}