package com.example.tubes

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.tubes.databinding.ActivityInputDataBinding
import java.util.Calendar

class InputDataActivity : AppCompatActivity() {
    // Deklarasi variabel lain yang sudah ada
    private lateinit var binding: ActivityInputDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Inisialisasi komponen lainnya seperti sebelumnya
        // ...

        // Inisialisasi Spinner untuk pilihan dokter
        val dokterList = listOf("Dokter Umum", "Dokter Gigi", "Dokter Anak")
        val dokterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dokterList)
        dokterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spDoter.adapter = dokterAdapter

        // Menangani aksi klik tombol Pesan Sekarang
        binding.btnCheckout.setOnClickListener {
            saveDataToDatabase()
        }

        // Menampilkan DatePickerDialog ketika tombol untuk memilih tanggal diklik
        binding.inputTanggal.setOnClickListener {
            showDatePicker()
        }
    }

    // Fungsi untuk menyimpan data ke Firebase Realtime Database
    private fun saveDataToDatabase() {
        // Ambil nilai dari inputan
        val nik = binding.inputNIK.text.toString().trim()
        val nama = binding.inputNama.text.toString().trim()
        val dokter = binding.spDoter.selectedItem.toString()
        val jenisKelamin = if (binding.radioButtonLakiLaki.isChecked) "Laki-laki" else "Perempuan"
        val tanggalPemeriksaan = binding.inputTanggal.text.toString().trim()
        val alamat = binding.inputAlamat.text.toString().trim()
        val nomorTelepon = binding.inputTelepon.text.toString().trim()

        // Validasi data sebelum menyimpan
        if (nik.isEmpty() || nama.isEmpty() || tanggalPemeriksaan.isEmpty() || alamat.isEmpty() || nomorTelepon.isEmpty()) {
            Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        // Buat instance Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        val reference: DatabaseReference = database.getReference("patient_data")

        // Buat objek PatientData dengan data yang diisi
        val patientData = PatientData(nik, nama, dokter, jenisKelamin, tanggalPemeriksaan, alamat, nomorTelepon)

        // Simpan data ke Firebase Realtime Database
        reference.push().setValue(patientData)
            .addOnSuccessListener {
                Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                // Tambahkan aksi lain setelah data berhasil disimpan, jika diperlukan
                // ...

                // Start MainActivity setelah data berhasil disimpan
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional, jika Anda ingin menutup InputDataActivity setelah kembali ke MainActivity
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menyimpan data!", Toast.LENGTH_SHORT).show()
            }
    }

    // Fungsi untuk menampilkan DatePickerDialog
    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth-${month + 1}-$year"
                binding.inputTanggal.setText(selectedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
}
