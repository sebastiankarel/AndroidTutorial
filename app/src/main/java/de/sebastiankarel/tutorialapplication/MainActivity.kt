package de.sebastiankarel.tutorialapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = AppPreferencesImpl(this)

        val et = findViewById<EditText>(R.id.et)
        et.setText(prefs.getEnteredText())
        et.addTextChangedListener {
            prefs.setEnteredText(it.toString())
        }

    }
}