package com.example.nusukkeyassistant

import android.app.Activity
import android.os.Bundle
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

class MainActivity : Activity() {
    private val codeBankUrl = "https://is.gd/nusukcode"
    private var codeInput: EditText? = null
    private var statusInput: EditText? = null
    private var logView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 28, 32, 24)
            setBackgroundColor(0xFFF7F8FC.toInt())
        }

        val title = TextView(this).apply {
            text = "Nusuk Key Assistant"
            textSize = 24f
            setTextColor(0xFF17356B.toInt())
            gravity = Gravity.CENTER
        }
        layout.addView(title, matchWrap())

        val intro = TextView(this).apply {
            text = "কোড সংগ্রহ, কপি ও ব্যবহারের হিসাব রাখুন। Nusuk-এ জমা দেওয়ার আগে প্রতিটি কোড নিজে যাচাই করুন।"
            textSize = 15f
            setPadding(0, 18, 0, 18)
        }
        layout.addView(intro, matchWrap())

        layout.addView(makeButton("১. Nusuk Code Bank খুলুন") { openUrl(codeBankUrl) }, matchWrap())

        codeInput = EditText(this).apply {
            hint = "ওয়েবসাইট থেকে পাওয়া Key এখানে পেস্ট করুন"
            textSize = 17f
            singleLine (true)
        }
        layout.addView(codeInput, matchWrap())

        layout.addView(makeButton("Key কপি করুন") { copyCode() }, matchWrap())
        layout.addView(makeButton("২. Nusuk অ্যাপ খুলুন") { openNusuk() }, matchWrap())

        statusInput = EditText(this).apply {
            hint = "ফলাফল লিখুন (যেমন: সফল / ব্যর্থ)"
            textSize = 15f
        }
        layout.addView(statusInput, matchWrap())

        layout.addView(makeButton("ফলাফল লগে সংরক্ষণ করুন") { saveResult() }, matchWrap())

        logView = TextView(this).apply {
            text = "ব্যবহারের লগ এখানে দেখা যাবে।"
            textSize = 14f
            setPadding(0, 18, 0, 0)
        }
        layout.addView(logView, matchWrap())

        val note = TextView(this).apply {
            text = "নোট: এই সহায়ক অ্যাপ Nusuk-এর নিরাপত্তা যাচাই, সীমা বা নিয়ম এড়ায় না এবং নিজে থেকে কোড জমা দেয় না।"
            textSize = 12f
            setTextColor(0xFF666666.toInt())
            setPadding(0, 22, 0, 0)
        }
        layout.addView(note, matchWrap())

        setContentView(ScrollView(this).apply { addView(layout) })
    }

    private fun matchWrap() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply { bottomMargin = 14 }

    private fun makeButton(label: String, action: () -> Unit): Button =
        Button(this).apply { text = label; setOnClickListener { action() } }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun copyCode() {
        val code = codeInput?.text?.toString()?.trim().orEmpty()
        if (code.isBlank()) {
            toast("আগে Key পেস্ট করুন")
            return
        }
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("Nusuk Key", code))
        toast("Key কপি হয়েছে। এখন Nusuk-এ পেস্ট করতে পারবেন।")
    }

    private fun openNusuk() {
        val launch = packageManager.getLaunchIntentForPackage("com.moh.nusukapp")
        if (launch != null) startActivity(launch)
        else {
            toast("Nusuk অ্যাপ খুঁজে পাওয়া যায়নি। Play Store থেকে ইনস্টল/আপডেট যাচাই করুন।")
            openUrl("https://play.google.com/store/search?q=Nusuk&c=apps")
        }
    }

    private fun saveResult() {
        val code = codeInput?.text?.toString()?.trim().orEmpty()
        val result = statusInput?.text?.toString()?.trim().orEmpty()
        if (code.isBlank() || result.isBlank()) {
            toast("Key এবং ফলাফল—দুটিই লিখুন")
            return
        }
        val prefs = getSharedPreferences("keylog", MODE_PRIVATE)
        val old = prefs.getString("entries", "").orEmpty()
        val line = "${System.currentTimeMillis()} | $code | $result"
        val updated = (if (old.isBlank()) line else "$line\n$old")
        prefs.edit().putString("entries", updated).apply()
        logView?.text = updated
        codeInput?.setText("")
        statusInput?.setText("")
        toast("ফলাফল সংরক্ষিত হয়েছে")
    }

    private fun toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
