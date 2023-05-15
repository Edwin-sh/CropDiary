package com.example.cropdiary.view.Auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.cropdiary.view.main.*
import com.example.cropdiary.R
import com.example.cropdiary.controller.UserController
import com.example.cropdiary.databinding.FragmentAuthBinding
import com.example.cropdiary.model.UserModel
import com.example.cropdiary.util.FragmentsConstants
import com.example.cropdiary.util.ProviderType
import com.example.cropdiary.util.utilities

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private lateinit var userController: UserController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAuthBinding.inflate(layoutInflater)
        userController = UserController(this)
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
            btnSignUp.setOnClickListener { signUp() }
            btnSignIn.setOnClickListener { signIn() }
            btnSignInWithGoogle.setOnClickListener { signInWithGoogle() }
            tvForgotPassword.setOnClickListener { showForgotPassword() }
        }
    }

    private fun signUp() {
        with(binding) {
            val list = mutableListOf<Pair<EditText, String>>()
            list.add(Pair(editTextEmailAddress, getString(R.string.you_must_enter_the_email)))
            list.add(Pair(editTextPassword, getString(R.string.you_must_enter_the_password)))

            if (!utilities.noEmpty(list, requireContext())) {
                return@with
            }
            if (!utilities.isValid(
                    editTextEmailAddress,
                    binding.editTextPassword,
                    requireContext()
                )
            ) {
                return@with
            }
            if (!utilities.networkConnection(requireContext())) {
                return@with
            }

            val userModelAuthResult = userController.signUpUser(
                UserModel(
                    editTextEmailAddress.text.toString(),
                    editTextPassword.text.toString()
                )
            ) { firebaseUser ->
                if (firebaseUser != null) {
                    utilities.showSuccesAlert(requireContext(), "Se registró correctamente") {
                        showPantallaInicial(firebaseUser.email!!, ProviderType.BASIC)
                    }

                }
            }

        }
    }

    private fun signIn() {
        with(binding) {
            val list = mutableListOf<Pair<EditText, String>>()
            list.add(Pair(editTextEmailAddress, getString(R.string.you_must_enter_the_email)))
            list.add(Pair(editTextPassword, getString(R.string.you_must_enter_the_password)))

            if (!utilities.noEmpty(list, requireContext())) {
                return@with
            }

            if (!utilities.isValid(editTextEmailAddress, requireContext())) {
                return@with
            }

            if (!utilities.networkConnection(requireContext())) {
                return@with
            }
            userController.signInUser(
                UserModel(
                    editTextEmailAddress.text.toString(),
                    editTextPassword.text.toString()
                )
            ) {
                if (it != null) {
                    showPantallaInicial(it.email!!, ProviderType.BASIC)
                }
            }
        }
    }

    private fun signInWithGoogle() {
        userController.signInWithGoogle()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        userController.onActivityResult(requestCode, resultCode, data) {
            if (it != null) {
                showPantallaInicial(it.email!!, ProviderType.GOOGLE)
            }
        }

    }

    private fun showPantallaInicial(email: String, provider: ProviderType) {

        requireActivity().startActivity(
            Intent(requireContext(), MainActivity::class.java).apply {
                putExtra("email", email)
                putExtra("provider", provider.name)
            }
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