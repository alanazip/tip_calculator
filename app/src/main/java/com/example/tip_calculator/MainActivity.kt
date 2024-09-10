package com.example.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tip_calculator.R

class MainActivity : AppCompatActivity() {

    private lateinit var billTotalEditText: EditText
    private lateinit var tip10Button: Button
    private lateinit var tip15Button: Button
    private lateinit var tip20Button: Button
    private lateinit var tip25Button: Button
    private lateinit var customTipButton: Button
    private lateinit var decreaseButton: Button
    private lateinit var increaseButton: Button
    private lateinit var splitCountText: TextView
    private lateinit var totalPerPersonAmount: TextView
    private lateinit var billAmountText: TextView
    private lateinit var tipAmountText: TextView
    private var billTotal: Double = 0.0
    private var tipPercentage: Double = 0.2
    private var numberOfPeople: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        billTotalEditText = findViewById(R.id.billTotalEditText)
        tip10Button = findViewById(R.id.tip10Button)
        tip15Button = findViewById(R.id.tip15Button)
        tip20Button = findViewById(R.id.tip20Button)
        tip25Button = findViewById(R.id.tip25Button)
        customTipButton = findViewById(R.id.customTipButton)
        decreaseButton = findViewById(R.id.decreaseButton)
        increaseButton = findViewById(R.id.increaseButton)
        splitCountText = findViewById(R.id.splitCountText)
        totalPerPersonAmount = findViewById(R.id.totalPerPersonAmount)
        billAmountText = findViewById(R.id.billAmountText)
        tipAmountText = findViewById(R.id.tipAmountText)

        billTotalEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                billTotal = s.toString().toDoubleOrNull() ?: 0.0
                calculateTip()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val tipButtons = listOf(tip10Button, tip15Button, tip20Button, tip25Button, customTipButton)
        tipButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                tipPercentage = when (index) {
                    0 -> 0.10
                    1 -> 0.15
                    2 -> 0.20
                    3 -> 0.25
                    else -> 0.0 // Custom tip can be added later
                }
                calculateTip()
            }
        }

        decreaseButton.setOnClickListener {
            if (numberOfPeople > 1) {
                numberOfPeople--
                splitCountText.text = numberOfPeople.toString()
                calculateTip()
            }
        }

        increaseButton.setOnClickListener {
            numberOfPeople++
            splitCountText.text = numberOfPeople.toString()
            calculateTip()
        }
    }

    private fun calculateTip() {
        val tipAmount = billTotal * tipPercentage
        val total = billTotal + tipAmount
        val totalPerPerson = total / numberOfPeople

        totalPerPersonAmount.text = "R$%.2f".format(totalPerPerson)
        billAmountText.text = "conta: R$%.2f".format(billTotal)
        tipAmountText.text = "gorjeta: R$%.2f".format(tipAmount)
    }
}