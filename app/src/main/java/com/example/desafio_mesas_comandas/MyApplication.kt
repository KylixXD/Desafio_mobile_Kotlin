package com.example.desafio_mesas_comandas

import AppDatabase
import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    companion object {
        lateinit var db: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tables-db"
        ).build()
    }
}