package ru.gb.course1.kotlinlesson9permissions

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import ru.gb.course1.kotlinlesson9permissions.databinding.ActivityMainBinding

private const val CONTACTS_PERMISSION_REQUEST_CODE = 1212

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val targetPermission: String = Manifest.permission.READ_CONTACTS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.permissionNameTextView.text = targetPermission

        binding.checkButton.setOnClickListener {
            val permissionResult = checkSelfPermission(targetPermission)
            val hasPermission = permissionResult == PermissionChecker.PERMISSION_GRANTED
            binding.permissionCheckbox.isChecked = hasPermission
            binding.rationalyCheckbox.isChecked =
                shouldShowRequestPermissionRationale(targetPermission)
        }

        binding.requestButton.setOnClickListener {
            requestPermissions(
                arrayOf(targetPermission),
                CONTACTS_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        binding.resultTextView.text =
            PermissionResult(requestCode, permissions, grantResults).toString()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

private data class PermissionResult(
    val requestCode: Int,
    val permissions: Array<out String>,
    val grantResults: IntArray
)