package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import models.Comment
import models.Message
import repository.MessageRepository

class MessageViewModel: ViewModel() {
    private val repository = MessageRepository()
    val messagesLiveData: MutableLiveData<List<Message>> = repository.messagesLiveData
    val commentsLiveData: MutableLiveData<List<Comment>> = repository.commentsLiveData
    val errorMessage: MutableLiveData<String> = repository.errorMessage
    val updateMessage: MutableLiveData<String> = repository.updateMessage

    init {
        reload()
    }
    fun reload() {
        repository.getPosts()
    }

    operator fun get(index: Int): Message? {
        return messagesLiveData.value?.get(index)
    }
    fun add(message: Message){
        repository.add(message)
    }
    fun delete(id: Int) {
        repository.delete(id)
    }
    fun getComments(messageId: Int){
        repository.getAllComments(messageId)
    }

}