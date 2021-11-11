package com.example.numberguess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.numberguess.databinding.ActivityMaterialBinding
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaterialBinding

    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        binding = ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        Log.d(TAG, "Secret number: ${secretNumber.secret}")
        
        val rec_nickname = getSharedPreferences("guess_member_list", MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        val rec_counter = getSharedPreferences("guess_member_list", MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)

        Log.d(TAG, "guess_member_list: " + rec_nickname + "/" + rec_counter)

        binding.fab.setOnClickListener { view ->
            replay()
        }
    }

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.replay_confirm))
            .setMessage(getString(R.string.are_you_sure_to_replay))
            .setPositiveButton(getString(R.string.yes), { dialog, which ->
                secretNumber.reset()
                ed_number.setText("")
                counter.setText(secretNumber.count.toString())
                Log.d(TAG, "Secret number: ${secretNumber.secret}")
            })
            .setNeutralButton(getString(R.string.no), null)
            .show()
    }

    // register -> result contracts -> start activity result
    var resultLauncher = // need to registerForActivityResult before onStart of the Activity
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
            if (result.resultCode == RESULT_OK) {
                val nick = result.data?.getStringExtra("NICKNAME")
                Log.d(TAG, "result data: " + nick)
                replay()
            }
        }

    fun check(view: View) {
//        var input_str = binding.edNumber.text.toString()
//        val input_str = findViewById<EditText>(R.id.ed_number).text.toString()

        val input_str = ed_number.text.toString()
        var message = getString(R.string.bingo)
        var is_corrected = false

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
                    is_corrected = true
                    if (secretNumber.count < 3)
                        message = getString(R.string.excellent_result) + secretNumber.secret.toString()
                }
                //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
        Log.d(TAG, "check: is corrected: $is_corrected")

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), {dialog, which ->
                if (is_corrected) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("Counter", secretNumber.count)
                    //startActivity(intent)
                    resultLauncher.launch(intent)
                }
            })
            .show()
    }
}