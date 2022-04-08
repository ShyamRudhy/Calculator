package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // declare fragment binding
    private lateinit var binding: ActivityMainBinding

    private lateinit var tvInput: TextView
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        tvInput = binding.tvInput

    }

    fun onDigit(view: View) {
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastDot = false
            lastNumeric = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }

    }


    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDelete(view : View){
        val removeLastChar = tvInput.text.toString()
        if (removeLastChar != "0"){
            tvInput.text = removeLastChar.dropLast(1)
        }

    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var vaule = result
        if (result.contains(".0"))
            vaule = result.substring(0, result.length - 2)
        return vaule

    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)

                }
                when {
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }
}