package com.example.bullsandcows

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class GameFragment : Fragment() {
    private lateinit var timeTv: TextView
    private lateinit var resignButton: Button
    private lateinit var newGameButton: Button
    private lateinit var notificationTV: TextView
    private lateinit var gameInfo: TextView
    private lateinit var checkButton: Button
    private lateinit var myNumber: TextView
    private lateinit var saveButton:ImageButton
    private var generatedNumber = arrayListOf<Int>()
    private lateinit var mActivity: MainActivity
    private var minutes = 0
    private var seconds = 0
    private var sMinutes = ""
    private var sSeconds = ""
    private var miliseconds: Long = 300000
    private var countDown: Long = 1000

    //Declaring GameModel new instance
    private lateinit var mGameModel: GameModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_game, container, false)
        //initialising mGameModel
        mGameModel = GameModel()

        getTimer()
        saveButton = view.findViewById(R.id.saveButton)
        timeTv = view.findViewById(R.id.timeTv)
        resignButton = view.findViewById(R.id.buttonResign)
        newGameButton = view.findViewById(R.id.newGameButton)
        notificationTV = view.findViewById(R.id.notificationTV)
        gameInfo = view.findViewById(R.id.gameInfo)
        myNumber = view.findViewById(R.id.myNumber)
        checkButton = view.findViewById(R.id.checkButton)
        mActivity = activity as MainActivity
        mGameModel.output = ""
        initClick()
        generatedNumber = mGameModel.generateRandom()



        var bundle = Bundle()
        // You can use bundle.putxx everything such as String...float..
        bundle.putSerializable(Util.GAMEARCHIVE_KEY,mGameModel.gameHistory)
        mActivity.mHistoryFragment.arguments = bundle

          return view
    }


    @SuppressLint("SetTextI18n")
    fun initClick() {
        saveButton.setOnClickListener {
            mGameModel.createRecordToArchive(mGameModel.getGameDate(),mGameModel.tries,generatedNumber.toString())
            putInBundle()
        }
        resignButton.setOnClickListener {
            buttonsDesable()
            notificationTV.text =
                "You lost! The Number is " + generatedNumber[0] + generatedNumber[1] + generatedNumber[2] + generatedNumber[3]
        }

        checkButton.setOnClickListener {


            if (myNumber.text.toString().trim().length < 4) {
                buttonsDesable()
                notificationTV.text = "Text your 4-digit number"
                buttonsEnable()
            } else {
                notificationTV.text = ""
                mGameModel.gettingUserNumber(myNumber)

                if (!mGameModel.checkInput()) {
                    buttonsDesable()
                    notificationTV.text = "Wrong input! enter unique digits "
                    buttonsEnable()


                } else if (!mGameModel.checkTries()) {
                    buttonsDesable()
                    notificationTV.text =
                        "You lost! The Number is " + generatedNumber[0] + generatedNumber[1] + generatedNumber[2] + generatedNumber[3]
                } else {
                    mGameModel.checkNumber(mGameModel.generatedNumber, mGameModel.mTestArray)
                    gameInfo.text = mGameModel.output
                    checkWin()
                    myNumber.text = ""
                }
            }
            newGameButton.setOnClickListener {
                newGame()
            }
        }
    }

    fun newGame() {
        buttonsEnable()
        getTimer()
        gameInfo.text = ""
        notificationTV.text = ""
        mGameModel.newGame()
        myNumber.text = ""

    }
    private fun checkWin() {
        if (mGameModel.checkWinGame()) {
            buttonsDesable()
            notificationTV.text = "You won in ${mGameModel.tries} tries"
        }
    }

    private fun buttonsDesable() {
        resignButton.isEnabled = false
        checkButton.isEnabled = false
    }

    private fun buttonsEnable() {
        resignButton.isEnabled = true
        checkButton.isEnabled = true
    }
    fun getTimer(){
        object : CountDownTimer(miliseconds, countDown) {
            var millisUntilFinished: Long = 300000
            override fun onTick(millisUntilFinished: Long) {
                var miliseconds = millisUntilFinished
                fun milisecToSec() {
                    minutes = miliseconds.toInt() / 60000
                    seconds = ((miliseconds % 60000) / 1000).toInt()
                }
                milisecToSec()
                sMinutes = minutes.toString()
                sSeconds = seconds.toString()
                if (sSeconds.length < 2) {
                    sSeconds = "0$sSeconds"
                }
                timeTv.setText("Time remaining: $sMinutes:$sSeconds")

            }

            override fun onFinish() {
                timeTv.setText("Done!")

            }

        }.start()
    }

    fun putInBundle(){
        var bundle = Bundle()

        bundle.putSerializable(Util.GAMEARCHIVE_KEY,mGameModel.gameHistory)
        mActivity.mHistoryFragment.arguments = bundle
    }

}


