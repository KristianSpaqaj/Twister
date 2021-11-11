package com.example.twister

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    val comment: MutableLiveData<Comment> = MutableLiveData<Comment>()
    private val args: commentArgs by navArgs()


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

        val x = commentArgs.fromBundle(requireArguments())
        message.value = x.message

        message.observe(viewLifecycleOwner,{
            binding.messageText.text = message.value?.content
            messageViewModel.getComments(args.message.id)
        })
        binding.buttonDelete.setOnClickListener{
            val id : Int = message.value?.id ?: return@setOnClickListener
            if (message.value?.user == authappViewModel.userLiveData.value?.email){
                messageViewModel.delete(id)
                findNavController().popBackStack()
            } else {
                return@setOnClickListener
            }

        }
        messageViewModel.commentsLiveData.observe(viewLifecycleOwner,{
            binding.recyclerComment.adapter = commentAdapter<Comment>(it){
                val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())

                builder.setMessage("Delete Comment?").setPositiveButton("Yes", DialogInterface.OnClickListener{ dialog, id ->
                    messageViewModel.deleteComments(message.value?.id!!, it.id)
                }).setNegativeButton("Cancel", DialogInterface.OnClickListener {dialog, id ->
                    dialog.cancel()
                })
                if(authappViewModel.userLiveData.value?.email == it.user) {
                    builder.show()
                }
            }
        })
        binding.buttonComment.setOnClickListener{
            val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val input = EditText(requireContext())
            builder.setView(input)
            builder.setMessage("Add a comment").setPositiveButton("Comment", DialogInterface.OnClickListener{dialog, id ->
                val addComment = Comment(1,message.value?.id!!,input.text.toString(),authappViewModel.userLiveData.value?.email.toString())
                if (addComment.content.isNullOrEmpty()){
                    input.error = "Du skal udfylde noget"
                } else {
                    messageViewModel.addComment(message.value?.id!!,addComment)
                }
            }).setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, id ->
                dialog.cancel()
            })
            builder.show()
        }
        binding.swiperefresh.setOnRefreshListener{
            messageViewModel.getComments(message.value?.id!!)
            binding.swiperefresh.isRefreshing = false
        }


    }

}