package com.example.tasknotes.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasknotes.R
import com.example.tasknotes.data.models.IntroData
import com.example.tasknotes.utils.extensions.bindItem
import com.example.tasknotes.utils.extensions.inflate
import kotlinx.android.synthetic.main.intro_fragment.view.*

class IntroAdapter(private val data: List<IntroData>) :
    RecyclerView.Adapter<IntroAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.intro_fragment))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() = bindItem {
            val d = data[adapterPosition]
            textHeader.text = d.header
            imageIcon.setImageResource(d.image)
            textDescription.text = d.description
        }
    }
}
