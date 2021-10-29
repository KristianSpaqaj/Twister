package com.example.twister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.twister.databinding.FragmentCommentBinding
import com.google.gson.Gson
import models.Comment
import models.Message
import models.commentAdapter
import viewmodel.AuthAppViewModel
import viewmodel.MessageViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [comment.newInstance] factory method to
 * create an instance of this fragment.
 */
class comment : Fragment() {
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    val messageViewModel: MessageViewModel by activityViewModels()
    val authappViewModel: AuthAppViewModel by activityViewModels()
    val message: MutableLiveData<Message> = MutableLiveData<Message>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommentBinding.inflate(layoutInflater,container,false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments == null){

        } else {
            val gson = Gson()
            message.postValue(gson.fromJson(arguments?.getString("message"),Message::class.java))
        }

        message.observe(viewLifecycleOwner,{
            binding.messageText.text = message.value?.content

        })
        binding.buttonDelete.setOnClickListener{
            val id : Int = message.value?.id ?: return@setOnClickListener
            if (message.value?.user == authappViewModel.userLiveData.value?.email){
                messageViewModel.delete(id)
                findNavController().popBackStack()
            } else {

            }

        }
        messageViewModel.getComments(837)
        messageViewModel.commentsLiveData.observe(viewLifecycleOwner,{
            binding.recyclerComment.adapter = commentAdapter<Comment>(it){

            }
        })
    }

}