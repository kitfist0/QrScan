package app.qrscan.di

import android.content.Context
import androidx.navigation.NavController
import app.qrscan.data.repository.QrModelRepository
import app.qrscan.ui.generator.presenter.GeneratorPresenter
import app.qrscan.ui.generator.presenter.GeneratorPresenterImpl
import app.qrscan.ui.generator.view.GeneratorView
import app.qrscan.ui.generator.view.GeneratorViewImpl
import app.qrscan.util.EncodeUtil
import com.google.zxing.MultiFormatReader
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object GeneratorModule {

    @Provides
    @FragmentScoped
    fun provideView(context: Context, navController: NavController): GeneratorView =
            GeneratorViewImpl(context, navController)

    @Provides
    @FragmentScoped
    fun providePresenter(
            view: GeneratorView,
            repository: QrModelRepository
    ): GeneratorPresenter {
        val util = EncodeUtil(MultiFormatReader(), BarcodeEncoder())
        return GeneratorPresenterImpl(view, util, repository)
    }
}
