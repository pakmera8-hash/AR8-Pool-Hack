package com.ar8.hack

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // A code to track our permission request
    private val OVERLAY_PERMISSION_REQ_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val codeInput = findViewById<EditText>(R.id.promoCodeInput)
        val loginBtn = findViewById<Button>(R.id.loginButton)
        val contactBtn = findViewById<Button>(R.id.contactButton)

        loginBtn.setOnClickListener {
            val code = codeInput.text.toString()
            if (code == "PAK8") {
                Toast.makeText(this, "Code Accepted. Checking Permissions...", Toast.LENGTH_SHORT).show()
                checkOverlayPermissionAndStart()
            } else {
                Toast.makeText(this, "Invalid Code. Pay 1000 Rs for access.", Toast.LENGTH_LONG).show()
            }
        }

        contactBtn.setOnClickListener {
            val url = "https://wa.me/923244633007?text=I%20want%20to%20buy%20AR8%20Lifetime%20Access"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    // This is the new security bypass
    private fun checkOverlayPermissionAndStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // If we don't have permission, open the Android settings screen to ask for it
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
            } else {
                // We have permission! Start the hack.
                startOverlay()
            }
        } else {
            startOverlay()
        }
    }

    // If the user grants permission and comes back, start the hack
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    startOverlay()
                } else {
                    Toast.makeText(this, "Permission Denied. Hack cannot run.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startOverlay() {
        val intent = Intent(this, OverlayService::class.java)
        startService(intent)
        Toast.makeText(this, "AR8 Overlay Started!", Toast.LENGTH_SHORT).show()
        
        // Minimize our app so the user can open 8 Ball Pool
        moveTaskToBack(true) 
    }
}