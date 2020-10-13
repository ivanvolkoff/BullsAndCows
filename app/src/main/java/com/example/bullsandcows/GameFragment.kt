package com.example.bullsandcows


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import java.io.Serializable



class GameFragment : Fragment(), Runnable, Serializable {

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
    private lateinit var triesLeftTV: TextView
    private var mMinutes = ""
    private var mSeconds = ""
    private var miliseconds: Long = 300000
    private lateinit var mGameModel: GameModel
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    var leftedTries = 10

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
        triesLeftTV = view.findViewById(R.id.triesLeftTV)
        mActivity = activity as MainActivity
        mGameModel.mOutput = ""
        leftedTries = 10 - mGameModel.mTries

        initClick()
        generatedNumber = mGameModel.generateRandom()
        // caling timer
        timerSetUp()
        triesLeftTV.text = "10 tries left"


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

    fun initClick() {
        triesLeftTV.text = mGameModel.triesOutput
        saveButton.setOnClickListener {
            mGameModel.date = mGameModel.getGameDate()
            saveData()
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
            }
            else {
                //sending to model fun input and getting it recieving it in array of numbers


                notificationTV.text = ""
                mGameModel.gettingUserNumber(myNumber)
                //check for number of digits

                if (!mGameModel.checkInput()) {
                    buttonsDesable()
                    notificationTV.text = "Number must contain unique digits "
                    buttonsEnable()
                    //check for tries number
                }
                else if (mGameModel.mTries == 10 ) {
                    buttonsDesable()
                }
                else {

                    // here the real work happens
                    mGameModel.checkNumber(mGameModel.mGeneratedNumber, mGameModel.mUserNumbers)
                    gameInfo.text = mGameModel.mOutput
                    triesLeftTV.text = mGameModel.triesOutput
                    notificationTV.text = mGameModel.checkWinGame()
                    myNumber.text = ""
                }

            }

            if(mGameModel.mTries==10){
                notificationTV.text =
                    "You lost! The Number is " + generatedNumber[0] + generatedNumber[1] + generatedNumber[2] + generatedNumber[3]
            }

        }


        newGameButton.setOnClickListener {
            newGame()
        }

    }

    private fun saveData() {
        mActivity.mHistoryFragment.historyRecord.add(mGameModel)


        val sharedPreferences: SharedPreferences = mActivity.getSharedPreferences(Util.GAMESAVES_KEY,
            MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(mActivity.mHistoryFragment.historyRecord)
        editor.putString("task list", json)
        editor.apply()
        Toast.makeText(activity, "Game Saved", Toast.LENGTH_SHORT).show()

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
        triesLeftTV.text = "10 tries left"
    }

    //chech number and disabling buttons


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

    }


}


