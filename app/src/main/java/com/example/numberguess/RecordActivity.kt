package com.example.numberguess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.numberguess.databinding.ActivityRecordBinding


class RecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val count = intent.getIntExtra("Counter", -1) // get tag from intent in MainActivity
        binding.counter.setText(count.toString())

        binding.saveBtn.setOnClickListener {view ->
            val nick = binding.nickname.text.toString()

            getSharedPreferences("guess_member_list", MODE_PRIVATE)
                .edit()
                .putString("REC_NICKNAME", nick)
                .putInt("REC_COUNTER", count)
                .apply()
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

            val intent = Intent()
            intent.putExtra("NICKNAME", nick)
            setResult(RESULT_OK, intent)

            finish()
        }
    }
}