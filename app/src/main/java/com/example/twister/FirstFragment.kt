package com.example.twister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.twister.databinding.FragmentFirstBinding
import viewmodel.AuthAppViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: AuthAppViewModel by activityViewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorMessage.observe(this,{message ->
            binding.messageView.text = message
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.buttonFirst.setOnClickListener {
          //  findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //}
        binding.buttonCreateUser.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isNullOrEmpty()){
                binding.emailInputField.error = "Enter a valid email"
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()){
                binding.passwordInputField.error = "Enter a valid password"
                return@setOnClickListener
            }
            viewModel.register(email, password)


        }

        binding.buttonSignIn.setOnClickListener{
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isNullOrEmpty()){
                binding.emailInputField.error = "Enter a valid email"
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()){
                binding.passwordInputField.error = "Enter a valid password"
                return@setOnClickListener
            }
            viewModel.signIn(email, password)
            if (viewModel.loggedOutData.value == false){
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        //viewModel.userLiveData.observe(viewLifecycleOwner){user ->
            //if (user != null){
              //  findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            //}

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}