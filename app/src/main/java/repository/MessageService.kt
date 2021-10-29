package repository


import models.Comment
import models.Message
import retrofit2.Call
import retrofit2.http.*

interface MessageService {
    @GET("messages")
    fun getAllMessages(): Call<List<Message>>

    @GET("messages/{messageId}/comments")
    fun getComments(@Path("messageId") messageId: Int): Call<List<Comment>>

    @POST("messages")
    fun postMessage(@Body message: Message): Call<Message>

    @POST("messages/{messageId}/comments")
    fun postComment(@Path("messageId") messageId: Int,@Body comment: Comment): Call<Comment>

    @DELETE("messages/{id}")
    fun deleteMessage(@Path("id") id : Int): Call<Message>

    @DELETE("messages/{messageId}/comments/{commentId}")
    fun deleteComment(@Path("messageId") messageId: Int,@Path("commentId") commentId: Int ): Call<Comment>
}