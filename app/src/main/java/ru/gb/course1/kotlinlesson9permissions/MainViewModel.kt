package ru.gb.course1.kotlinlesson9permissions

import android.Manifest
import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val targetPermissions = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_CALL_LOG
    )

    private val _permissionsTitle = MutableLiveData(targetPermissions.first())
    val permissionsTitle: LiveData<String>
        get() = _permissionsTitle

    private val _permissionEnabled = MutableLiveData<Boolean>()
    val permissionEnabled: LiveData<Boolean>
        get() = _permissionEnabled

    private val _shouldShowRationale = MutableLiveData<Boolean>()
    val shouldShowRationale: LiveData<Boolean>
        get() = _shouldShowRationale

    private val _resultText = MutableLiveData<String>()
    val resultText: LiveData<String>
        get() = _resultText

    fun checkPermission(activity: Activity) {
        val permissionResult =
            ContextCompat.checkSelfPermission(activity, targetPermissions.first())
        val hasPermission = permissionResult == PermissionChecker.PERMISSION_GRANTED
        _permissionEnabled.postValue(hasPermission)

        val shouldShow =
            ActivityCompat.shouldShowRequestPermissionRationale(activity, targetPermissions.first())
        _shouldShowRationale.postValue(shouldShow)
    }

    fun requestPermission(permissionLauncher: ActivityResultLauncher<String>) {
        permissionLauncher.launch(targetPermissions.first())
    }

    fun onPermissionResult(granted: Boolean) {
        _resultText.postValue(if (granted) "SUCCESS" else "FAIL")
    }
}