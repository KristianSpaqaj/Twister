package com.example.twister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.twister.databinding.FragmentPostMessageBinding
import models.Message
import viewmodel.AuthAppViewModel
import viewmodel.MessageViewModel
/**
 * A simple [Fragment] subclass.
 * Use the [postMessage.newInstance] factory method to
 * create an instance of this fragment.
 */
class postMessage : Fragment() {
    val messageViewModel: MessageViewModel by activityViewModels()
    val authappViewModel: AuthAppViewModel by activityViewModels()
    private var _binding: FragmentPostMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostMessageBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_post_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSendMessage.setOnClickListener{
            val message = binding.editTextMessage.text.toString().trim()
            if (message.isNullOrEmpty()){
                binding.editTextMessage.error = "Du skal udfylde noget!"
                return@setOnClickListener
            } else {
                val addMessage = Message(message, authappViewModel.userLiveData.value?.email.toString(),0)
                messageViewModel.add(addMessage)
                findNavController().popBackStack()
            }
        }
    }
}