package app.qrscan.di

import android.content.Context
import androidx.navigation.NavController
import app.qrscan.data.repository.QrModelRepository
import app.qrscan.ui.history.presenter.HistoryPresenter
import app.qrscan.ui.history.presenter.HistoryPresenterImpl
import app.qrscan.ui.history.view.HistoryView
import app.qrscan.ui.history.view.HistoryViewImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object HistoryModule {

    @Provides
    @FragmentScoped
    fun provideView(context: Context, navController: NavController): HistoryView =
            HistoryViewImpl(context, navController)

    @Provides
    @FragmentScoped
    fun providePresenter(view: HistoryView, repository: QrModelRepository): HistoryPresenter =
            HistoryPresenterImpl(view, repository)
}
