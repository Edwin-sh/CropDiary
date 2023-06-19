package com.example.cropdiary.ui.view.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cropdiary.R
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.core.view.NavigationAuthHelper
import com.example.cropdiary.core.view.ViewVisibilityHelper
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.data.model.FirebaseUserModel
import com.example.cropdiary.databinding.FragmentSignInBinding
import com.example.cropdiary.ui.view.main.*
import com.example.cropdiary.ui.viewmodel.AuthViewModel
import com.example.cropdiary.ui.viewmodel.UserViewModel
import com.example.cropdiary.core.view.dialogs
import com.example.cropdiary.core.util.utilities
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    @Inject
    lateinit var googleSignInOptions: GoogleSignInOptions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignInBinding.inflate(layoutInflater)
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
            btnSign.setOnClickListener { signIn() }
            tvSignUp.setOnClickListener { NavigationAuthHelper.showSignUpFragment(this@SignInFragment) }
            btnSignInWithGoogle.setOnClickListener { signInWithGoogle() }
            tvForgotPassword.setOnClickListener {
                NavigationAuthHelper.showForgotPasswordFragment(
                    this@SignInFragment
                )
            }
            progressBarSignIn.visibility = View.INVISIBLE
        }
        authViewModel.authSignInModel.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                it.getOrNull()?.email?.let { it1 ->
                    SharedPrefUserHelper.addUserPrefs(requireContext(), it1, null, null)
                    userViewModel.isRegistered(it1)
                }
            } else if (it.isFailure) {
                it.exceptionOrNull()?.message?.toInt()?.let { it1 -> getString(it1) }
                    ?.let { it2 ->
                        dialogs.showErrorAlert(
                            requireActivity(),
                            it2
                        )
                    }
                SharedPrefUserHelper.clearPrefs(requireContext())
            }
        }
        authViewModel._progressbar.observe(viewLifecycleOwner) {
            ViewVisibilityHelper.setVisibility(binding.progressBarSignIn, it)
        }

        userViewModel.userResultModel.observe(viewLifecycleOwner) {
            Log.w( "msg",it.getOrNull().toString())
            if (it.isSuccess) {
                if (it.getOrNull() == true) {
                    SharedPrefUserHelper.addUserPrefs(requireContext(), null, null, true)
                } else {
                    SharedPrefUserHelper.addUserPrefs(requireContext(), null, null, false)
                }
                NavigationAuthHelper.showMainOrRegisterActivity(requireActivity())
            }
        }
        userViewModel._progressbar.observe(viewLifecycleOwner) {
            ViewVisibilityHelper.setVisibility(binding.progressBarSignIn, it)
        }
    }

    private fun signIn() {
        with(binding) {
            val list = mutableListOf<Pair<EditText, String>>()
            list.add(Pair(edTxEmailAddressSignIn, getString(R.string.you_must_enter_the_email)))
            list.add(Pair(edTxPasswordSignIn, getString(R.string.you_must_enter_the_password)))

            if (!utilities.noEmpty(list, requireActivity())) {
                return@with
            }

            if (!utilities.isValid(edTxEmailAddressSignIn, requireActivity())) {
                return@with
            }

            if (!utilities.networkConnection(requireActivity())) {
                return@with
            }
            SharedPrefUserHelper.addUserPrefs(requireContext(), null, ProviderType.BASIC, null)
            authViewModel.signInWithEmail(
                FirebaseUserModel(
                    edTxEmailAddressSignIn.text.toString(),
                    edTxPasswordSignIn.text.toString()
                )
            )
        }
    }

    private fun signInWithGoogle() {
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        startActivityForResult(
            googleSignInClient.signInIntent, 9001
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                SharedPrefUserHelper.addUserPrefs(requireContext(), null, ProviderType.GOOGLE, null)
                account.idToken?.let { authViewModel.signInWithGoogle(it) }
            } catch (e: ApiException) {
                // Google Sign In failed
                Log.w("error", "Google sign in failed", e)
            }
        }
    }

}