package com.example.bullsandcows

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener  {

    var mGameFragment: GameFragment = GameFragment()
    var mHistoryFragment: HistoryFragment = HistoryFragment()
    var mRuleFragment: RuleFragment= RuleFragment()
    var mGameModel:GameModel = GameModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(mGameFragment)
        nav_view.setNavigationItemSelectedListener(this)


    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean {
        when (item.itemId) {
            R.id.new_game_pressed -> {
                mGameFragment.newGame()
                setFragment(mGameFragment)
            }
            R.id.history_pressed-> {
               setFragment(mHistoryFragment)

            }
            R.id.rule_pressed->{
                setFragment(mRuleFragment)
            }
        }
        drawerLayout.closeDrawer(Gravity.LEFT)
        return true
    }
    fun setFragment(fragment: Fragment) {
        val fm: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        ft.replace(R.id.mainFrame, fragment, Util.FRAGMENT_KEY)
        ft.commit()
    }
}




