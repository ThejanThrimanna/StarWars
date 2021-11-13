package com.thejan.starwars.helper

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.thejan.starwars.R

/**
 * Created by Thejan Thrimanna on 11/12/21.
 */
fun startActivityRightToLeft(activity: Activity, intent: Intent) {
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left)
}

fun closeActivityLeftToRight(activity: Activity) {
    activity.overridePendingTransition(R.anim.left_in, R.anim.slide_to_right)
}

fun showProgress(activity: Activity): Dialog {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(
        ColorDrawable(0)
    )
    dialog.setContentView(R.layout.fragment_dialog_progress)
    dialog.setCancelable(false)
    dialog.window!!.decorView.setBackgroundResource(android.R.color.transparent)
    dialog.window!!.setDimAmount(0.0f)
    dialog.show()
    return dialog
}

fun loadImageGlide(activity: Context, url:String, iv: ImageView) {
    Glide.with(activity)
        .load(url)
        .into(iv)
}