package com.example.tubes
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        usernameInput = findViewById(R.id.inputUsername)
        emailInput = findViewById(R.id.inputEmail)
        passwordInput = findViewById(R.id.inputPassword)

        btnRegister = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            registerUser()
        }

        // Add click listener for the back button (btnBack)
        val btnBack: ImageView = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            navigateBackToWelcome()
        }
    }

    private fun navigateBackToWelcome() {
        // Create an Intent to go back to the WelcomeActivity
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)

        // Optional: Add transition animation between activities (slide from right to left)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        // Finish the current activity so that pressing the back button from the WelcomeActivity
        // won't bring back the RegisterActivity.
        finish()
    }

    private fun registerUser() {
        val username = usernameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (username.isEmpty()) {
            usernameInput.error = "Username is required"
            usernameInput.requestFocus()
            return
        }

        if (email.isEmpty()) {
            emailInput.error = "Email is required"
            emailInput.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Invalid email format"
            emailInput.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordInput.error = "Password is required"
            passwordInput.requestFocus()
            return
        }

        if (password.length < 6) {
            passwordInput.error = "Password must be at least 6 characters"
            passwordInput.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful, you can do further actions here if needed
                    // For example, you might want to redirect the user to another activity
                    // or display a success message.
                    showToast("Registration successful!")
                    navigateBackToWelcome()
                } else {
                    // Registration failed, handle the error.
                    // You can use task.exception?.message to get the error message.
                    showToast("Registration failed. ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}