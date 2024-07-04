package com.example.eventmanagementapp

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.eventmanagementapp.databinding.ActivityOrganizerHomePageBinding
import com.example.eventmanagementapp.fragments.OrganizerAcFragment
import com.example.eventmanagementapp.fragments.OrganizerFavFragment
import com.example.eventmanagementapp.fragments.OrganizerHomeFragment
import com.example.eventmanagementapp.fragments.OrganizerSearchFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class OrganizerHomePage : AppCompatActivity() {
    private lateinit var binding: ActivityOrganizerHomePageBinding
    private var currentTabId: Int = R.id.tab_home
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrganizerHomePageBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            loadFragment(OrganizerHomeFragment())
        }


        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                if (currentTabId != newTab.id) {
                    currentTabId = newTab.id
                    when (newTab.id) {
                        R.id.tab_home -> {
                            loadFragment(OrganizerHomeFragment())
                        }

                        R.id.tab_search -> {
                            loadFragment(OrganizerSearchFragment())
                        }

                        R.id.tab_favourites -> {
                            loadFragment(OrganizerFavFragment())
                        }

                        R.id.tab_profile -> {
                            loadFragment(OrganizerAcFragment())
                        }
                    }
                }

            }
        })
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (currentTabId != R.id.tab_home) {
                    currentTabId = R.id.tab_home
                    binding.bottomBar.selectTabById(R.id.tab_home, false)
                    loadFragment(OrganizerHomeFragment())
                } else {
                    finish()
                }
            }
        })
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }
}