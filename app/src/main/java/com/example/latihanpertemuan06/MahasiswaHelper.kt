package com.example.latihanpertemuan06

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MahasiswaHelper (context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VESION) {
    companion object{
        private val DATABASE_NAME = "mahasiswa.db"
        private val DATABASE_VESION = 1

        private val SQL_CREATE_ENTRIES = " CREATE TABLE "+
                "${MahasiswaContract.MahasiswaEntry.TABLE_NAME}("+
                "${MahasiswaContract.MahasiswaEntry.COLUMN_EMAIL} TEXT PRIMARY KEY,"+
                "${MahasiswaContract.MahasiswaEntry.COLUMN_NAMA} TEXT,"+
                "${MahasiswaContract.MahasiswaEntry.COLUMN_NIM} TEXT,"+
                "${MahasiswaContract.MahasiswaEntry.COLUMN_PASSWORD} TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${MahasiswaContract.MahasiswaEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun getData(): ArrayList<Mahasiswa> {
        val db = writableDatabase
        val mahasiswaList = ArrayList<Mahasiswa>()

        val sql = " SELECT " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_EMAIL}, " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_NAMA}, " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_NIM}, " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_PASSWORD} " +
                "FROM ${MahasiswaContract.MahasiswaEntry.TABLE_NAME} " +
                "ORDER BY ${MahasiswaContract.MahasiswaEntry.COLUMN_NAMA} ASC"
        val cursor = db.rawQuery(sql, null,)

        with(cursor) {
            while (moveToNext()) {
                val email =
                    getString(getColumnIndexOrThrow(MahasiswaContract.MahasiswaEntry.COLUMN_EMAIL))
                val nama = getString(getColumnIndexOrThrow(MahasiswaContract.MahasiswaEntry.COLUMN_NAMA))
                val nim = getString(getColumnIndexOrThrow(MahasiswaContract.MahasiswaEntry.COLUMN_NIM))
                val pass = getString(getColumnIndexOrThrow(MahasiswaContract.MahasiswaEntry.COLUMN_PASSWORD))

                val mahasiswa = Mahasiswa(email, nama, nim, pass)
                mahasiswaList.add(mahasiswa)
            }
        }
        cursor.close()
        return mahasiswaList
    }

    fun insertData(mahasiswa: Mahasiswa) {
        val db = writableDatabase

        val sql = "INSERT INTO ${MahasiswaContract.MahasiswaEntry.TABLE_NAME} " +
                "(${MahasiswaContract.MahasiswaEntry.COLUMN_EMAIL}, " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_NAMA}, " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_NIM}, " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_PASSWORD} )" +
                "VALUES ('${mahasiswa.email}', '${mahasiswa.nama}', '${mahasiswa.nim}', '${mahasiswa.password}')"

        db.execSQL(sql)
        db.close()
    }

    fun delData(email: String) {
        val db = writableDatabase
        val table = MahasiswaContract.MahasiswaEntry.TABLE_NAME
        val emailColumn = MahasiswaContract.MahasiswaEntry.COLUMN_EMAIL
        val sql = " DELETE FROM " + table + " WHERE " + emailColumn + " = '" + email + "' "
        db.execSQL(sql)
        db.close()
    }

    fun updateData(mahasiswa: Mahasiswa){
        val db = writableDatabase

        val sql = " UPDATE ${MahasiswaContract.MahasiswaEntry.TABLE_NAME} SET " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_NAMA} = '${mahasiswa.nama}', " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_NIM} = '${mahasiswa.nim}', " +
                "${MahasiswaContract.MahasiswaEntry.COLUMN_PASSWORD} = '${mahasiswa.password}' " +
                " WHERE ${MahasiswaContract.MahasiswaEntry.COLUMN_EMAIL} = '${mahasiswa.email}' "
        db.execSQL(sql)
        db.close()

    }


}