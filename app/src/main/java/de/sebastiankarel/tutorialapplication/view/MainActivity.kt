package de.sebastiankarel.tutorialapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.loginStateLiveData.observe(this) {
            if (!it) {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_loginFragment)
            }
        }
    }

    fun showErrorSnackbar(message: String) {
        val rootView = findViewById<View>(R.id.content_layout)
        val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
            .apply {
                setActionTextColor(ContextCompat.getColor(context, R.color.white))
            }
        val textView = snackbar.view
            .findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        snackbar.show()
    }
}