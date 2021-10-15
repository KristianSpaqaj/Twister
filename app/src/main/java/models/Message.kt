package models

data class Message(val id: Int, val content: String, val user: String, val totalComments: Int) {
    constructor(content: String,user: String,totalComments: Int) : this(-1,content,user,totalComments)

    override fun toString(): String {
        return "$id $user $totalComments"
    }

}