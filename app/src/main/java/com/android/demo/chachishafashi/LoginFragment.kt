package com.android.demo.chachishafashi

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.demo.chachishafashi.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


 class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
    get() = _binding!!


    lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        loginButton = binding.login
        loginButton?.setOnClickListener { signToNextScreen() }
        loginButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_loginFragment3_to_productFragment)
        }

        val registerButton = binding.RegisterBtn
        registerButton.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_loginFragment3_to_registerFragment)
        }
       return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    fun createAccount(email: String, password: String){
        Log.d(TAG, "Creating a new account")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Log.d(TAG, "signInWithEmail success")
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                   updateUI(null)
                }
            }
    }

  fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Log.d(TAG, "signInWithEmail successful")
                    Toast.makeText(context, "signIn successful", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)

                }else{
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    fun validateForm(): Boolean{
        var valid = true

        val email = binding.outlinedTextEmail.toString()
        if (TextUtils.isEmpty(email)){
            binding.outlinedTextEmail.error = "Required"
            valid = false
        }else{
            binding.outlinedTextEmail.error = null
        }

        val password = binding.outlinedTextPassword.toString()
        if (TextUtils.isEmpty(password)){
            binding.outlinedTextPassword.error = "Required"
            valid = false
        }else{
            binding.outlinedTextPassword.error = null
        }
        return valid
    }

     fun updateUI(user: FirebaseUser?) {
        if (user != null) {

            if (user.isEmailVerified) {

            } else {

            }
        } else {

        }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener{task ->
            if (task.isSuccessful){
                updateUI(auth.currentUser)
                Toast.makeText(context,
                    "Reload successful!",
                    Toast.LENGTH_SHORT).show()
            }else{
                Log.e(TAG, "reload", task.exception)
                Toast.makeText(context,
                    "Failed to reload user.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signToNextScreen(){
            val email = binding.outlinedTextEmail.editText?.text.toString()
            val password = binding.outlinedTextPassword.editText?.text.toString()
            signIn(email, password)
//        val nextScreen = {view: View ->
//            view.run { findNavController().navigate(R.id.action_loginFragment3_to_productFragment) }
//        }
//        loginButton.setOnClickListener(nextScreen)
    }


    companion object {
        private const val TAG = "EmailPassword"
        private const val RC_MULTI_FACTOR = 9005
    }
}