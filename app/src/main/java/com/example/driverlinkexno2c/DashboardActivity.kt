package com.example.driverlinkexno2c

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var infoIcon: TextView
    private lateinit var sosCard: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1️⃣ Set layout
        setContentView(R.layout.activity_dashboard)

        // ✅ ADD THIS LINE (THIS ENABLES OPTIONS MENU BAR)
        supportActionBar?.title = "DriverLink"



        // 2️⃣ Existing code (UNCHANGED)
        infoIcon = findViewById(R.id.infoIcon)
        sosCard = findViewById(R.id.sosCard)

        // Popup Menu
        infoIcon.setOnClickListener {
            showPopupMenu(it)
        }

        // Context Menu
        registerForContextMenu(sosCard)
    }

    /* ---------- OPTIONS MENU ---------- */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_profile -> toast("Profile clicked")
            R.id.menu_settings -> toast("Settings clicked")
            R.id.menu_logout -> toast("Logout clicked")
        }
        return true
    }

    /* ---------- POPUP MENU ---------- */
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_popup, popup.menu)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.popup_about -> toast("About clicked")
                R.id.popup_help -> toast("Help clicked")
            }
            true
        }
        popup.show()
    }

    /* ---------- CONTEXT MENU ---------- */
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.context_call -> toast("Calling support...")
            R.id.context_share -> toast("Sharing location...")
        }
        return true
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
