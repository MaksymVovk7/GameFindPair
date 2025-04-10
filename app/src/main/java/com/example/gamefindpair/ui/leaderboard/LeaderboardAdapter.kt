package com.example.gamefindpair.ui.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamefindpair.R
import com.example.gamefindpair.data.model.GameResult

class LeaderboardAdapter(
    private var results: List<GameResult>) : RecyclerView.Adapter<LeaderboardAdapter.ResultViewHolder>() {

    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeText: TextView = view.findViewById(R.id.timeText)
        val modeText: TextView = view.findViewById(R.id.modeText)
        val dateText: TextView = view.findViewById(R.id.dateText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return ResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = results[position]
        holder.timeText.text = "${(result.timeMillis / 1000)} с"
        holder.modeText.text = "${result.mode} карток"
        holder.dateText.text = "${result.date}"
    }

    fun updateData(newResults: List<GameResult>) {
        results = newResults
        notifyDataSetChanged()
    }
}