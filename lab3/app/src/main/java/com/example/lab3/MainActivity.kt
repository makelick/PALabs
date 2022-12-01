package com.example.lab3

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.lab3.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i -> chooseInputType() }
        binding.button.setOnClickListener { executeAction() }

        binding.editTextKey.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.editTextData.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun executeAction() {
        val key = binding.editTextKey.text?.toString()?.toIntOrNull()
        val data = binding.editTextData.text?.toString()

        // TODO: Create B-tree and add implementation of actions bellow

        val result =
            if (key != null) {
                when (binding.radioGroup.checkedRadioButtonId) {
                    R.id.option_insert -> "Insert key $key with data \"$data\""
                    R.id.option_update -> "Update key $key data to \"$data\""
                    R.id.option_delete -> "Delete key $key"
                    R.id.option_search -> "Search key $key"
                    else -> "Invalid action"
                }
            } else "Invalid key"

        binding.result.text = getString(R.string.result, result)
    }

    private fun chooseInputType() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.parentLayout)

        if (binding.radioGroup.checkedRadioButtonId == R.id.option_search
            || binding.radioGroup.checkedRadioButtonId == R.id.option_delete
        ) {

            binding.editTextData.visibility = View.GONE

            constraintSet.connect(
                R.id.text_key, ConstraintSet.END,
                R.id.parent_layout, ConstraintSet.END
            )
        } else {
            binding.editTextData.visibility = View.VISIBLE

            constraintSet.connect(
                R.id.text_key, ConstraintSet.END,
                R.id.text_data, ConstraintSet.START
            )
        }
        constraintSet.applyTo(binding.parentLayout)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}