package com.example.bullsandcows

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList
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
   private var mnewGuess = ArrayList<Int>()


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
        randomList = (1..9).shuffled().take(4)
       generatedNumber = randomList as ArrayList<Int>
    }



    @SuppressLint("SetTextI18n")
    fun initClick() {
        resignButton.setOnClickListener(View.OnClickListener {


            notificationTV.text = myNumber.toString()
                "You lost! The Number is " + generatedNumber[0] + generatedNumber[1] + generatedNumber[2] + generatedNumber[3]
        })

        checkButton.setOnClickListener(View.OnClickListener {

            var setOfUserNumber = setOf(mnewGuess)

            if (setOfUserNumber.size != mnewGuess.size) {
                notificationTV.text = "Wrong input! enter unique digits"
            }
            if (tries >= 10) {
                resignButton.isEnabled = false
                checkButton.isEnabled = false
                notificationTV.text =
                    "You lost! The Number is " + generatedNumber[0] + generatedNumber[1] + generatedNumber[2] + generatedNumber[3]

            } else {
                checkNumber(generatedNumber,mnewGuess )
                checkWin()
            }

        })
    }

    @SuppressLint("SetTextI18n")
    private fun checkWin() {

        if (bullCounter == 4) {
            resignButton.isEnabled = false
            checkButton.isEnabled = false
            notificationTV.text = "You won in " + tries + " tries"
        }
    }


    private fun checkNumber(arrayList1: ArrayList<Int>, arrayList2: ArrayList<Int>) {

        if(arrayList1[0]==arrayList2[0]) bullCounter++
        if(arrayList1[1]==arrayList2[1]) bullCounter++
        if(arrayList1[2]==arrayList2[2]) bullCounter++
        if(arrayList1[3]==arrayList2[3]) bullCounter++

        if(arrayList1.contains(arrayList2[0])) cowCounter++
        if(arrayList1.contains(arrayList2[1])) cowCounter++
        if(arrayList1.contains(arrayList2[2])) cowCounter++
        if(arrayList1.contains(arrayList2[3])) cowCounter++
//        for(num in arrayList1){
//            for (vals in arrayList2){
//                if (arrayList1[num] == arrayList2[vals]){
//                    if (num==vals){
//                        bullCounter++
//                    }
//                    cowCounter++
//                }
//            }
//        }
        //incrementing number of trys

        tries++
        output = output + "Try : $tries. $bullCounter bulls, $cowCounter - cows \n "
        gameInfo.text = output

    }


}