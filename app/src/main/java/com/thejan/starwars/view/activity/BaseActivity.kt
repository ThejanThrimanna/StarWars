package com.thejan.starwars.view.activity

import android.app.Dialog
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.thejan.starwars.R

/**
 * Created by Thejan Thrimanna on 11/13/21.
 */
open class BaseActivity : AppCompatActivity() {
    fun setToolBar(toolbar: Toolbar, title: String, isHome: Boolean, isShowBack: Boolean = true) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        if (isHome) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        } else {
            if (isShowBack) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            }
        }
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}