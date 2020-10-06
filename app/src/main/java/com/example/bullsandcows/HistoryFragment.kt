package com.example.bullsandcows

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type

class HistoryFragment : Fragment() {

    var adapter: MyAdapter? = null
    private lateinit var mActivity: MainActivity
     var historyRecord = ArrayList<GameModel>()
    private lateinit var  rcView: RecyclerView


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mActivity = activity as MainActivity
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        loadData()
         rcView = view.findViewById(R.id.rcView)

        var list = fillArray()


        rcView.hasFixedSize()
        rcView.layoutManager = LinearLayoutManager(mActivity.mHistoryFragment.context)
        adapter = MyAdapter(list, mActivity)
        rcView.adapter = adapter







        return view
    }

    private fun loadData(): ArrayList<GameModel> {
        val sharedPreferences: SharedPreferences =
            mActivity.getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("task list", null)
        val type: Type = object : TypeToken<ArrayList<GameModel?>?>() {}.type
        historyRecord = gson.fromJson(json, type)
        if (historyRecord == null) {
            historyRecord = ArrayList()
            return historyRecord
        }
        return historyRecord
    }


     fun fillArray(): ArrayList<HistoryItem>{
         var list = historyRecord
         var itemList = ArrayList<HistoryItem>()
         for(n in 0..list.size-1){
             var item = HistoryItem("Date: ${list[n].date}","Tries left: ${(10 - list[n].mTries)}","Bulls: ${list[n].mBullCounter.toString()}","Cows: ${list[n].mCowCounter.toString()}",list[n].mOutput)
              itemList.add(item)
         }
      return itemList

     }








}