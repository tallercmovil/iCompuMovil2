package com.amaurypm.icompumvil2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.amaurypm.icompumvil2.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.analytics.ktx.logEvent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private var user: FirebaseUser? = null

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        analytics = Firebase.analytics

        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
            param(FirebaseAnalytics.Param.ITEM_ID, "1");
            param(FirebaseAnalytics.Param.ITEM_NAME, "Pantalla principal");
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "Pantalla");
        }

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser
        binding.tvUser.text = user?.email

        if(user?.isEmailVerified != true){
            binding.tvCorreoNoVerificado.visibility = View.VISIBLE
        }

        binding.btnSignOut.setOnClickListener {

            analytics.logEvent("clic_botón"){
                param("acción", "clic")
                param("botón", "Logout")
            }


            firebaseAuth.signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }

    }
}