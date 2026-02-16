package com.example.driverlinkexno2c

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AddBookingActivity : AppCompatActivity() {

    private lateinit var travelDate: EditText
    private lateinit var travelTime: EditText
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_booking)

        supportActionBar?.title = "Add Booking"

        travelDate = findViewById(R.id.travelDate)
        travelTime = findViewById(R.id.etTravelTime)
        saveBtn = findViewById(R.id.saveBookingBtn)

        // Date Picker
        travelDate.setOnClickListener {
            showDatePicker()
        }

        // Time Picker
        travelTime.setOnClickListener {
            showTimePicker()
        }

        // Save Button
        saveBtn.setOnClickListener {
            Toast.makeText(this, "Booking Saved Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    /* ---------------- DATE PICKER ---------------- */

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->

                val formattedDate =
                    "$selectedDay/${selectedMonth + 1}/$selectedYear"

                travelDate.setText(formattedDate)

            },
            year,
            month,
            day
        )

        datePicker.show()
    }

    /* ---------------- TIME PICKER ---------------- */

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->

                val formattedTime =
                    String.format("%02d:%02d", selectedHour, selectedMinute)

                travelTime.setText(formattedTime)

            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }
}
