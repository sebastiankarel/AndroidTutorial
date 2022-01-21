package de.sebastiankarel.tutorialapplication.view

import androidx.recyclerview.widget.DiffUtil
import de.sebastiankarel.tutorialapplication.model.User

class UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
}