package com.example.eventmanagementapp

import android.content.Intent
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.eventmanagementapp.databinding.ActivityVisitorHomePageBinding
import com.example.eventmanagementapp.fragments.OrganizerHomeFragment
import com.example.eventmanagementapp.fragments.VisitorAcFragment
import com.example.eventmanagementapp.fragments.VisitorFavFragment
import com.example.eventmanagementapp.fragments.VisitorHomeFragment
import com.example.eventmanagementapp.fragments.VisitorSearchFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import nl.joery.animatedbottombar.AnimatedBottomBar

class VisitorHomePage : AppCompatActivity() {
    private lateinit var binding: ActivityVisitorHomePageBinding
    private var currentTabId: Int = R.id.tab_home
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVisitorHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            loadFragment(VisitorHomeFragment())
        }
        binding.bottomBar.setOnTabSelectListener(object: AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                if(currentTabId != newTab.id) {
                    currentTabId = newTab.id
                    when (newTab.id) {
                        R.id.tab_home -> loadFragment(VisitorHomeFragment())
                        R.id.tab_search -> loadFragment(VisitorSearchFragment())
                        R.id.tab_favourites -> loadFragment(VisitorFavFragment())
                        R.id.tab_profile -> loadFragment(VisitorAcFragment())
                    }
                }
            }
        })
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(currentTabId != R.id.tab_home) {
                    currentTabId = R.id.tab_home
                    binding.bottomBar.selectTabById(R.id.tab_home, false)
                    loadFragment(VisitorHomeFragment())
                } else {
                    finish()
                }
            }

        })
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.visitor_nav_host_fragment, fragment)
        transaction.commit()
    }
}