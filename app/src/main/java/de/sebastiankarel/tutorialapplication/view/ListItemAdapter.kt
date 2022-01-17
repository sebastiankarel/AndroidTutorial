package de.sebastiankarel.tutorialapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.databinding.ItemsListBinding
import de.sebastiankarel.tutorialapplication.model.User

class ListItemAdapter(private val onClick: (id: String) -> Unit) : RecyclerView.Adapter<ListItemAdapter.ListItemViewHolder>() {

    var items: List<User> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemsListBinding = ItemsListBinding.inflate(inflater, parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.binding.title = items[position].name
        holder.binding.text = items[position].email
        holder.binding.img.load(items[position].thumb) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
        holder.binding.root.setOnClickListener {
            onClick(items[position].name)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ListItemViewHolder(val binding: ItemsListBinding) : RecyclerView.ViewHolder(binding.root)
}