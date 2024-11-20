package com.example.datetime

import android.content.ClipData.Item
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CardActivity : AppCompatActivity() {
    private lateinit var cardToolbar : androidx.appcompat.widget.Toolbar

    private lateinit var showImageIV : ImageView
    private lateinit var lastNameTV : TextView
    private lateinit var nameTV : TextView
    private lateinit var phoneTV : TextView
    private lateinit var ageTV : TextView
    private lateinit var untilBirthdayTV : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showImageIV = findViewById(R.id.showImageIV)
        lastNameTV = findViewById(R.id.lastNameTV)
        nameTV = findViewById(R.id.nameTV)
        phoneTV = findViewById(R.id.phoneTV)
        ageTV = findViewById(R.id.ageTV)
        untilBirthdayTV = findViewById(R.id.untilBirthdayTV)

        cardToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(cardToolbar)

        showImageIV.setImageURI(Uri.parse(intent.getStringExtra("image")))
        val lastName = intent.extras?.get("lastName")
        lastNameTV.text = lastName.toString()
        val name = intent.extras?.get("name")
        nameTV.text = name.toString()
        val phone = intent.extras?.get("phone")
        phoneTV.text = phone.toString()
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDate = Date()
        val birthdate = sdf.parse(intent.extras?.get("date").toString())
        val age = currentDate.year - (birthdate?.year ?: Date().year)
        ageTV.text = "Возраст: $age"

        val nextBirthday = Calendar.getInstance().apply {
            if (birthdate != null) {
                time = birthdate
            }
            set(Calendar.YEAR, currentDate.year + 1900 + if (currentDate.after(this.time)) 1 else 0)
        }

        val daysUntilBirthday = ((nextBirthday.timeInMillis - currentDate.time) / (1000 * 60 * 60 * 24)).toInt()
        var monthsUntilBirthday = nextBirthday.get(Calendar.MONTH) - currentDate.month

        if (monthsUntilBirthday < 0) {
            monthsUntilBirthday += 12
        }

        untilBirthdayTV.text = "До дня рождения: $daysUntilBirthday дней, $monthsUntilBirthday месяцев"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.exit -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}