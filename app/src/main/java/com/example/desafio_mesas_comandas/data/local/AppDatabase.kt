package com.example.desafio_mesas_comandas.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TableEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tableDao(): TableDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
            CREATE TABLE tables_new (
                id INTEGER PRIMARY KEY NOT NULL,
                title INTEGER NOT NULL,
                customerName TEXT,
                sellerName TEXT,
                orderCount INTEGER NOT NULL,
                activity TEXT,
                idleTime INTEGER NOT NULL,
                subTotal REAL
            )
        """
                )

                db.execSQL(
                    """
            INSERT INTO tables_new (id, title, customerName, sellerName, orderCount, activity, idleTime, subTotal)
            SELECT id, CAST(title AS INTEGER), customerName, sellerName, orderCount, activity, idleTime, subTotal
            FROM tables
        """
                )

                db.execSQL("DROP TABLE tables")
                db.execSQL("ALTER TABLE tables_new RENAME TO tables")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
            CREATE TABLE tables_new (
                id INTEGER PRIMARY KEY NOT NULL,
                title INTEGER NOT NULL,
                customerName TEXT,
                sellerName TEXT,
                orderCount INTEGER NOT NULL,
                activity TEXT,
                idleTime INTEGER NOT NULL,
                subTotal REAL
            )
        """
                )

                db.execSQL(
                    """
            INSERT INTO tables_new (id, title, customerName, sellerName, orderCount, activity, idleTime, subTotal)
            SELECT id, CAST(title AS INTEGER), customerName, sellerName, orderCount, activity, idleTime, subTotal
            FROM tables
        """
                )

                db.execSQL("DROP TABLE tables")
                db.execSQL("ALTER TABLE tables_new RENAME TO tables")
            }
        }


        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Tables_Database.db"
                ).addMigrations(MIGRATION_1_3, MIGRATION_2_3).build()
                INSTANCE = instance
                instance
            }
        }
    }
}