package com.example.clapy

import android.animation.Animator
import android.graphics.Path
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import com.example.clapy.helper.ObjectAnimatorCompat
import kotlinx.android.synthetic.main.activity_main.clapButton
import kotlinx.android.synthetic.main.activity_main.clapProgressBar
import kotlinx.android.synthetic.main.activity_main.rootLayout

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
    addNewClap()
  }

  override fun onAnimationStart(p0: Animation?) {}
}

private  val pathAnimationListener = object : Animator.AnimatorListener {

  override fun onAnimationRepeat(p0: Animator?) {}

  override fun onAnimationEnd(p0: Animator?) {
    clapImageView?.let {
      rootLayout.removeView(it)
    }
  }

  override fun onAnimationCancel(p0: Animator?) {}

  override fun onAnimationStart(p0: Animator?) {}

}

private var clapCount = 0
private var clapImageView: ImageView? = null

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
  clapCount++
  if (clapCount > 1) {
    clapProgressBar.progress = clapCount
  }
}
private fun addNewClap() {

  val imageView = ImageView(this)
  imageView.id = ViewCompat.generateViewId()
  imageView.setImageResource(R.drawable.clap)
  rootLayout.addView(imageView)

  val constraintSet = ConstraintSet()
  constraintSet.clone(rootLayout)
  constraintSet.connect(imageView.id, ConstraintSet.START, rootLayout.id, ConstraintSet.START)

  val path = Path()
  path.addCircle((rootLayout.width / 2).toFloat(), (rootLayout.height / 2).toFloat(), 200f, Path.Direction.CW)

  val animator = ObjectAnimatorCompat.ofFloat(imageView, "translationX", "translationY", path)
  animator.duration = 1000
  animator.addListener(pathAnimationListener)
  animator.start()
  clapImageView = imageView
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