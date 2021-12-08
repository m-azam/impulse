package com.purdue.impulse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val PFWID = findViewById<EditText>(R.id.editTextNumber);

            fun validatePFWID():Boolean {

                val pfwidInput:String = PFWID.text.toString().trim();
                val pattern_id = Regex("^900[0-9]+")

                if (pfwidInput.isEmpty()) {
                    PFWID.setError("Field can not be empty");
                    return false;
                }
                else if (pattern_id.matches(pfwidInput) == false) {
                    PFWID.setError("Entered PFWID is incorrect");
                    return false;
                }
                else if (pfwidInput.length != 9){
                    PFWID.setError("Entered PFWID is incorrect");
                    return false;
                }
                else {
                    PFWID.setError(null);
                    return true;
                }
            }

                if (validatePFWID()) {
                    val input = "PFWID: " + PFWID.text.toString();
                    Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

                    var intent: Intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                    finish()
            }
        }
    }
}