    package com.example.foodapplication.activity

    import android.Manifest
    import android.annotation.SuppressLint
    import android.content.Context
    import android.content.Intent
    import android.content.SharedPreferences
    import android.content.pm.PackageManager
    import android.os.Bundle
    import android.text.Editable
    import android.text.TextWatcher
    import android.text.method.HideReturnsTransformationMethod
    import android.text.method.PasswordTransformationMethod
    import android.view.MotionEvent
    import android.widget.Button
    import android.widget.EditText
    import android.widget.TextView
    import android.widget.Toast
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.app.ActivityCompat
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import com.example.foodapplication.R
    import com.example.foodapplication.fragments.ForgotPass
    import com.example.foodapplication.fragments.RegisterFragment
    import com.google.firebase.auth.FirebaseAuth

    class LoginActivity : AppCompatActivity() {

        private lateinit var etemail:EditText
        private lateinit var etpass:EditText
        private lateinit var logbtn:Button
        private lateinit var gotoregister:Button
        private lateinit var gotoforgotpass:TextView
        private lateinit var auth: FirebaseAuth

        private val sharedfile = "FoodAppSharedPref"
        private lateinit var sharedPreferences:SharedPreferences
        @SuppressLint("ClickableViewAccessibility")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_login)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request permission
                val LOCATION_PERMISSION_REQUEST_CODE = 1
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }
            auth = FirebaseAuth.getInstance()
            sharedPreferences = this.getSharedPreferences(sharedfile, Context.MODE_PRIVATE)

            etemail = findViewById(R.id.edemail)
            etemail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!isValidEmail(etemail.text.toString())){
                        etemail.setBackgroundResource(R.drawable.error_background)
                    }
                    else{
                        etemail.setBackgroundResource(R.drawable.rect)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}

            })

            etpass = findViewById(R.id.edpass)
            var isPasswordVisible = false

            etpass.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (etpass.right - etpass.compoundDrawables[2].bounds.width())) {
                        isPasswordVisible = !isPasswordVisible
                        if (isPasswordVisible) {
                            etpass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                            etpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.key_open, 0)
                        } else {
                            etpass.transformationMethod = PasswordTransformationMethod.getInstance()
                            etpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.key_close, 0)
                        }
                        etpass.setSelection(etpass.text.length)
                        return@setOnTouchListener true
                    }
                }
                return@setOnTouchListener false
            }

            /*etpass.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    var msg = validatePassword(etpass.text.toString());
                    if(msg != null){
                        etpass.setBackgroundResource(R.drawable.error_background)
                    }
                    else{
                        etpass.setBackgroundResource(R.drawable.rect)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}

            })
            */

            gotoregister = findViewById(R.id.regbtn)
            gotoregister.setOnClickListener {
                    val fragment = RegisterFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main,fragment)
                        .addToBackStack(null)
                        .commit()

                }

            gotoforgotpass = findViewById(R.id.forgotPassword)
            gotoforgotpass.setOnClickListener {
                    val frag = ForgotPass()
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main,frag)
                        .commit()
                }

            logbtn = findViewById(R.id.login)
            logbtn.setOnClickListener {
                loginprocess(etemail,etpass)
            }

        }

        private fun loginprocess(etemail:EditText,etpass: EditText) {
            val mail = etemail.text.toString().trim()
            val pass = etpass.text.toString().trim()
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            if(mail.isEmpty()){
                Toast.makeText(this,"Email is Blank",Toast.LENGTH_SHORT).show()
                etemail.requestFocus()
                makered()
                editor.putInt("id_key",1)
                editor.putString("Emailsp",mail)
            }
            if(pass.isEmpty()){
                Toast.makeText(this,"Password is Blank",Toast.LENGTH_SHORT).show()
                etpass.requestFocus()
                editor.putString("Passsp",pass)
                editor.apply()

            }
            if(!isValidEmail(mail)){
                Toast.makeText(this,"Invalid Email..",Toast.LENGTH_SHORT).show()
            }
            else {
                val passwordError = validatePassword(pass)
                if (passwordError != null) {
                    Toast.makeText(this, passwordError, Toast.LENGTH_SHORT).show()
                } else {
                    logininbase(mail,pass)
                }
            }
        }

        private fun makered() {

        }

        private fun logininbase(mail: String, pass: String) {
            auth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener {
                    if(it.isComplete){
                        Toast.makeText(this, "Logged In Successfull!", Toast.LENGTH_SHORT).show()
                        gotohome()
                    }
                    else{
                        Toast.makeText(this,"Log In Failed...",Toast.LENGTH_SHORT).show()
                    }
                }
        }

        private fun gotohome() {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

        fun isValidEmail(target: CharSequence): Boolean {
            return if (target.isEmpty()) {
                false
            } else {
                android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
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