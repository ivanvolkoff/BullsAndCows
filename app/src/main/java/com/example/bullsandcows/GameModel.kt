package com.example.bullsandcows

import android.widget.TextView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class GameModel() {

    var bullCounter: Int = 0
    var cowCounter: Int = 0
    var dateInString:String = getGameDate()
    private lateinit var r: Random
    var generatedNumber = arrayListOf<Int>()
    var mTestArray: ArrayList<Int> = ArrayList()
    var temp: Int = 0
    private lateinit var mActivity: MainActivity
    private var mDateTime = LocalDateTime.now()
    private lateinit var mGameArchive : GameArchive
    var tries: Int = 0
    var output: String = ""
    var gameHistory  = arrayListOf<GameArchive>()


    fun generateRandom(): ArrayList<Int> {
        val randomList = (1..9).shuffled().take(4)

        //generate first digit
        generatedNumber.add(randomList[0])
        //generate second digit
        generatedNumber.add(randomList[1])
        //generate third digit
        generatedNumber.add(randomList[2])
        //generate forth digit
        generatedNumber.add(randomList[3])

        return generatedNumber

    }


    fun gettingUserNumber(textView: TextView): ArrayList<Int> {
        mTestArray.clear()
        temp = textView.text.toString().toInt()
        var newGuess: IntArray
        newGuess = temp.toString().chars().map { c: Int -> c - '0'.toInt() }.toArray()
        for (i in newGuess.indices) {
            mTestArray.add(newGuess[i])
        }
        return mTestArray
    }

    fun checkNumber(arrayGenerated: ArrayList<Int>, arrayList2: ArrayList<Int>) {
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
        output += "Try $tries: Your number is : $temp  $bullCounter bulls, $cowCounter - cows\n"


    }

    fun checkInput(): Boolean {
        if (mTestArray.size != 4) return false
        else if (mTestArray.size != mTestArray.toSet().size) {
            return false
        }

        return true
    }

    fun checkTries(): Boolean {
        if (tries > 9) {
            return false
        }
        return true
    }

    fun checkWinGame(): Boolean {
        if (bullCounter == 4) {
            return true
        }
        return false
    }

    fun newGame() {
        generateRandom()
        generatedNumber.clear()
        mTestArray.clear()
        output = ""
        generatedNumber = generateRandom()
        tries = 0
    }

    fun getGameDate():String{
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formatted = current.format(formatter)

      return formatted
    }

    fun createRecordToArchive(gameName:String, gameTries:Int, randomNumGenerated:String){
        mGameArchive = GameArchive(gameName,gameTries,randomNumGenerated)
        gameHistory.add(mGameArchive)
    }






}




