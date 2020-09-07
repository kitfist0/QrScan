package app.qrscan.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import app.qrscan.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_ui)
    }
}
