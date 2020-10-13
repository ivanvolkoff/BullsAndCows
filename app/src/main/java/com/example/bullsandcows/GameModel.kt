package com.example.bullsandcows

import android.widget.TextView
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class GameModel() : Serializable {

    var mBullCounter: Int = 0
    var mCowCounter: Int = 0
    var mGeneratedNumber = arrayListOf<Int>()
    var mUserNumbers: ArrayList<Int> = ArrayList()
    var mTemp: Int = 0
    var mTries: Int = 0
    var mOutput: String = ""
    var mGameHistory = arrayListOf<GameArchive>()
    lateinit var date: String
    var mTotalTries = 10
    var triesOutput = ""


// list with rundom numbers

    fun generateRandom(): ArrayList<Int> {
        val randomList = (1..9).shuffled().take(4)
        mGeneratedNumber.addAll(randomList)

        return mGeneratedNumber
    }

    //getting user input

    fun gettingUserNumber(textView: TextView): ArrayList<Int> {
        //clear list from privious turn
        mUserNumbers.clear()
        //getting users imput
        mTemp = textView.text.toString().toInt()
        var newGuess: IntArray
        //numbers to
        newGuess = mTemp.toString().chars().map { c: Int -> c - '0'.toInt() }.toArray()
        for (i in newGuess.indices) {
            mUserNumbers.add(newGuess[i])
        }
        return mUserNumbers
    }


    // check input for empty text, for number of digits and uniqness of the digits

    fun checkInput(): Boolean {
        if (mUserNumbers.size != 4) return false
        else if (mUserNumbers.size != mUserNumbers.toSet().size) {
            return false
        }

        return true
    }


    //check bull counter for constatating victory
    fun checkWinGame(): String {
        if (mTries <= 10 && mBullCounter == 4) {
            return "Congratulations you won!!!"
        } else {
            return ""
        }
    }

//setting up a new game


    fun newGame() {
        generateRandom()
        mGeneratedNumber.clear()
        mUserNumbers.clear()
        mOutput = ""
        mGeneratedNumber = generateRandom()
        mTries = 0
        mTotalTries = 10
    }


    //comparing  random number with user number

    fun checkNumber(arrayGenerated: ArrayList<Int>, arrayList2: ArrayList<Int>) {
        mBullCounter = 0
        mCowCounter = 0
        for (i in arrayGenerated.indices) {
            for (j in arrayList2.indices) {
                if (arrayGenerated[i] == arrayList2[j]) {
                    if (i == j) {
                        mBullCounter++
                        continue
                    }
                    mCowCounter++
                }
            }
        }
        //incrementing number of tries
        mTotalTries--
        mTries++
        mOutput += "Try $mTries: number is: $mTemp. Bulls:$mBullCounter, cows:$mCowCounter\n"
        triesOutput = "$mTotalTries  tries left "


    }


    //getting game date
    fun getGameDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formatted = current.format(formatter)
        return formatted
    }

    // make record to Game Archive


}


