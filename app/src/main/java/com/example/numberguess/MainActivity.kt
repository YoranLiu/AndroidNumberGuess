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
    val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // use view binding(go to app->build->data_binding_base_class_source out to check)
        // In this case, we use ActivityMainBinding to bind

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_main) // R: package_name.R

        Log.d(TAG, "Secret number: ${secretNumber.secret}")
    }

    fun check(view: View) {
        var input_str = binding.edNumber.text.toString()
        var message = getString(R.string.bingo)

        if (input_str == "")
            message = getString(R.string.please_input_a_number)
        else {
            val input_num = input_str.toInt()
            if (input_num > 10 || input_num < 1)
                message = getString(R.string.please_input_a_valid_number)
            else {
                val diff = secretNumber.check_num(input_num)
                if (diff < 0)
                    message = getString(R.string.guess_bigger)
                else if (diff > 0)
                    message = getString(R.string.guess_smaller)
                //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()

    }
}
