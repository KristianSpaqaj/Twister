package models

data class Comment(val id: Int,val messageId: Int, val content: String, val user: String) {
   // constructor(content: String,user: String) : this(-1,-1,content,user)

    override fun toString(): String {
        return "$user : $content"
    }
}