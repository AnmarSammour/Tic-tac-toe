package com.example.tic_tac_toe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    private lateinit var arr: Array<Button>
    private var sign = ""
    private var count = 0
    private var winner = ""
    private var radioChecked = false
    private val winnerset = arrayOf(
        arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8),
        arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8),
        arrayOf(0, 4, 8), arrayOf(2, 4, 6))
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        initView()
        initRadioGroup()
        initButtons()
        initResetButton()
    }

    private fun initView() {
        arr = Array(9) { findViewById(resources.getIdentifier("b${it + 1}", "id", packageName)) }
    }

    private fun initRadioGroup() {
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup1)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            radioChecked = true
            sign = ""
            count = 0
            winner = ""
            arr.forEach { button ->
                button.text = ""
                button.setBackgroundColor(Color.parseColor("#FFFFFF"))
                button.isEnabled = true
            }
            if (checkedId == R.id.radioButton1) {
                for (button in arr) {
                    button.setOnClickListener { onPlayerButtonClick(button) }
                }
            } else if (checkedId == R.id.radioButton2) {
                for (button in arr) {
                    button.setOnClickListener { onComputerButtonClick(button) }
                }
            }
        }
    }

    private fun initButtons() {
        arr.forEach { button ->
            button.isEnabled = false
        }
    }

    private fun initResetButton() {
        val reset: Button = findViewById(R.id.reset)
        reset.setOnClickListener {
            sign = ""
            count = 0
            winner = ""
            arr.forEach { button ->
                button.text = ""
                button.setBackgroundColor(Color.parseColor("#FFFFFF"))
                button.isEnabled = true
            }
        }
    }

    private fun onPlayerButtonClick(button: Button) {
        if (button.text == "") {
            button.text = if (count % 2 == 0) "x" else "o"
            count++
            checkWinner()
        }
    }

    private fun onComputerButtonClick(button: Button) {
        if (button.text == "") {
            button.text = "x"
            count++
            checkWinner()
            if (count < 9) {
                performComputerTurn()
                checkWinner()
            }
        }
    }

    private fun performComputerTurn() {
        for (i in arr.indices) {
            if (arr[i].text == "") {
                arr[i].text = "o"
                break
            }
        }
        count++
    }

    private fun checkWinner() {
        for (winSet in winnerset) {
            val (a, b, c) = winSet
            if (arr[a].text == arr[b].text && arr[b].text == arr[c].text && arr[a].text != "") {
                highlightWinnerCells(a, b, c)
                winner = arr[a].text.toString()
                disableAllButtons()
                handler.postDelayed({
                    showWinnerPage(winner)
                }, 1000)
                return
            }
        }
        if (winner.isNotEmpty()) {
            handler.postDelayed({
                showWinnerPage(winner)
            }, 1000)
        } else if (count == 9) {
            disableAllButtons()
            handler.postDelayed({
                showWinnerPage("It's a draw!")
            }, 1000)
        }
    }
    private fun showWinnerPage(winnerText: String) {
        val intent = Intent(this, Result::class.java)
        intent.putExtra("winnerText", winnerText)
        startActivity(intent)
    }
    private fun highlightWinnerCells(a: Int, b: Int, c: Int) {
        arr[a].setBackgroundColor(Color.parseColor("#40F8FF"))
        arr[b].setBackgroundColor(Color.parseColor("#40F8FF"))
        arr[c].setBackgroundColor(Color.parseColor("#40F8FF"))
    }

    private fun disableAllButtons() {
        arr.forEach { button ->
            button.isEnabled = false
        }
    }
}
