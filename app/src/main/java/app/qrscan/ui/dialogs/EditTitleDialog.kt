package app.qrscan.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import app.qrscan.R
import app.qrscan.ext.customAfterTextChanged
import app.qrscan.ext.shortToast
import app.qrscan.util.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EditTitleDialog : BottomSheetDialogFragment(), View.OnClickListener {

    companion object {

        private fun getTitleBundle(title: String): Bundle =
            bundleOf(Constants.TITLE_RESULT_KEY to title.trim())
    }

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val title = arguments?.let { EditTitleDialogArgs.fromBundle(it).title } ?: ""
        val view = inflater.inflate(R.layout.dialog_edit_title, container, false)
        textInputLayout = view.findViewById(R.id.textInputLayout)
        textInputEditText = view.findViewById(R.id.textInputEditText)
        textInputEditText.setText(title)
        textInputEditText.customAfterTextChanged { textInputLayout.error = null }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.buttonOk).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOk -> textInputEditText.text?.let {
                when {
                    it.isEmpty() ->
                        requireContext().shortToast(R.string.empty_text_field)
                    it.length > Constants.TITLE_MAX_LENGTH ->
                        requireContext().shortToast(R.string.text_is_too_long)
                    else ->
                        setFragmentResult(Constants.TITLE_REQUEST_KEY, getTitleBundle(it.toString()))
                }
                dismiss()
            }
        }
    }
}
