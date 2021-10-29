package repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import models.Comment
import models.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessageRepository {
    private val url = "https://anbo-restmessages.azurewebsites.net/api/"
    private val messageService: MessageService
    val messagesLiveData: MutableLiveData<List<Message>> = MutableLiveData<List<Message>>()
    val commentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData<List<Comment>>()
    val errorMessage: MutableLiveData<String> = MutableLiveData<String>()
    val updateMessage: MutableLiveData<String> = MutableLiveData<String>()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        messageService = build.create(MessageService::class.java)
        getPosts()
    }

    fun getPosts(){
        messageService.getAllMessages().enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) {
                    messagesLiveData.postValue(response.body())
                    errorMessage.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.postValue(message)
                    Log.d("KAGE", message)
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.d("KAGE", t.message!!)
            }
        })
    }

    fun getAllComments(messageId: Int){
        messageService.getComments(messageId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful){
                    commentsLiveData.postValue(response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.postValue(message)
                    Log.d("KAGE",message)
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.d("KAGE",t.message!!)
            }

        })
    }

    fun add(message: Message){
        messageService.postMessage(message).enqueue(object : Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful){
                        updateMessage.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.postValue(message)
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun delete(id: Int){
        messageService.deleteMessage(id).enqueue(object : Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful){
                    updateMessage.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.postValue(message)
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }



}