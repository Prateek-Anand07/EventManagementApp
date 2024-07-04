package com.example.eventmanagementapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.eventmanagementapp.FilterMenu
import com.example.eventmanagementapp.R
import com.example.eventmanagementapp.databinding.FragmentOrganizerSearchBinding

class OrganizerSearchFragment : Fragment() {
    private lateinit var binding: FragmentOrganizerSearchBinding
    val filterMenu = com.example.eventmanagementapp.FilterMenu()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrganizerSearchBinding.inflate(inflater, container, false)
        binding.btnFilter.setOnClickListener {
            filterMenu.FiltersMenu(requireContext(), it)
        }
        return binding.root
    }

    companion object {

    }
}