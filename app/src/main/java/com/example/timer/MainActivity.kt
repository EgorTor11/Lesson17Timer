package com.example.timer

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var txtView: TextView
    lateinit var btnStart: Button
    lateinit var edTime: EditText
    val scope = CoroutineScope(Dispatchers.IO)
    var count = 1
    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtView = findViewById(R.id.textView)
        edTime = findViewById(R.id.edTime)
        btnStart = findViewById(R.id.button)
        btnStart.setBackgroundColor(Color.GREEN)

    }

    fun onClickBtn(view: View) {

        count++

        if (count % 2 == 0) {
            job = scope.launch {
                withContext(Dispatchers.Main) {
                    if (!edTime.text.toString().equals("")) {
                        for (i in Integer.valueOf(edTime.text.toString()) downTo 0) {
                            txtView.text = i.toString()
                            delay(1000)
                        }
                    }
                    if (txtView.text.toString().equals("0")) {

                        Toast.makeText(applicationContext, "отсчет завершен", Toast.LENGTH_LONG).show()
                        vibro()
                        btnStart.setBackgroundColor(Color.GREEN)
                        btnStart.setText("START")
                        count++
                        job.cancel()
                    }
                }

            }

            btnStart.setText("STOP")
            btnStart.setBackgroundColor(Color.RED)
        } else {
            btnStart.setBackgroundColor(Color.GREEN)
            btnStart.setText("START")
            job.cancel()
            vibro()
            txtView.text = "0"
        }

    }

    fun vibro() {
        // Kotlin
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val canVibrate: Boolean = vibrator.hasVibrator()
        val milliseconds = 1000L

        if (canVibrate) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // API 26
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        milliseconds,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                // This method was deprecated in API level 26
                vibrator.vibrate(milliseconds)
            }
        }
    }

}