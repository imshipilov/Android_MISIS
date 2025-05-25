package com.shipilov.lab2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val number_field_1 = findViewById<EditText>(R.id.number_field_1)
        val number_field_2 = findViewById<EditText>(R.id.number_field_2)
        val button_calculate = findViewById<Button>(R.id.button_calculate)
        val result_field = findViewById<TextView>(R.id.result_field)
        val button_reset = findViewById<Button>(R.id.button_reset)
        val operator_spinner = findViewById<Spinner>(R.id.operator_spinner)

        button_reset.setOnClickListener {
            number_field_1.text.clear()
            number_field_2.text.clear()

            result_field.text = getString(R.string.result_text)
        }
        button_calculate.setOnClickListener {
            val n1 = number_field_1.text.toString().toFloatOrNull() ?: 0f
            val n2 = number_field_2.text.toString().toFloatOrNull() ?: 0f
            val operator = operator_spinner.selectedItem.toString()

            val result = when (operator) {
                "+" -> n1 + n2
                "-" -> n1 - n2
                "*" -> n1 * n2
                "/" -> if (n2 != 0f) n1 / n2 else "Ошибка (/ 0)"
                else -> "Ошибка"
            }

            result_field.text = getString(R.string.result_text) + " " + result.toString()
        }
    }
}