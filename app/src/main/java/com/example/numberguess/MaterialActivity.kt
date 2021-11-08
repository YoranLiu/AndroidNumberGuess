package com.example.numberguess

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.numberguess.databinding.ActivityMaterialBinding
import com.example.numberguess.databinding.ContentMaterialBinding
import kotlinx.android.synthetic.main.content_material.*
import org.w3c.dom.Text

class MaterialActivity : AppCompatActivity() {
//    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMaterialBinding

    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        Log.d(TAG, "Secret number: ${secretNumber.secret}")

//        val navController = findNavController(R.id.nav_host_fragment_content_material)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.replay_confirm))
                .setMessage(getString(R.string.are_you_sure_to_replay))
                .setPositiveButton(getString(R.string.yes), {dialog, which ->
                    secretNumber.reset()
                    ed_number.setText("")
                    counter.setText(secretNumber.count.toString())
                    Log.d(TAG, "Secret number: ${secretNumber.secret}")
                })
                .setNeutralButton(getString(R.string.no), null)
                .show()
        }
    }

    fun check(view: View) {
//        var input_str = binding.edNumber.text.toString()
//        val input_str = findViewById<EditText>(R.id.ed_number).text.toString()

        val input_str = ed_number.text.toString()
        var message = getString(R.string.bingo)

        // fool-proof: input can not be empty
        if (input_str == "")
            message = getString(R.string.please_input_a_number)
        else {
            val input_num = input_str.toInt()

            // fool-proof: limit the input number to be legal range
            if (input_num > 10 || input_num < 1)
                message = getString(R.string.please_input_a_valid_number)
            else {
                Log.d(TAG, "Input number: $input_num")
                val diff = secretNumber.check_num(input_num)

                counter.setText(secretNumber.count.toString())

                if (diff < 0)
                    message = getString(R.string.guess_bigger)
                else if (diff > 0)
                    message = getString(R.string.guess_smaller)
                else {
                    if (secretNumber.count < 3)
                        message = getString(R.string.excellent_result) + secretNumber.secret.toString()
                }
                //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_material)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}