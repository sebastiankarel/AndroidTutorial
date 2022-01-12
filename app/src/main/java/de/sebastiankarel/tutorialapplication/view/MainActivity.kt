package de.sebastiankarel.tutorialapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.sebastiankarel.tutorialapplication.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}