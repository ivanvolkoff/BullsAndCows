package com.example.bullsandcows

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var resignButton: Button
    private lateinit var checkButton: Button
    private lateinit var tv_input: TextView
    private lateinit var tvOutput: TextView

    private lateinit var minusA: Button
    private lateinit var minusB: Button
    private lateinit var minusC: Button
    private lateinit var minusD: Button
    private lateinit var plusA: Button
    private lateinit var plusB: Button
    private lateinit var plusC: Button
    private lateinit var plusD: Button
    private lateinit var numberA: TextView
    private lateinit var numberB: TextView
    private lateinit var numberC: TextView
    private lateinit var numberD: TextView

    private lateinit var r: Random
    private var guessA: Int = 1
    private var guessB: Int = 2
    private var guessC: Int = 3
    private var guessD: Int = 4

    private var generatedA: Int? = null
    private var generatedB: Int? = null
    private var generatedC: Int? = null
    private var generatedD: Int? = null
    private var bullCounter: Int = 0
    private var cowCounter: Int = 0
    private var tries: Int = 0
    private lateinit var output: String
    private var seed: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        minusA = findViewById(R.id.a_minus_a)
        minusB = findViewById(R.id.b_minus_b)
        minusC = findViewById(R.id.c_minus_c)
        minusD = findViewById(R.id.d_minus_d)

        plusA = findViewById(R.id.a_plus_a)
        plusB = findViewById(R.id.b_plus_b)
        plusC = findViewById(R.id.c_plus_c)
        plusD = findViewById(R.id.d_plus_d)

        numberA = findViewById(R.id.number_a)
        numberB = findViewById(R.id.number_b)
        numberC = findViewById(R.id.number_c)
        numberD = findViewById(R.id.number_d)

        resignButton = findViewById(R.id.resignButton)
        checkButton = findViewById(R.id.checkButton)
        tv_input = findViewById(R.id.tv_input)
        tvOutput = findViewById(R.id.tv_output)

        output = ""
        initClick()

        r = Random(seed)
        generateRandom()


    }

    fun pickNumbers() {
        val randomList = (0..9).shuffled().take(4)

    }

    fun initClick() {
        minusA.setOnClickListener(View.OnClickListener {
            if (guessA > 0) {
                guessA--
            }
            numberA.text = guessA.toString()
        })
        minusB.setOnClickListener(View.OnClickListener {
            if (guessB > 0) {
                guessB--
            }
            numberB.text = guessB.toString()
        })
        minusC.setOnClickListener(View.OnClickListener {
            if (guessC > 0) {
                guessC--
            }
            numberC.text = guessC.toString()
        })
        minusD.setOnClickListener(View.OnClickListener {
            if (guessD > 0) {
                guessD--
            }
            numberD.text = guessD.toString()
        })
        plusA.setOnClickListener(View.OnClickListener {
            if (guessA < 9) {
                guessA++
            }
            numberA.text = guessA.toString()
        })
        plusB.setOnClickListener(View.OnClickListener {
            if (guessB < 9) {
                guessB++
            }
            numberB.text = guessB.toString()
        })
        plusC.setOnClickListener(View.OnClickListener {
            if (guessC < 9) {
                guessC++
            }
            numberC.text = guessC.toString()
        })
        plusD.setOnClickListener(View.OnClickListener {
            if (guessD < 9) {
                guessD++
            }
            numberD.text = guessD.toString()
        })


        resignButton.setOnClickListener(View.OnClickListener {
            tv_input.text =
                "You lost! The Number is " + generatedA + " " + generatedB + " " + generatedC + " " + generatedD
        })


        checkButton.setOnClickListener(View.OnClickListener {
            if (numberA == numberB || numberA == numberC || numberD == numberD||
                    numberB ==numberC||numberB==numberD||numberC==numberD) {

                tv_input.text = "Wrong input! enter unique digits"

            }
            if (tries >= 10) {
                resignButton.isEnabled = false
                checkButton.isEnabled = false
                tv_input.text =
                    "You lost! The Number is " + generatedA + " " + generatedB + " " + generatedC + " " + generatedD


            } else {
                checkNumber()
                checkWin()
                bullCounter = 0
                cowCounter = 0
            }

        })
    }

    @SuppressLint("SetTextI18n")
    private fun checkWin() {

        if (bullCounter == 4) {
            resignButton.isEnabled = false
            checkButton.isEnabled = false
            tv_input.text = "You won in " + tries + " tries"

        }


    }


    private fun generateRandom() {

        val randomList = (0..9).shuffled().take(4)
        //generate first digit
        generatedA = randomList[0]
        //generate second digit
        generatedB = randomList[1]
        //generate third digit
        generatedC = randomList[2]
        //generate forth digit
        generatedD = randomList[3]


    }

    fun checkNumber() {

        //bulls
        if (guessA == generatedA) {
            bullCounter++
        }
        if (guessB == generatedB) {
            bullCounter++
        }
        if (guessC == generatedC) {
            bullCounter++
        }
        if (guessD == generatedD) {
            bullCounter++
        }

        //cows
        if (guessA == generatedB || guessA == generatedC || guessA == generatedD) {
            cowCounter++
        }
        if (guessB == generatedA || guessB == generatedC || guessB == generatedD) {
            cowCounter++
        }
        if (guessC == generatedA || guessC == generatedB || guessC == generatedD) {
            cowCounter++
        }
        if (guessD == generatedA || guessD == generatedB || guessD == generatedC) {
            cowCounter++
        }

        //incrementing number of trys
        tries++

        output = output + "Try : $tries. $bullCounter  bulls, $cowCounter - cows \n "
        tvOutput.text = output

    }


}