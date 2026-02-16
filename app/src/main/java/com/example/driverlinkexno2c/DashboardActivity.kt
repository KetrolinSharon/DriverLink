package com.example.driverlinkexno2c

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class DashboardActivity : AppCompatActivity() {

    private lateinit var infoIcon: TextView
    private lateinit var sosCard: LinearLayout
    private lateinit var todayTripCard: LinearLayout

    private val CHANNEL_ID = "SOS_CHANNEL"
    private val NOTIFICATION_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        supportActionBar?.title = "DriverLink"

        infoIcon = findViewById(R.id.infoIcon)
        sosCard = findViewById(R.id.sosCard)
        todayTripCard = findViewById(R.id.todayTripCard)

        // Navigate to Add Booking
        todayTripCard.setOnClickListener {
            startActivity(Intent(this, AddBookingActivity::class.java))
        }

        // SOS Click
        sosCard.setOnClickListener {
            checkNotificationPermission()
        }

        // Popup Menu
        infoIcon.setOnClickListener {
            showPopupMenu(it)
        }

        // Context Menu
        registerForContextMenu(sosCard)
    }

    /* ---------------- PERMISSION CHECK ---------------- */

    private fun checkNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            } else {
                showEmergencyNotification()
            }

        } else {
            showEmergencyNotification()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                showEmergencyNotification()
            } else {
                Toast.makeText(
                    this,
                    "Notification Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /* ---------------- SOS NOTIFICATION ---------------- */

    private fun showEmergencyNotification() {

        // Extra safety permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        // Create Notification Channel (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Emergency Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "SOS Emergency Notifications"
                enableVibration(true)
            }

            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Emergency Alert Sent")
            .setContentText("Your emergency request has been notified.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        try {
            NotificationManagerCompat.from(this).notify(1, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    /* ---------------- OPTIONS MENU ---------------- */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_profile -> toast("Profile clicked")
            R.id.menu_settings -> toast("Settings clicked")
            R.id.menu_logout -> showLogoutDialog()
        }
        return true
    }

    /* ---------------- ALERT DIALOG ---------------- */

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Confirm Logout")
        builder.setMessage("Are you sure you want to logout?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        builder.setCancelable(false)
        builder.show()
    }

    /* ---------------- POPUP MENU ---------------- */

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

    /* ---------------- CONTEXT MENU ---------------- */

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
