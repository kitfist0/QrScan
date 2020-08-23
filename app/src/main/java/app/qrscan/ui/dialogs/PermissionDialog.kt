package app.qrscan.ui.dialogs

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import app.qrscan.BuildConfig
import app.qrscan.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_permission.view.*
import javax.inject.Inject

@AndroidEntryPoint
@RequiresApi(api = Build.VERSION_CODES.M)
class PermissionDialog : BottomSheetDialogFragment(), View.OnClickListener {

    companion object {

        private fun getAppInfoIntent() = Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            }

        private fun Fragment.shouldShowRationale(permission: String): Boolean =
                ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)

        private fun Fragment.permissionDenied(permission: String): Boolean =
                ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_DENIED

        private fun TextView.setRationaleText(permission: String) {
            when (permission) {
                Manifest.permission.CAMERA ->
                    text = context.getString(R.string.permission_rationale_camera)
            }
        }
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var permission: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { permission = PermissionDialogArgs.fromBundle(it).permission }

        val neverAskAgain = neverAskPreferenceTrue(permission)
                .and(shouldShowRationale(permission).not())
                .and(permissionDenied(permission))
        Log.d("RATIONALE_DIALOG", "neverAskAgainClicked: $neverAskAgain")

        setupViews(neverAskAgain)
        view.buttonOpenSettings.setOnClickListener(this)
        view.buttonCancel.setOnClickListener(this)
        view.buttonOk.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when {
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ->
                dismiss()
            shouldShowRationale(permissions[0]).not() ->
                setupViews(true).also { saveNeverAskPreference(permissions[0], true) }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOk -> {
                requestPermissions(arrayOf(permission), 12345)
                saveNeverAskPreference(permission, false)
            }
            R.id.buttonCancel -> dismiss()
            R.id.buttonOpenSettings -> startActivity(getAppInfoIntent()).also { dismiss() }
        }
    }

    private fun setupViews(neverAskAgain: Boolean) {
        requireView().textMessage.setRationaleText(permission)
        requireView().buttonOpenSettings.visibility = if (neverAskAgain) View.VISIBLE else View.GONE
        requireView().buttonOk.visibility = if (neverAskAgain) View.GONE else View.VISIBLE
    }

    private fun saveNeverAskPreference(permission: String, value: Boolean) =
            sharedPreferences.edit().putBoolean(permission, value).apply()

    private fun neverAskPreferenceTrue(permission: String): Boolean =
            sharedPreferences.getBoolean(permission, false)
}
