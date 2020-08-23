package app.qrscan.di

import android.content.ClipboardManager
import android.content.Context
import androidx.navigation.NavController
import app.qrscan.data.repository.QrModelRepository
import app.qrscan.ui.details.presenter.DetailsPresenter
import app.qrscan.ui.details.presenter.DetailsPresenterImpl
import app.qrscan.ui.details.view.DetailsView
import app.qrscan.ui.details.view.DetailsViewImpl
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
object DetailsModule {

    @Provides
    @FragmentScoped
    fun provideView(context: Context, navController: NavController): DetailsView =
            DetailsViewImpl(context, navController)

    @Provides
    @FragmentScoped
    fun providePresenter(
            context: Context,
            view: DetailsView,
            repository: QrModelRepository
    ): DetailsPresenter {
        val clipboard =  context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val encodeUtil = EncodeUtil(MultiFormatReader(), BarcodeEncoder())
        return DetailsPresenterImpl(view, clipboard, repository, encodeUtil)
    }
}
