package com.example.bullsandcows

import android.content.Intent
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.header_main_menu.*


class HistoryFragment : Fragment() {
    private lateinit var mActivity: MainActivity
    private lateinit var historyRecord : ArrayList<GameArchive>
    private lateinit var fieldForRecord : TextView


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mActivity = activity as MainActivity
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        fieldForRecord = view.findViewById(R.id.mHistoryList)
        var output= ""



        if (arguments?.getSerializable(Util.GAMEARCHIVE_KEY)!=null){
            historyRecord = arguments?.getSerializable("gameArchive") as ArrayList<GameArchive>

        }
        for (i in historyRecord.indices){
            output += "${historyRecord[i].gameName}                 ${historyRecord[i].tries}                       ${historyRecord[i].number} \n"

        }
        fieldForRecord.text = output
        return view
    }





}