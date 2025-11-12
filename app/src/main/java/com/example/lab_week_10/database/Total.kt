package com.example.lab_week_10.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

// Buat table database "total"
// entity bisa set ForeignKey dan PrimaryKey di second parameter

@Entity(tableName = "total")
data class Total (
    // PrimaryKey berfungsi set column otomatis primary key
    @PrimaryKey(autoGenerate = true)

    // ColumnInfo memberi nama kolomnya apa
    //Kolom ID
    @ColumnInfo(name = "id")
    val id: Long = 0,

    // Kolom total
    @Embedded val total: TotalObject
)

data class TotalObject(
    @ColumnInfo(name = "value") val value: Int,
    @ColumnInfo(name = "date") val date:String
)