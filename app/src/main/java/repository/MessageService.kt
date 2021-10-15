package repository


import models.Message
import retrofit2.Call
import retrofit2.http.GET

interface MessageService {
    @GET("messages")
    fun getAllMessages(): Call<List<Message>>
}