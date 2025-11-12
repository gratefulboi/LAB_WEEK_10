package com.example.lab_week_10.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// Dao berperan sebagai interface untuk mengakses database. Dao bisa melakukan operasi CRUD Database
@Dao
interface TotalDao {
    // @Insert berfungsi untuk memasukkan row data baru ke database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(total: Total)

    // @Update dipakai untuk memperbarui row yang sudah ada
    @Update
    fun update(total: Total)

    // @Delete digunakan untuk delete row yang sudah ada
    @Delete
    fun delete(total: Total)

    // @Query untuk membuat custom query
    @Query("SELECT * FROM total WHERE id = :id")
    fun getTotal(id: Long): List<Total>
}