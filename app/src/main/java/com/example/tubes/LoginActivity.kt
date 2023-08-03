package com.example.tubes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tubes.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var btnLogin: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        usernameInput = findViewById(R.id.inputUsername)
        passwordInput = findViewById(R.id.inputPassword)

        btnLogin = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val username = usernameInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (username.isEmpty()) {
            usernameInput.error = "Username is required"
            usernameInput.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordInput.error = "Password is required"
            passwordInput.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful, navigate to the next activity (e.g., HomeActivity).
                    showToast("Login successful!")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Login failed, handle the error.
                    // You can use task.exception?.message to get the error message.
                    showToast("Login failed. ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}