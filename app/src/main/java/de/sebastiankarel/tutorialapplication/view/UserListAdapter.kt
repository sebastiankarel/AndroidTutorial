package de.sebastiankarel.tutorialapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.sebastiankarel.tutorialapplication.databinding.UserItemBinding
import de.sebastiankarel.tutorialapplication.model.User
import de.sebastiankarel.tutorialapplication.util.ImageLoader

class UserListAdapter(
    private val imageLoader: ImageLoader,
    private val onClick: (id: Int) -> Unit
) : ListAdapter<User, UserListAdapter.ListItemViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: UserItemBinding = UserItemBinding.inflate(inflater, parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    inner class ListItemViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onClick: (id: Int) -> Unit) {
            imageLoader.loadImage(binding.img, user.photoId)
            binding.user = user
            binding.root.setOnClickListener { onClick(user.id) }
            binding.executePendingBindings()
        }
    }
}