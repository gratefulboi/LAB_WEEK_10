package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.database.TotalObject
import com.example.lab_week_10.viewmodels.TotalViewModel
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // inisialisasi value total dari database
        initializeValueFromDatabase()

        prepareViewModel()
    }

    override fun onStart() {
        super.onStart()

        // Fetching data dan show toast
        val totalList = db.totalDao().getTotal(ID)
        if(totalList.isNotEmpty()) {
            val lastUpdateDate = totalList.first().total.date

            // Show toast jika column date tidak kosong
            if(lastUpdateDate.isNotEmpty()) {
                Toast.makeText(this, "Last Update: $lastUpdateDate", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()

        // Ambil value sekarang dari ViewModel
        val currentValue = viewModel.total.value ?: 0
        // Ambil date sekarang
        val currentDate = Date().toString()
        // Buat objek TotalObject baru
        val newTotalObject = TotalObject(value = currentValue, date = currentDate)

        // update db dengan new total entity
        db.totalDao().update(Total(id = ID, total = newTotalObject))
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text = getString(R.string.text_total, total)
    }

    private fun prepareViewModel(){
        viewModel.total.observe(this, {
            updateText(it)
        })

        findViewById<Button>(R.id.button_increment).setOnClickListener { viewModel.incrementTotal() }
    }

//    // Buat TotalDatabase dengan nama total-database
//    private fun prepareDatabase(): TotalDatabase {
//        return Room.databaseBuilder(
//            applicationContext,
//            TotalDatabase::class.java, "total-database"
//        ).allowMainThreadQueries().build()
//    }

    // Inisialisasi value total dari database
    // Jika database kosong, insert total object baru dengan value 0
    // Jika ga kosong, ambil value dari database
    private fun initializeValueFromDatabase() {
        val total = db.totalDao().getTotal(ID)
        if(total.isEmpty()) {
            // Jika database kosong, buat new entry dengan value 0 dan tanggal kosong
            val defaultTotalObject = TotalObject(value = 0, date = "")
            db.totalDao().insert(Total(id = ID, total = defaultTotalObject))
            viewModel.setTotal(0)
        } else {
            val databaseValue = total.first().total.value
            viewModel.setTotal(databaseValue)
        }
    }

    companion object {
        const val ID: Long = 1
    }
}