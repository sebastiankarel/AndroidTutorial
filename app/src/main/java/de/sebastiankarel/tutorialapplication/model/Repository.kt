package de.sebastiankarel.tutorialapplication.model

class Repository {

    fun getItems(): List<ListItem> {
        val result = mutableListOf<ListItem>()
        for (i in 0..100) {
            result.add(ListItem(i, "Title $i", "Text $i"))
        }
        return result
    }
}