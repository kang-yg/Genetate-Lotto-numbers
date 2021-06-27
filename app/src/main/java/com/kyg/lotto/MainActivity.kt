package com.kyg.lotto

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.kyg.lotto.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    private val numberPicker: NumberPicker by lazy {
        activityMainBinding.numberPicker
    }
    private val addNumBt: Button by lazy {
        activityMainBinding.addNumBt
    }
    private val numClearBt: Button by lazy {
        activityMainBinding.numClearBt
    }
    private val genNumBt: Button by lazy {
        activityMainBinding.genNumBt
    }
    private val tvNumList: List<TextView> by lazy {
        listOf<TextView>(
            activityMainBinding.tvNum1,
            activityMainBinding.tvNum2,
            activityMainBinding.tvNum3,
            activityMainBinding.tvNum4,
            activityMainBinding.tvNum5,
            activityMainBinding.tvNum6,
            activityMainBinding.tvNum7
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setNumberPicker()
        setAddNumBt()
        setClearBt()
        setGenNumBt()
    }

    private fun setNumberPicker() {
        numberPicker.minValue = 1
        numberPicker.maxValue = 45
    }

    private fun setAddNumBt() {
        addNumBt.setOnClickListener {
            val numberPickerValue: Int = numberPicker.value
            for (i in tvNumList) {
                if (!i.text.equals("")) {
                    if (i.text.toString().toInt() == numberPickerValue) {
                        Toast.makeText(this, resources.getString(R.string.TOAST_ALREADY_EXIST_NUM), Toast.LENGTH_SHORT).show()
                        break
                    }
                } else if (i.text.isNullOrBlank()) {
                    i.text = numberPickerValue.toString()
                    setShapeDrawableColor(resources.getDrawable(R.drawable.tv_circle, null), i, this)
                    i.visibility = View.VISIBLE
                    break
                }
            }
        }
    }

    private fun setClearBt() {
        numClearBt.setOnClickListener {
            for (i in tvNumList) {
                i.text = ""
                i.visibility = View.GONE
            }
        }
    }

    private fun setGenNumBt() {
        genNumBt.setOnClickListener {
            val list = getRandomNum()
            for (i in tvNumList.withIndex()) {
                tvNumList[i.index].text = list[i.index].toString()
                setShapeDrawableColor(resources.getDrawable(R.drawable.tv_circle, null), i.value, this)
                tvNumList[i.index].visibility = View.VISIBLE
            }
        }
    }

    private fun getRandomNum(): List<Int> {
        val numberList = mutableListOf<Int>()
        for (i in tvNumList) {
            if (!i.text.equals("") || !i.text.isNullOrEmpty()) {
                numberList.add(i.text.toString().toInt())
            }

            if (numberList.size == 7) {
                numberList.clear()
                break
            }
        }

        while (numberList.size < 7) {
            val randomNum = Random.nextInt(1, 46)
            if (!numberList.contains(randomNum)) {
                numberList.add(randomNum)
            }
        }
        numberList.sort()

        return numberList
    }

    private fun setShapeDrawableColor(drawable: Drawable, view: View, context: Context) {
        if (drawable is ShapeDrawable) {
            drawable.paint.setColor(getLottoColor(view, context))
        } else if (drawable is GradientDrawable) {
            drawable.setColor(getLottoColor(view, context))
        } else if (drawable is ColorDrawable) {
            drawable.setColor(getLottoColor(view, context))
        }

        view.background = drawable
    }

    private fun getLottoColor(view: View, context: Context): Int {
        val resultColor: Int

        when ((view as TextView).text.toString().toInt()) {
            in 1..10 -> {
                resultColor = ContextCompat.getColor(context, R.color.LOTTO_YELLOW)
            }

            in 11..20 -> {
                resultColor = ContextCompat.getColor(context, R.color.LOTTO_BLUE)
            }

            in 21..30 -> {
                resultColor = ContextCompat.getColor(context, R.color.LOTTO_RED)
            }

            in 31..40 -> {
                resultColor = ContextCompat.getColor(context, R.color.LOTTO_GRAY)
            }

            in 41..45 -> {
                resultColor = ContextCompat.getColor(context, R.color.LOTTO_GREEN)
            }

            else -> {
                resultColor = ContextCompat.getColor(context, R.color.LOTTO_RED)
            }
        }

        return resultColor
    }
}