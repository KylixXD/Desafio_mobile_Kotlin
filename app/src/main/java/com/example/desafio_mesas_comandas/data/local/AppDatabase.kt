package com.example.desafio_mesas_comandas.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// 1. A versão do banco está correta: 3
@Database(entities = [TableEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tableDao(): TableDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // 2. ESTA É A MIGRATION CORRETA E SIMPLIFICADA
        // Ela apenas adiciona a nova coluna na tabela existente.
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Usamos o nome EXATO da tabela entre aspas para lidar com o espaço.
                db.execSQL("ALTER TABLE `CheckPad Tables` ADD COLUMN numberCustomer INTEGER")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Tables_Database.db"
                )
                    // 3. Adicionamos APENAS a migration necessária
                    .addMigrations(MIGRATION_2_3)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}