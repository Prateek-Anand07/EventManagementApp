package com.example.eventmanagementapp

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import java.lang.Exception

class FilterMenu {
    var selectedCategory: String? = null
    fun FiltersMenu(context: Context, view: View) {
        val pop = PopupMenu(context, view)
        pop.inflate(R.menu.filter_item)
        pop.setOnMenuItemClickListener {
            when (it!!.itemId) {
                R.id.movies -> {
                    selectedCategory = "Movies"
                    Toast.makeText(context, "Movies selected", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.comedy -> {
                    selectedCategory = "Comedy"
                    Toast.makeText(context, "Comedies selected", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.sports -> {
                    selectedCategory = "Sports"
                    Toast.makeText(context, "Sports selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        try {
            val fieldMpopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMpopup.isAccessible = true
            val mPopup = fieldMpopup.get(pop)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (_: Exception) {

        } finally {
            pop.show()
        }
    }
}