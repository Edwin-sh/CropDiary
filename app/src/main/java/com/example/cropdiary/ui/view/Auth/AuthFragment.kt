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
import com.example.cropdiary.core.FirebaseHelper
import com.example.cropdiary.core.FragmentsConstants
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.core.auth.GoogleServerClientIdProvider
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.databinding.FragmentAuthBinding
import com.example.cropdiary.ui.view.main.*
import com.example.cropdiary.ui.view.user.RegistreUserActivity
import com.example.cropdiary.ui.viewmodel.AuthViewModel
import com.example.cropdiary.ui.viewmodel.UserViewModel
import com.example.cropdiary.util.Utilities
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    @Inject
    lateinit var googleServerClientIdProvider: GoogleServerClientIdProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAuthBinding.inflate(layoutInflater)
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
            btnSignUp.setOnClickListener { signUp() }
            btnSignIn.setOnClickListener { signIn() }
            btnSignInWithGoogle.setOnClickListener { signInWithGoogle() }
            tvForgotPassword.setOnClickListener { showForgotPassword() }

        }
        authViewModel.authSignInModel.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                it.getOrNull()?.email?.let { it1 ->
                    SharedPrefUserHelper.addUserPrefs(requireContext(), it1, null, null)
                    userViewModel.getUser(it1)
                }
            } else if (it.isFailure) {
                it.exceptionOrNull()?.message?.toInt()?.let { it1 -> getString(it1) }
                    ?.let { it2 ->
                        Utilities.showErrorAlert(
                            requireActivity(),
                            it2
                        )
                    }
                SharedPrefUserHelper.clearPrefs(requireContext())
            }
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
                        showRegisterActivity()
                    }
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
        userViewModel.userModel.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                if (it.getOrNull() != null) {
                    SharedPrefUserHelper.addUserPrefs(requireContext(), null, null, true)
                } else {
                    SharedPrefUserHelper.addUserPrefs(requireContext(), null, null, false)
                }
                showNextActivity()
            }
        }
    }

    private fun signUp() {
        with(binding) {
            val list = mutableListOf<Pair<EditText, String>>()
            list.add(Pair(editTextEmailAddress, getString(R.string.you_must_enter_the_email)))
            list.add(Pair(editTextPassword, getString(R.string.you_must_enter_the_password)))

            if (!Utilities.noEmpty(list, requireActivity())) {
                return@with
            }
            if (!Utilities.isValid(
                    editTextEmailAddress,
                    binding.editTextPassword,
                    requireActivity()
                )
            ) {
                return@with
            }
            if (!Utilities.networkConnection(requireActivity())) {
                return@with
            }

            authViewModel.signUpWithEmail(
                UserModel(
                    editTextEmailAddress.text.toString(),
                    editTextPassword.text.toString()
                )
            )
        }
    }

    private fun signIn() {
        with(binding) {
            val list = mutableListOf<Pair<EditText, String>>()
            list.add(Pair(editTextEmailAddress, getString(R.string.you_must_enter_the_email)))
            list.add(Pair(editTextPassword, getString(R.string.you_must_enter_the_password)))

            if (!Utilities.noEmpty(list, requireActivity())) {
                return@with
            }

            if (!Utilities.isValid(editTextEmailAddress, requireActivity())) {
                return@with
            }

            if (!Utilities.networkConnection(requireActivity())) {
                return@with
            }
            SharedPrefUserHelper.addUserPrefs(requireContext(), null, ProviderType.BASIC, null)
            authViewModel.signInWithEmail(
                UserModel(
                    editTextEmailAddress.text.toString(),
                    editTextPassword.text.toString()
                )
            )
        }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(googleServerClientIdProvider.getGoogleServerClientId())
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(requireActivity(), gso)
        startActivityForResult(googleClient.signInIntent, 9001)
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
                // Google Sign In failed, update UI appropriately
                Log.w("error", "Google sign in failed", e)
            }
        }
    }

    private fun showNextActivity() {
        if (SharedPrefUserHelper.getUserPrefs(requireContext()).isRegistered) {
            requireActivity().startActivity(
                Intent(requireContext(), MainActivity::class.java)
            )
        } else {
            requireActivity().startActivity(
                Intent(requireContext(), RegistreUserActivity::class.java)
            )
        }
        requireActivity().finish()
    }

    private fun showRegisterActivity() {

        requireActivity().startActivity(
            Intent(requireContext(), RegistreUserActivity::class.java)
        )
        requireActivity().finish()
    }

    private fun showForgotPassword() {
        requireFragmentManager().beginTransaction()
            .replace(R.id.fragContainerAuth, RecoveryPasswordFragment())
            .addToBackStack(FragmentsConstants.FRAGMENT_AUTH)
            .commit()
    }
}