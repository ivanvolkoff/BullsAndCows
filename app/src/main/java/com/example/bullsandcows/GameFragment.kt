package com.example.bullsandcows

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameFragment : Fragment(), Runnable {

    private lateinit var timeTv: TextView
    private lateinit var resignButton: Button
    private lateinit var newGameButton: Button
    private lateinit var notificationTV: TextView
    private lateinit var gameInfo: TextView
    private lateinit var checkButton: Button
    private lateinit var myNumber: TextView
    private lateinit var saveButton: ImageButton
    private var generatedNumber = arrayListOf<Int>()
    private lateinit var mActivity: MainActivity
    private var mMinutes = ""
    private var mSeconds = ""
    private var miliseconds: Long = 300000
    private lateinit var mGameModel: GameModel
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_game, container, false)

        //initialising mGameModel
        mGameModel = GameModel()
        saveButton = view.findViewById(R.id.saveButton)
        timeTv = view.findViewById(R.id.timeTv)
        resignButton = view.findViewById(R.id.buttonResign)
        newGameButton = view.findViewById(R.id.newGameButton)
        notificationTV = view.findViewById(R.id.notificationTV)
        gameInfo = view.findViewById(R.id.gameInfo)
        myNumber = view.findViewById(R.id.myNumber)
        checkButton = view.findViewById(R.id.checkButton)
        mActivity = activity as MainActivity
        mGameModel.mOutput = ""


        initClick()
        generatedNumber = mGameModel.generateRandom()
         // caling timer
        timerSetUp()

        //put in bundle


        var bundle = Bundle()
        // You can use bundle.putxx everything such as String...float..
        bundle.putSerializable(Util.GAMEARCHIVE_KEY, mGameModel.mGameHistory)
        mActivity.mHistoryFragment.arguments = bundle

        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        timerSetOff()
    }

      // timer


    fun timerSetUp() {
        miliseconds = 300000
        mHandler = Handler()
        mRunnable = object : Runnable {
            override fun run() {
                miliecondsToTime(miliseconds)
                timeTv.text = "Time remaining: $mMinutes:$mSeconds"
                miliseconds -= 1000
                mHandler.postDelayed(this, 1000)
            }
        }
        mHandler.post(mRunnable)
    }

    //timer turn off
    fun timerSetOff() {
        mHandler.removeCallbacks(mRunnable)
        mHandler.removeCallbacksAndMessages(null)
    }
// converting miliseconds for minutes and seconds for text view


    fun miliecondsToTime(miliseconds: Long) {
        mMinutes = (miliseconds.toInt() / 60000).toString()
        mSeconds = ((miliseconds % 60000) / 1000).toInt().toString()

    // adding "0"
        if (mSeconds.length < 2) {
            mSeconds = "0$mSeconds"
        }
    // adding "0"
    if (mMinutes.length < 2) {
        mMinutes = "0$mMinutes"
    }

    }

    // initializing clicklisteners

    @SuppressLint("SetTextI18n")
    fun initClick() {
        saveButton.setOnClickListener {
            mGameModel.createRecordToArchive(
                mGameModel.getGameDate(),
                mGameModel.mTries,
                generatedNumber.toString()
            )
            putInBundle()
        }
        resignButton.setOnClickListener {
            timerSetOff()
            buttonsDesable()
            notificationTV.text =
                "You lost! The Number is " + generatedNumber[0] + generatedNumber[1] + generatedNumber[2] + generatedNumber[3]
            buttonsEnable()
        }

        checkButton.setOnClickListener {

            //check lenght
            if (myNumber.text.toString().trim().length < 4) {
                buttonsDesable()
                notificationTV.text = "Text your 4-digit number"
                buttonsEnable()
            } else {
                //sending to model fun input and getting it recieving it in array of numbers
                notificationTV.text = ""
                mGameModel.gettingUserNumber(myNumber)
                //check for number of digits
                if (!mGameModel.checkInput()) {
                    buttonsDesable()
                    notificationTV.text = "Number must contain unique digits "
                    buttonsEnable()
                        //check for tries number
                } else if (!mGameModel.checkTries()) {
                    buttonsDesable()
                    notificationTV.text =
                        "You lost! The Number is " + generatedNumber[0] + generatedNumber[1] + generatedNumber[2] + generatedNumber[3]
                } else {

                    // here the real work happens
                    mGameModel.checkNumber(mGameModel.mGeneratedNumber, mGameModel.mUserNumbers)
                    gameInfo.text = mGameModel.mOutput
                    checkWin()
                    myNumber.text = ""
                }
            }
            newGameButton.setOnClickListener {
                newGame()
            }
        }
    }
//setting new game
    private fun newGame() {
        timerSetOff()
        timerSetUp()
        buttonsEnable()
        gameInfo.text = ""
        notificationTV.text = ""
    //calling new game fun from model class
        mGameModel.newGame()
        myNumber.text = ""
    }
   //chech number and disabling buttons
    private fun checkWin() {
        if (mGameModel.checkWinGame()) {
            buttonsDesable()
            notificationTV.text = "You won in ${mGameModel.mTries} tries"
        }
    }
// disable buttons fun
    private fun buttonsDesable() {
        resignButton.isEnabled = false
        checkButton.isEnabled = false
    }
//enable buttons fun
    private fun buttonsEnable() {
        resignButton.isEnabled = true
        checkButton.isEnabled = true
    }
//put in bundle
    private fun putInBundle() {
        var bundle = Bundle()
        bundle.putSerializable(Util.GAMEARCHIVE_KEY, mGameModel.mGameHistory)
        mActivity.mHistoryFragment.arguments = bundle
    }



    override fun run() {
        TODO("Not yet implemented")
    }


}


