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
import androidx.navigation.fragment.findNavController
import com.example.twister.databinding.FragmentSecondBinding
import com.google.gson.Gson
import models.GenericAdapter
import models.Message
import viewmodel.AuthAppViewModel
import viewmodel.MessageViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val authappViewModel: AuthAppViewModel by activityViewModels()
    private val messageViewModel: MessageViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageViewModel.messagesLiveData.observe(viewLifecycleOwner){
            message -> binding.recyclerView.adapter = GenericAdapter<Message>(message){
                val action = SecondFragmentDirections.actionSecondFragmentToComment(it)
                findNavController().navigate(action)
            }
        }
        messageViewModel.reload()
        //binding.fab.setOnClickListener{
        //    findNavController().navigate(R.id.action_SecondFragment_to_postMessage)
        //}
        binding.fab.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val input = EditText(requireContext())
            builder.setView(input)
            builder.setMessage("Add a message").setPositiveButton("Add",DialogInterface.OnClickListener{ dialog, id ->
                val addMessage = Message(input.text.toString(), authappViewModel.userLiveData.value?.email.toString(),0)
                messageViewModel.add(addMessage)
            }).setNegativeButton("Cancel", DialogInterface.OnClickListener {dialog, id ->
                dialog.cancel()
            })
            builder.show()
        }

        binding.swiperefresh.setOnRefreshListener{
            messageViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}