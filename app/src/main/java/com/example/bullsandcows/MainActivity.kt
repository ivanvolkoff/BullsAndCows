package com.example.bullsandcows

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var resignButton: Button
    private lateinit var newGameButton: Button
    private lateinit var notificationTV: TextView
    private lateinit var gameInfo: TextView
    private lateinit var checkButton: Button
    private lateinit var myNumber: TextView
    private lateinit var output: String
    private var bullCounter: Int = 0
    private var cowCounter: Int = 0
    private var tries: Int = 0
    private var seed: Int = 0
    private lateinit var r: Random
    private lateinit var randomList: List<Int>
    private var generatedNumber = arrayListOf<Int>()
    private var mnewGuess = IntArray(4)
    private var mTestArray: ArrayList<Int> = ArrayList()
    var temp: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        resignButton = findViewById(R.id.buttonResign)
        newGameButton = findViewById(R.id.newGameButton)
        notificationTV = findViewById(R.id.notificationTV)
        gameInfo = findViewById(R.id.gameInfo)
        myNumber = findViewById(R.id.myNumber)
        checkButton = findViewById(R.id.checkButton)

        output = ""
        initClick()
        r = Random(seed)
        generateRandom()
    }

    private fun generateRandom() {
        val randomList = (1..9).shuffled().take(4)
        //generate first digit
        generatedNumber.add(randomList[0])
        //generate second digit
        generatedNumber.add(randomList[1])
        //generate third digit
        generatedNumber.add(randomList[2])
        //generate forth digit
        generatedNumber.add(randomList[3])
    }

    @SuppressLint("SetTextI18n")
    fun initClick() {


        resignButton.setOnClickListener(View.OnClickListener {
            resignButton.isEnabled = false
            checkButton.isEnabled = false
            notificationTV.text =
                "You lost! The Number is " + generatedNumber[0] + generatedNumber[1] + generatedNumber[2] + generatedNumber[3]
        })
        checkButton.setOnClickListener {

            gettingUserNumber(myNumber)


//            Log.i("logArrayEllements", mTestArray.toString())
            if (mTestArray.size != mTestArray.toSet().size || mTestArray.size > 4) {
                resignButton.isEnabled = false
                checkButton.isEnabled = false
                notificationTV.text = "Wrong input! enter unique digits "
                resignButton.isEnabled = false
                checkButton.isEnabled = false

            } else if (tries >= 10) {
                resignButton.isEnabled = false
                checkButton.isEnabled = false
                notificationTV.text =
                    "You lost! The Number is " + generatedNumber[0] + generatedNumber[1] + generatedNumber[2] + generatedNumber[3]
            } else {
                checkNumber(generatedNumber, mTestArray)
                checkWin()
                bullCounter = 0
                cowCounter = 0
            }
        }
        newGameButton.setOnClickListener {
            newGame()
        }
    }

    private fun newGame() {
        resignButton.isEnabled = true
        checkButton.isEnabled = true
        gameInfo.text = ""
        notificationTV.text = ""
        generatedNumber.clear()
        mTestArray.clear()
        output = ""
        generateRandom()
        initClick()
        tries = 0
        myNumber.text = ""
    }

    private fun checkWin() {
        if (bullCounter == 4) {
            resignButton.isEnabled = false
            checkButton.isEnabled = false
            notificationTV.text = "You won in $tries tries"
        }
    }

    private fun checkNumber(arrayGenerated: ArrayList<Int>, arrayList2: ArrayList<Int>) {
        bullCounter = 0
        cowCounter = 0
        for (i in arrayGenerated.indices) {
            for (j in arrayList2.indices) {
                if (arrayGenerated[i] == arrayList2[j]) {
                    if (i == j) {
                        bullCounter++
                        continue
                    }
                    cowCounter++
                }
            }
        }
        //incrementing number of tries
        tries++
        output += "Try : $tries. Your number is : $temp.  $bullCounter bulls, $cowCounter - cows \n "
        gameInfo.text = output
    }

    private fun gettingUserNumber(textView: TextView): ArrayList<Int> {
        mTestArray.clear()
        temp = textView.text.toString().toInt()
        var newGuess: IntArray
        newGuess = temp.toString().chars().map { c: Int -> c - '0'.toInt() }.toArray()
        for (i in newGuess.indices) {
            mTestArray.add(newGuess[i])
        }
        return mTestArray
    }
}