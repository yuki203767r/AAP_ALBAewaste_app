package com.google.codelabs.buildyourfirstmap

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log


class MyUsers() : Application(){

    private var userList: ArrayList<String> = ArrayList<String>()
    private var userIdList: ArrayList<Int> = ArrayList<Int>()

    companion object {

        val ourInstance = MyUsers()
    }

//TODO 8: Make use of MyDBAdapter's method to perform
//  - insert,
//  - delete,
//  - retrieval of data.

    fun addToDatabase(
        entryName: String, entryPassw: String, entrypfp: ByteArray, c: Context): Long? {
        val db = MyDBAdapter(c)
        db.open()

        val rowIDofInsertedEntry = db.insertEntry(entryName, entryPassw, entrypfp)
        db.close()

        return rowIDofInsertedEntry

    }
    fun updateDatabase(
        rowID: Int,
        entryName: String, entryPassw: String, entrypfp: ByteArray, c: Context): Int? {
        val db = MyDBAdapter(c)
        db.open()

        val rowIDofUpdater = db.updateEntry(rowID, entryName, entryPassw, entrypfp)
        db.close()

        return rowIDofUpdater

    }
    fun deleteFrmDatabase(rowID: Int, c: Context): Boolean {
        val db = MyDBAdapter(c)
        db.open()

        val id = userIdList[rowID]
        val updateStatus = db.removeEntry(id)
        db.close()

        return updateStatus

    }


    /*fun retrieveAll(c: Context): ArrayList<String> {

        val myCursor: Cursor?
        var myString = ""

        val db = MyDBAdapter(c)
        db.open()
        userIdList.clear()
        userList.clear()
        myCursor = db.retrieveAllEntriesCursor()

        if (myCursor != null && myCursor.count > 0){
            myCursor!!.moveToFirst()
            do {
                userIdList.add(myCursor.getInt(db.COLUMN_KEY_ID))
                myString = myCursor.getString(db.COLUMN_NAME_ID)
                userList.add(myString)
            }while (myCursor.moveToNext())
        }
        db.close()
        return userList

    }*/


    fun retrieveOneUser(c: Context, mId : Int): UserDisplays? {
        Log.w("run", "hello")

        val myCursor: Cursor?
        var myString = ""

        var returnUser: UserDisplays? = null

        val db = MyDBAdapter(c)
        db.open()
        userIdList.clear()
        userList.clear()
        myCursor = db.getData(mId)
        Log.w("Cursor Count", myCursor!!.count.toString())


        if (myCursor!!.count > 0){
            myCursor!!.moveToFirst()
            do {
                Log.w(myCursor!!.getString(db.COLUMN_NAME_ID).toString(), "name")
                userIdList.add(myCursor.getInt(db.COLUMN_KEY_ID))
                myString = myCursor.getString(db.COLUMN_NAME_ID) + myCursor.getString(db.COLUMN_PASSWORD_ID)+
                        myCursor.getString(db.COLUMN_PFP_ID)
                userList.add(myString)

                returnUser = UserDisplays(myCursor.getString(db.COLUMN_NAME_ID), myCursor.getString(db.COLUMN_PASSWORD_ID), myCursor.getString(db.COLUMN_PFP_ID).toByteArray())
                break
            }while (myCursor.moveToNext())
        }
        db.close()
        return returnUser
    }

    fun getIDofMovie(position: Int): Int{
        Log.w(userIdList[position].toString(), "list Id")
        return userIdList[position]
    }

}