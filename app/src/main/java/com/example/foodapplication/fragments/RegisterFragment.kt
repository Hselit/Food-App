package com.example.foodapplication.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.foodapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class RegisterFragment : Fragment() {

    private lateinit var googlesignin: ImageView
    private lateinit var remail: EditText
    private lateinit var rpass: EditText
    private lateinit var rusername: EditText
    private lateinit var gotolog: Button
    private lateinit var register: Button
    private lateinit var tandc: CheckBox
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googlesignin = view.findViewById(R.id.imageView2)
        googlesignin.setOnClickListener {
            signInWithGoogle()
        }

        gotolog = view.findViewById(R.id.loginbtn)
        gotolog.setOnClickListener {
            gotolog()
        }

        remail = view.findViewById(R.id.emailaddress)
        remail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!isValidEmail(remail.text.toString())) {
                    remail.setBackgroundResource(R.drawable.error_background)
                } else {
                    remail.setBackgroundResource(R.drawable.rect)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        rusername = view.findViewById(R.id.username)
        rusername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val str = validateUsername(rusername.text.toString())
                if (str != null) {
                    rusername.setBackgroundResource(R.drawable.error_background)
                } else {
                    rusername.setBackgroundResource(R.drawable.rect)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        rpass = view.findViewById(R.id.password)
        rpass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val msg = validatePassword(rpass.text.toString())
                if (msg != null) {
                    rpass.setBackgroundResource(R.drawable.error_background)
                } else {
                    rpass.setBackgroundResource(R.drawable.rect)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        var isPasswordVisible = false
        rpass.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (rpass.right - rpass.compoundDrawables[2].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    if (isPasswordVisible) {
                        rpass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        rpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.key_open, 0)
                    } else {
                        rpass.transformationMethod = PasswordTransformationMethod.getInstance()
                        rpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.key_close, 0)
                    }
                    rpass.setSelection(rpass.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }

        tandc = view.findViewById(R.id.checkBox)
        register = view.findViewById(R.id.registerbtn)
        register.setOnClickListener {
            checkregister(remail, rpass, rusername, tandc)
        }

        return view
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Google sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(requireContext(), "Google Sign-In successful! Welcome ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    gotolog()
                } else {
                    Toast.makeText(requireContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkregister(eemail: EditText, epass: EditText, euser: EditText, ck: CheckBox) {
        val email = eemail.text.toString()
        val pass = epass.text.toString()
        val username = euser.text.toString()
        if (email.isEmpty()) {
            Toast.makeText(context, "Email is Blank", Toast.LENGTH_SHORT).show()
            eemail.requestFocus()
        }
        if (pass.isEmpty()) {
            Toast.makeText(context, "Password is Blank", Toast.LENGTH_SHORT).show()
            epass.requestFocus()
        }
        if (username.isEmpty()) {
            Toast.makeText(context, "Username is Blank", Toast.LENGTH_SHORT).show()
            euser.requestFocus()
        }
        if (validateUsername(username) != null) {
            Toast.makeText(context, "Username should be in the range of 0-12 letters", Toast.LENGTH_SHORT).show()
        }
        if (!isValidEmail(email)) {
            Toast.makeText(context, "Invalid Email..", Toast.LENGTH_SHORT).show()
        } else {
            val passwordError = validatePassword(pass)
            if (passwordError != null) {
                Toast.makeText(context, passwordError, Toast.LENGTH_SHORT).show()
            } else {
                if (ck.isChecked) {
                    registerinbase(email, pass, username)
                } else {
                    Toast.makeText(context, "Please Accept the Terms and Conditions...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun registerinbase(email: String, pass: String, username: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isComplete) {
                    Toast.makeText(requireContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show()
                    gotolog()
                } else {
                    Toast.makeText(requireContext(), "Registration Failed...", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun gotolog() {
        parentFragmentManager.popBackStack()
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return if (target.isEmpty()) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun validateUsername(username: String): String? {
        return when {
            username.length > 12 -> "Username should not be more than 12 letters"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.length <= 8 -> "Password must contain more than 8 digits"
            !password.contains(Regex("[A-Z]")) -> "Password must contain one capital letter"
            !password.contains(Regex("[0-9]")) -> "Password must contain one digit"
            !password.contains(Regex("[^a-zA-Z0-9 ]")) -> "Password must contain one special character"
            else -> null
        }
    }
}
