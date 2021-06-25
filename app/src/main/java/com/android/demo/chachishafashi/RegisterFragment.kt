package com.android.demo.chachishafashi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.demo.chachishafashi.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {

    lateinit var bindigng2: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    lateinit var signUp: Button
    lateinit var email: EditText
    lateinit var pword: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindigng2 = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()


//        if (pword.text.toString().isEmpty()){
//            pword.error = "Please Enter a password"
//        }
//        if (email.text.toString().isEmpty()){
//            email.error = "Email cannot be empty"
//        }
         email = bindigng2.SEmmmail
         pword = bindigng2.Spasss
        signUp = bindigng2.Signup
        signUp.setOnClickListener {
            Login()
        }
//        auth.createUserWithEmailAndPassword(email.text.toString(), pword.text.toString())
//                .addOnCompleteListener{task ->
//                    if (task.isSuccessful) {
//                        signUp.setOnClickListener {
//                        view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment3)
//                    }
//                    }else{
//                        Toast.makeText(context, "SignUpfailed", Toast.LENGTH_SHORT).show()
//                    }
//                }

//        createAccount(email.text.toString(), pword.text.toString())
        return bindigng2.root
    }

    fun Login(){
        if (pword.text.toString().isEmpty()){
            pword.error = "Please Enter a password"
        }
        if (email.text.toString().isEmpty()){
            email.error = "Email cannot be empty"
        }
                auth.createUserWithEmailAndPassword(email.text.toString(), pword.text.toString())
                .addOnCompleteListener{task ->
                    if (task.isSuccessful) {
                        view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment3)

                    }else{
                        Toast.makeText(context, "SignUpfailed", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val RC_MULTI_FACTOR = 9005
    }
}
