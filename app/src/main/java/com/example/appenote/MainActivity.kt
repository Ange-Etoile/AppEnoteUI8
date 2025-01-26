package com.example.appenote

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val colorgreen = "2D7D51"
        val btnlogin = findViewById<Button>(R.id.btn_sign)
        val input_check = findViewById<CheckBox>(R.id.checked_remember)
        btnlogin.backgroundTintList= ContextCompat.getColorStateList(this,R.color.blue)

        val emailED = findViewById<TextInputEditText>(R.id.emailED)
        val passwordED = findViewById<TextInputEditText>(R.id.pwED)
        val emailL = findViewById<TextInputLayout>(R.id.emailL)
        val passwordL = findViewById<TextInputLayout>(R.id.pwL)




        //validation pour gerer l'email

        emailED.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s:CharSequence?,start :Int, count:Int,after:Int)
            {
                val email = s.toString()
                if(!email.endsWith("@gmail.com")){
                    emailL.error=null;
                    emailL.helperText="email invalide"
                    emailL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                    emailL.boxStrokeColor=Color.RED
                    emailL.hintTextColor=ColorStateList.valueOf(Color.RED)
                }
                else{
                    emailL.error=null;
                    emailL.helperText="email valide"
                    emailL.setHelperTextColor(ColorStateList.valueOf(Color.GREEN))
                    emailL.boxStrokeColor=Color.GREEN
                    emailL.hintTextColor=ColorStateList.valueOf(Color.GREEN)
                }
            }

            override fun afterTextChanged(s: Editable?) {


            }
        })

        // validation pour gerer le password
        passwordED.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                if(password.length<8){
                    passwordL.helperText="mininum 8 caracteres"
                    passwordL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                    passwordL.boxStrokeColor=Color.RED
                    passwordL.hintTextColor=ColorStateList.valueOf(Color.RED)
                }
                else{
                    passwordL.helperText="passwordvalide"
                    passwordL.setHelperTextColor(ColorStateList.valueOf(Color.GREEN))
                    passwordL.boxStrokeColor=Color.GREEN
                    passwordL.hintTextColor=ColorStateList.valueOf(Color.GREEN)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        //validation du button

        btnlogin.setOnClickListener{
            val emailr = emailED.text.toString()
            val passwordr= passwordED.text.toString()
            if(emailr.length==0 && passwordr.length == 0){
                emailL.error=null
                emailL.helperText="remplir le champ"
                emailL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                emailL.boxStrokeColor=Color.RED
                emailL.hintTextColor=ColorStateList.valueOf(Color.RED)
                //==============================================================
                passwordL.error=null
                passwordL.helperText="veillez remplir le champ"
                passwordL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                passwordL.boxStrokeColor=Color.RED
                passwordL.hintTextColor=ColorStateList.valueOf(Color.RED)

            }
            else {
                if(emailr.length == 0){
                    emailL.error=null
                    emailL.helperText="remplir le champ"
                    emailL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                    emailL.boxStrokeColor=Color.RED
                    emailL.hintTextColor=ColorStateList.valueOf(Color.RED)
                }
                else{
                    if (passwordr.length == 0){
                        passwordL.error=null
                        passwordL.helperText="veillez remplir le champ"
                        passwordL.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                        passwordL.boxStrokeColor=Color.RED
                        passwordL.hintTextColor=ColorStateList.valueOf(Color.RED)
                    }
                    else{
                        println("donnee valide tout va bien")
                        Toast.makeText(this, "donnee valide",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        

    }
}