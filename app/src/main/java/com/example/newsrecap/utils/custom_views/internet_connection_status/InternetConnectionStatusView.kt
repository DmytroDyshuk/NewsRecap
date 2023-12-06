package com.example.newsrecap.utils.custom_views.internet_connection_status

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.newsrecap.R
import com.example.newsrecap.databinding.ViewInternentConnectionStatusBinding
import com.example.newsrecap.utils.connectivity_observer.ConnectivityObserver

class InternetConnectionStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val translationYStart = -30F
    private val translationYEnd = 0F

    private val binding: ViewInternentConnectionStatusBinding =
        ViewInternentConnectionStatusBinding.inflate(LayoutInflater.from(context), this, true)

    fun updateViewWithConnectStatus(status: ConnectivityObserver.Status) {
        if (status == ConnectivityObserver.Status.Lost) {
            setTextMessage(ContextCompat.getString(context, R.string.connection_lost))
            setViewBackgroundColor(ContextCompat.getColor(context, R.color.red))
            setTextColor(ContextCompat.getColor(context, R.color.black))
            showView()
        } else {
            setTextMessage(ContextCompat.getString(context, R.string.connection_restored))
            setViewBackgroundColor(ContextCompat.getColor(context, R.color.green))
            setTextColor(ContextCompat.getColor(context, R.color.white))
            hideView()
        }
    }

    private fun setViewBackgroundColor(color: Int) {
        binding.tvInternetConnection.setBackgroundColor(color)
    }

    private fun setTextMessage(text: String) {
        binding.tvInternetConnection.text = text
    }

    private fun setTextColor(color: Int) {
        binding.tvInternetConnection.setTextColor(color)
    }

    private fun showView() {
        val animatorOut = createAnimator(
            binding.root,
            startValue = binding.root.translationY,
            endValue = translationYEnd
        )
        binding.root.visibility = View.VISIBLE
        animatorOut.start()
    }

    private fun hideView() {
        binding.root.postDelayed({
            val animatorIn = createAnimator(
                binding.root,
                startValue = binding.root.translationY,
                endValue = translationYStart - 30
            )
            animatorIn.start()
        }, 800L)
    }

    private fun createAnimator(view: View, startValue: Float, endValue: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, startValue, endValue).apply {
            this.duration = 700
        }
    }
}