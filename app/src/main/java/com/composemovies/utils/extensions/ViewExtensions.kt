package com.composemovies.utils.extensions


import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.composemovies.dependency_injection.ViewModelModule


inline fun <reified VM : ViewModel> ComponentActivity.provideViewModel() =
    ViewModelModule.provideViewModel<VM>(this)

//fun imageFromGlide(context: Context, poster_path:String, imageView :ImageView){
//    // Using Glide to ImageView
//    val url = "https://image.tmdb.org/t/p/w500/${poster_path}" //basic path to get images + specific end path from the movie
//    val calendar = Calendar.getInstance()
//    val versionNumber =calendar.get(Calendar.DAY_OF_WEEK) + calendar.get(Calendar.WEEK_OF_YEAR) +
//            calendar.get(Calendar.YEAR) *100
//    Glide.with(context)
//        .load(url)
//        .signature(IntegerVersionSignature(versionNumber))
//        .placeholder(circularProgressDrawable(context))
//        .error(ContextCompat.getDrawable(context.applicationContext, R.drawable.ic_baseline_image_not_supported_40 ))
//        .into(imageView)

//}