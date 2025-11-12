package com.example.lab_week_10.database

import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab_week_10.viewmodels.TotalViewModel

// @Database berfungsi untuk membuat database.
// Punya 2 parameter, entities yaitu tabel yang ada di database dan version yaitu versi database
@Database(entities = [Total::class], version = 1)
abstract class TotalDatabase : RoomDatabase() {
    // Declare Dao
    abstract fun totalDao() : TotalDao

}