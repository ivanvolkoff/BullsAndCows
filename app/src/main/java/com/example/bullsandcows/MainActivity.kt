package com.example.bullsandcows

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var mGameFragment: GameFragment = GameFragment()
    var mHistoryFragment: HistoryFragment = HistoryFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(mGameFragment)


    }
    fun setFragment(fragment: Fragment){
        val fm: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        ft.replace(R.id.mainFrame,fragment,Util.FRAGMENT_KEY)
        ft.commit()
    }
}