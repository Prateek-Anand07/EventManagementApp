package com.example.eventmanagementapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eventmanagementapp.databinding.ActivityCreateEventPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateEventPage : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventPageBinding
    private val filterMenu = FilterMenu()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventPageBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        binding.btnCategory.setOnClickListener {
            filterMenu.FiltersMenu(this, it)
        }
        binding.btnSubmit.setOnClickListener {
            submitEvent()
        }
    }

    private fun submitEvent() {
        val eventTitle = binding.eventTitleCreate.editText?.text.toString().trim()
        val eventVenue = binding.eventVenue.editText?.text.toString().trim()
        val eventTime = binding.eventTimeCreate.editText?.text.toString().trim()
        val eventDate = binding.eventDate.editText?.text.toString().trim()
        val eventDetails = binding.eventDetails.editText?.text.toString().trim()
        val organizerName = binding.organizerName.editText?.text.toString().trim()
        val organizerContact = binding.organizerContact.editText?.text.toString().trim()
        val eventCategory = filterMenu.selectedCategory
        if(eventTitle.isEmpty() || eventVenue.isEmpty() || eventTime.isEmpty() || eventDate.isEmpty() ||
            eventDetails.isEmpty() || organizerName.isEmpty() || organizerContact.isEmpty() || eventCategory.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill all fields and select a category", Toast.LENGTH_SHORT).show()
        } else {
            val currentUser = auth.currentUser
            currentUser?.let { user ->
                val userKey = databaseReference.child("users").child("userType").child("Organizer").push().key
                val item = OrganizerHomeItem(eventTitle, eventVenue, eventTime, eventDate, eventDetails, organizerName, organizerContact, eventCategory, userKey ?: "")
                if(userKey != null) {
                    databaseReference.child("users").child(user.uid).child("userType").child("Organizer")
                        .child(userKey).setValue(item)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                Toast.makeText(this, "Event saved successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this, "Failed to save the event", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }
}