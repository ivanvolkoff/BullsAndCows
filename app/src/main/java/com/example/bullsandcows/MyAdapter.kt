package com.example.bullsandcows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(historyList: ArrayList<HistoryItem>, context: Context): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var historyListR = historyList
    private var contextR = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(contextR)
        return ViewHolder(inflater.inflate(R.layout.history_item,parent,false))
    }

    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val dateTv = view.findViewById<TextView>(R.id.textView7)
        val triesTV = view.findViewById<TextView>(R.id.triesInGame)
        val bullsTV = view.findViewById<TextView>(R.id.bullsTV)
        val cowsTv = view.findViewById<TextView>(R.id.cowTV)
        val outputTv = view.findViewById<TextView>(R.id.outputTV)

     fun bind(historyItem: HistoryItem) {
         dateTv.text = historyItem.date
         triesTV.text = historyItem.tries
         bullsTV.text = historyItem.bullsCounter
         cowsTv.text = historyItem.cowCounter
         outputTv.text = historyItem.output
     }
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
      val historyItem = historyListR[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int {
        return historyListR.size
    }
}