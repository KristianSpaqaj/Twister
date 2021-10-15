package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import models.Message
import repository.MessageRepository

class MessageViewModel: ViewModel() {
    private val repository = MessageRepository()
    val messagesLiveData: MutableLiveData<List<Message>> = repository.messagesLiveData
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

    }
    fun delete(id: Int) {

    }
    fun update(message: Message) {

    }
}