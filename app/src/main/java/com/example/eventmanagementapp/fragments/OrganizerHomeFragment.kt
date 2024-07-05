package com.example.eventmanagementapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.example.eventmanagementapp.CreateEventPage
import com.example.eventmanagementapp.R
import com.example.eventmanagementapp.databinding.FragmentOrganizerHomeBinding
import com.example.eventmanagementapp.databinding.FragmentOrganizerSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OrganizerHomeFragment : Fragment() {
    private lateinit var binding: FragmentOrganizerHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrganizerHomeBinding.inflate(inflater, container, false)
        binding.createEvent.setOnClickListener {
            startActivity(Intent(requireContext(), CreateEventPage::class.java))
        }
        return binding.root
    }

    companion object {

    }
}