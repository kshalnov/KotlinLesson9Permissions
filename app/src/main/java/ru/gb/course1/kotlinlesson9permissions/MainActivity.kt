package ru.gb.course1.kotlinlesson9permissions

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.gb.course1.kotlinlesson9permissions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.onPermissionResult(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.permissionsTitle.observe(this) {
            binding.permissionNameTextView.text = it
        }
        viewModel.permissionEnabled.observe(this) {
            binding.permissionCheckbox.isChecked = it
        }
        viewModel.shouldShowRationale.observe(this) {
            binding.rationalyCheckbox.isChecked = it
        }
        viewModel.resultText.observe(this) {
            binding.resultTextView.text = it
        }

        binding.checkButton.setOnClickListener {
            viewModel.checkPermission(this)
        }
        binding.requestButton.setOnClickListener {
            viewModel.requestPermission(permissionLauncher)
        }
    }
}
