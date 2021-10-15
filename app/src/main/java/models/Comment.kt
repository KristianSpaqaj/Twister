package models

data class Comment(val id: Int,val messageId: Int, val content: String, val user: String) {
    constructor(messageId: Int,content: String,user: String) : this(-1,messageId,content,user)

    override fun toString(): String {
        return "$id $messageId $content $user"
    }
}