package com.example.myapplicationsmartstudy


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private lateinit var auth: FirebaseAuth



class MainActivity : AppCompatActivity() {


    private lateinit var textView: TextView
    private lateinit var client: GoogleSignInClient
    private val REQ_ONE_TAP = 2
    val auth = Firebase.auth

    override fun onStart() {
        super.onStart()


        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }


    fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Toast.makeText(this, "You Signed In successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, BottomBar::class.java))

        } else {
            Toast.makeText(this, "Hãy đăng nhập để sử dụng dịch vụ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = findViewById<ImageButton>(R.id.login)
        val email = findViewById<EditText>(R.id.account)
        val pass = findViewById<EditText>(R.id.pass)
        val google = findViewById<ImageButton>(R.id.google);


        val currentUser = auth.currentUser

        val name = currentUser?.email





        login.setOnClickListener {


            auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this,options)



        google.setOnClickListener()
        {
            val intent = client.signInIntent;
            startActivityForResult(intent,100001)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val currentUser = auth.currentUser

        if (requestCode == 100001)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful)
                    {

                       var  isNew = task.getResult().additionalUserInfo?.isNewUser

                        if (isNew == true)
                        {
                            Toast.makeText(this, "Bạn là người mới",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this,"Bạn là người đã có kinh nghiệm",Toast.LENGTH_SHORT).show()
                        }

                        val i = Intent(this,BottomBar::class.java)
                        startActivity(i)

                    }else{
                        Toast.makeText(this, task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }


        }
    }



}















