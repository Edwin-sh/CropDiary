package com.example.cropdiary.ui.view.Auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cropdiary.R
import com.example.cropdiary.databinding.FragmentRecoveryPasswordBinding
import com.example.cropdiary.ui.viewmodel.AuthViewModel
import com.example.cropdiary.util.Utilities

class RecoveryPasswordFragment : Fragment() {
    private lateinit var binding: FragmentRecoveryPasswordBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRecoveryPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Setup
        setup()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setup() {
        with(binding) {
            btnRecPassword.setOnClickListener {
                recoveryPassword()
            }
        }
        authViewModel.authResultModel.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.isSuccess) {
                    Utilities.showSuccesAlert(
                        requireContext(),
                        getString(R.string.we_have_sent_an_email_to_reset_your_password),
                        ::regress
                    )
                } else if (it.isFailure) {
                    it.exceptionOrNull()?.message?.toInt()?.let { it1 -> getString(it1) }
                        ?.let { it2 ->
                            Utilities.showErrorAlert(
                                requireActivity(),
                                it2
                            )
                        }
                }
            }
        }
    }

    private fun regress(context: Context) {
        requireFragmentManager().popBackStack()
    }

    private fun recoveryPassword() {
        with(binding) {
            if (!Utilities.noEmpty(
                    Pair(
                        editTextEmailAddress,
                        getString(R.string.you_must_enter_the_email)
                    ), requireActivity()
                )
            ) {
                return@with
            }

            if (!Utilities.isValid(editTextEmailAddress, requireActivity())) {
                return@with
            }

            if (!Utilities.networkConnection(requireActivity())) {
                return@with
            }

            authViewModel.recoveryPassword(editTextEmailAddress.text.toString())
        }
    }
}