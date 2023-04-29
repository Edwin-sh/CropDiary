package com.example.cropdiary.view.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cropdiary.R
import com.example.cropdiary.controller.UserController
import com.example.cropdiary.databinding.FragmentRecoveryPasswordBinding
import com.example.cropdiary.util.utilities

class RecoveryPasswordFragment : Fragment() {
    private lateinit var binding: FragmentRecoveryPasswordBinding
    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRecoveryPasswordBinding.inflate(layoutInflater)
        userController = UserController(requireContext())
        //Setup
        setup()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }


    private fun setup() {
        with(binding) {
            btnRecPassword.setOnClickListener {
                recoveryPassword()
            }
        }
    }

    private fun recoveryPassword() {
        with(binding) {
            if (!utilities.noEmpty(
                    Pair(
                        editTextEmailAddress,
                        getString(R.string.you_must_enter_the_email)
                    ), requireContext()
                )
            ) {
                return@with
            }

            if (!utilities.isValid(editTextEmailAddress, requireContext())) {
                return@with
            }

            if (!utilities.networkConnection(requireContext())) {
                return@with
            }
            userController.recoveryPassword(editTextEmailAddress.text.toString()) {
                if (it) {
                    utilities.showSuccesAlert(
                        requireContext(),
                        getString(R.string.we_have_sent_an_email_to_reset_your_password)
                    ) {
                        requireFragmentManager().popBackStack()
                    }

                }
            }
        }
    }
}