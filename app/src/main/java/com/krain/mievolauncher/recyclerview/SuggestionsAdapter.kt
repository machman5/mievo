package com.krain.mievolauncher.recyclerview

import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.krain.mievolauncher.R
import com.krain.mievolauncher.room.App
import java.util.concurrent.atomic.AtomicReferenceArray

class SuggestionsAdapter : RecyclerView.Adapter<SuggestionViewHolder>() {

    lateinit var suggestions: AtomicReferenceArray<App>

    override fun getItemCount(): Int = suggestions.length()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder =
        SuggestionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.suggestion,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val view = holder.itemView.findViewById<TextView>(R.id.suggestion)
        view.text = suggestions[position].name
        try {
            val intent: Intent? = holder.itemView.context.packageManager
                .getLaunchIntentForPackage(suggestions[position].pkg)
                ?.addCategory(Intent.CATEGORY_LAUNCHER)
                ?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            view.setOnClickListener {
                holder.itemView.context.startActivity(intent)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("LAUNCH_ERROR", "Could not find package ${suggestions[position].pkg}")
        }
    }
}