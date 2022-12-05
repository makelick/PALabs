package com.example.lab3

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.lab3.databinding.ActivityMainBinding
import java.io.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val filename = "tree.bin"
    private var tree = BTree()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.radioGroup.setOnCheckedChangeListener { _, _ -> chooseInputType() }
        binding.button.setOnClickListener { executeAction() }

        binding.editTextKey.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.editTextData.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    override fun onPause() {
        saveTree(tree)
        super.onPause()
    }

    override fun onResume() {
        tree = loadTree()
        super.onResume()
    }

    private fun executeAction() {
        val key = binding.editTextKey.text.toString().toIntOrNull()
        val data = binding.editTextData.text.toString()

        val result =
            if (key != null) {
                when (binding.radioGroup.checkedRadioButtonId) {
                    R.id.option_insert -> tree.insert(applicationContext, Record(key, data))
                    R.id.option_update -> tree.update(applicationContext, Record(key, data))
                    R.id.option_delete -> tree.delete(applicationContext, key)
                    R.id.option_search -> tree.search(applicationContext, key)
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
            binding.textData.visibility = View.GONE

            constraintSet.connect(
                R.id.text_key, ConstraintSet.END,
                R.id.parent_layout, ConstraintSet.END
            )
        } else {
            binding.editTextData.visibility = View.VISIBLE
            binding.textData.visibility = View.VISIBLE

            constraintSet.connect(
                R.id.text_key, ConstraintSet.END,
                R.id.text_data, ConstraintSet.START
            )
        }
        constraintSet.applyTo(binding.parentLayout)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            binding.button.callOnClick()
            return true
        }
        return false
    }

    private fun saveTree(tree: BTree) {
        val fileOut = FileOutputStream(File(filesDir, filename))
        val objectOut = ObjectOutputStream(fileOut)
        objectOut.writeObject(tree)
        objectOut.close()
    }

    private fun loadTree(): BTree {
        return try {
            val fileIn = FileInputStream(File(filesDir, filename))
            val objectIn = ObjectInputStream(fileIn)
            val obj = objectIn.readObject()
            objectIn.close()
            obj as BTree
        } catch (e: Exception) {
            BTree()
        }
    }
}