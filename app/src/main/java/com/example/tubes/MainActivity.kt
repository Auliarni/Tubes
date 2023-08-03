package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var usernameTextView: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var btnDokterUmum: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi komponen lainnya seperti sebelumnya
        // ...

        // Inisialisasi tombol btn_dokterUmum dan set fungsi onClick
        btnDokterUmum = findViewById(R.id.btn_dokterUmum)
        btnDokterUmum.setOnClickListener {
            openInputDataActivity()
        }
    }

    // Fungsi untuk membuka halaman input data
    private fun openInputDataActivity() {
        val intent = Intent(this, InputDataActivity::class.java)
        startActivity(intent)
    }

}