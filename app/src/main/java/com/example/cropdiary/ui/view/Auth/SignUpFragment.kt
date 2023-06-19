package com.example.cropdiary.ui.view.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cropdiary.R
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.core.view.NavigationAuthHelper
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.data.model.FirebaseUserModel
import com.example.cropdiary.databinding.FragmentSignUpBinding
import com.example.cropdiary.ui.viewmodel.AuthViewModel
import com.example.cropdiary.ui.viewmodel.UserViewModel
import com.example.cropdiary.core.view.dialogs
import com.example.cropdiary.core.util.utilities
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignUpBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setup()
        return binding.root
    }

    private fun setup() {
        with(binding) {
            btnCancelSignUp.setOnClickListener { fragmentManager?.popBackStack() }
            btnSignUp.setOnClickListener { signUp() }
        }
        authViewModel.authSignUpModel.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.isSuccess) {
                    it.getOrNull()?.email?.let { it1 ->
                        SharedPrefUserHelper.addUserPrefs(
                            requireContext(),
                            it1,
                            ProviderType.BASIC,
                            false
                        )
                        NavigationAuthHelper.showRegisterActivity(requireActivity())
                    }
                } else if (it.isFailure) {
                    it.exceptionOrNull()?.message?.toInt()?.let { it1 -> getString(it1) }
                        ?.let { it2 ->
                            dialogs.showErrorAlert(
                                requireActivity(),
                                it2
                            )
                        }
                }
            }
        }
    }

    private fun signUp() {
        with(binding) {
            val list = mutableListOf<Pair<EditText, String>>()
            list.add(Pair(edTxEmailAddresSignUp, getString(R.string.you_must_enter_the_email)))
            list.add(Pair(edTxPasswordSignUp, getString(R.string.you_must_enter_the_password)))

            if (!utilities.noEmpty(list, requireActivity())) {
                return@with
            }
            if (!utilities.isValid(
                    edTxEmailAddresSignUp,
                    edTxPasswordSignUp,
                    requireActivity()
                )
            ) {
                return@with
            }
            if (edTxPasswordSignUp.text.toString() != edTxRepeatPasswordSignUp.text.toString()) {

                return@with
            }
            if (!utilities.networkConnection(requireActivity())) {
                return@with
            }

            authViewModel.signUpWithEmail(
                FirebaseUserModel(
                    edTxEmailAddresSignUp.text.toString(),
                    edTxPasswordSignUp.text.toString()
                )
            )
        }
    }
}