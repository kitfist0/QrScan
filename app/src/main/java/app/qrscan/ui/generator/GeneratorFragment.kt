package app.qrscan.ui.generator

import app.qrscan.R
import app.qrscan.ext.customAfterTextChanged
import app.qrscan.ui.base.BaseFragment
import app.qrscan.ui.generator.presenter.GeneratorPresenter
import app.qrscan.ui.generator.view.GeneratorView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_generator.*

@AndroidEntryPoint
class GeneratorFragment: BaseFragment<GeneratorPresenter, GeneratorView>(R.layout.fragment_generator) {

    override fun setupViews() {
        super.setupViews()
        textInputEditText.customAfterTextChanged {
            presenter.onTextChanged(it)
        }
        textInputLayout.setEndIconOnClickListener {
            presenter.onSaveButtonClicked()
        }
    }
}
