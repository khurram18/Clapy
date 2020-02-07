package com.example.clapy

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.clapButton
import kotlinx.android.synthetic.main.activity_main.clapProgressBar

// Clap image taken from Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
class MainActivity : AppCompatActivity() {

private val scaleUpAnimationListener = object : Animation.AnimationListener {

  override fun onAnimationRepeat(p0: Animation?) {}

  override fun onAnimationEnd(p0: Animation?) {
    performScaleDownAnimation()
  }

  override fun onAnimationStart(p0: Animation?) {}
}

private val scaleDownAnimationListener = object : Animation.AnimationListener {

  override fun onAnimationRepeat(p0: Animation?) {}

  override fun onAnimationEnd(p0: Animation?) {
    incrementClapProgress()
  }

  override fun onAnimationStart(p0: Animation?) {}
}

override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContentView(R.layout.activity_main)
  addListener()
}

private fun addListener() {
  clapButton.setOnClickListener {
    performAnimation()
  }
}

private fun performAnimation() {
  performScaleUpAnimation()
}

private fun performScaleUpAnimation() {

  val buttonAnimation = newScaleUpAnimation()
  val progressAnimation = newScaleUpAnimation()
  buttonAnimation.setAnimationListener(scaleUpAnimationListener)

  clapButton.startAnimation(buttonAnimation)
  clapProgressBar.startAnimation(progressAnimation)

}

private fun performScaleDownAnimation() {
  val buttonAnimation = newScaleDownAnimation()
  val progressAnimation = newScaleDownAnimation()
  buttonAnimation.setAnimationListener(scaleDownAnimationListener)

  clapButton.startAnimation(buttonAnimation)
  clapProgressBar.startAnimation(progressAnimation)
}

private fun incrementClapProgress() {
  val progress = clapProgressBar.progress
  clapProgressBar.progress = progress + 1
}
private fun addNewClap() {

}
}

private fun newScaleUpAnimation() = ScaleAnimation(1f, 1.5f, 1f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
  duration = 500
  fillAfter = true
}

private fun newScaleDownAnimation() = ScaleAnimation(1.5f, 1f, 1.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
  duration = 500
  fillAfter = true
}