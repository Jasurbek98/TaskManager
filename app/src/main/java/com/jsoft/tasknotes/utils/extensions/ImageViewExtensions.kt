package com.jsoft.tasknotes.utils.extensions

import android.os.Build
import android.widget.ImageView
import androidx.annotation.ColorRes
import com.jsoft.tasknotes.R
import com.squareup.picasso.Picasso

fun ImageView.setInt(@ColorRes colorRes: Int){
    val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        resources.getColor(colorRes, context.theme)
    }else{
        resources.getColor(colorRes)
    }
    setColorFilter(color)
}

fun ImageView.loadFromUrl(url:String){
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.person_icon)
        .resize(80.dp,80.dp)
        .centerCrop()
//        .error(R.drawable.image_avatar)
        .into(this)
}