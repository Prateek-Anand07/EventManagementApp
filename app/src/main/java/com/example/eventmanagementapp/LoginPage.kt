package com.example.eventmanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eventmanagementapp.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val sharedPref = getSharedPreferences("userPref", MODE_PRIVATE)
            val userRole = sharedPref.getString("userRole", "")
            if (userRole == "Organizer") {
                startActivity(Intent(this, OrganizerHomePage::class.java))
            } else if (userRole == "Visitor") {
                startActivity(Intent(this, VisitorHomePage::class.java))
            } else {
                Toast.makeText(this, "Role not set. Please Sign in again.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginPage::class.java))
            }
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        auth = Firebase.auth
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnNewAc.setOnClickListener {
            startActivity(Intent(this, SignUpPage::class.java))
            finish()
        }
        binding.btnSignIn.setOnClickListener {
            val email = binding.edTemail.text.toString()
            val password = binding.edTpassword.text.toString()
            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                val uid = user.uid
                                val role: String
                                val userData = mutableMapOf<String, Any>()
                                if (binding.radioOrganizer.isChecked) {
                                    role = "Organizer"
                                    userData["role"] = role
                                    saveUserData(uid, role, userData)
                                    Toast.makeText(this, "Sign in successful as Organizer", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, OrganizerHomePage::class.java))
                                } else if (binding.radioVisitor.isChecked) {
                                    role = "Visitor"
                                    userData["role"] = role
                                    saveUserData(uid, role, userData)
                                    Toast.makeText(this, "Sign in successful as Visitor", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, VisitorHomePage::class.java))
                                } else {
                                    Toast.makeText(this, "Please select your role as Organizer or Visitor", Toast.LENGTH_LONG).show()
                                }
                                finish()
                            }
                        } else {
                            Toast.makeText(this, "Sign in failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

    private fun saveUserData(uid: String, userType: String, userData: Map<String, Any>) {
        val userRef = database.getReference("users/$uid/userType/$userType")
        userRef.setValue(userData)
            .addOnSuccessListener {
                // Data saved successfully
                val sharedPref = getSharedPreferences("userPref", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("userRole", userType)
                editor.apply()
            }
            .addOnFailureListener { exception ->
                // Handle error
                Toast.makeText(this, "Error saving data: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }
}
