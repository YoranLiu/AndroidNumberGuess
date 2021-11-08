package com.example.numberguess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.numberguess.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // use view binding(go to app->build->data_binding_base_class_source out to check)
        // In this case, we use ActivityMainBinding to bind

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_main) // R: package_name.R

        Log.d("Mainactivity", "Secret number: ${secretNumber.secret}")
    }

    fun check(view: View) {
        val input_num = binding.edNumber.text.toString().toInt()
        val diff = secretNumber.check_num(input_num)
        var message = "BINGO!!"

        if (diff < 0)
            message = "Guess bigger"
        else if (diff > 0)
            message = "Guess smaller"
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle("Result message")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
