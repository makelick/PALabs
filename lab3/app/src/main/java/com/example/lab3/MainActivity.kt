package com.example.lab3

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.lab3.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i -> chooseInputType() }
    }

    private fun chooseInputType() {

        val constraintLayout = findViewById<ConstraintLayout>(R.id.parent_layout)
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        if (binding.radioGroup.checkedRadioButtonId == R.id.option_search
            || binding.radioGroup.checkedRadioButtonId == R.id.option_delete) {
            binding.editTextData.visibility = View.GONE

            constraintSet.connect(
                R.id.text_key,
                ConstraintSet.END,
                R.id.parent_layout,
                ConstraintSet.END,
                0
            )

        } else {
            binding.editTextData.visibility = View.VISIBLE

            constraintSet.connect(
                R.id.text_key,
                ConstraintSet.END,
                R.id.text_data,
                ConstraintSet.START,
                0
            )
        }
        constraintSet.applyTo(constraintLayout)
    }
}