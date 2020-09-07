package app.qrscan.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import app.qrscan.data.repository.QrModelRepository
import app.qrscan.ui.scanner.presenter.ScannerPresenter
import app.qrscan.ui.scanner.presenter.ScannerPresenterImpl
import app.qrscan.ui.scanner.view.ScannerView
import app.qrscan.ui.scanner.view.ScannerViewImpl
import app.qrscan.util.FileManager
import app.qrscan.util.PermissionChecker
import app.qrscan.util.SoundVibrationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object ScannerModule {

    @Provides
    @FragmentScoped
    fun provideView(activity: Activity, navController: NavController): ScannerView =
            ScannerViewImpl(activity, navController)

    @Provides
    @FragmentScoped
    fun providePresenter(
            context: Context,
            scannerView: ScannerView,
            preferences: SharedPreferences,
            repository: QrModelRepository
    ): ScannerPresenter {
        val fileManager = FileManager(context)
        val soundVibrationUtil = SoundVibrationUtil(context)
        val permissionChecker = PermissionChecker(context)
        return ScannerPresenterImpl(scannerView, fileManager, soundVibrationUtil, preferences, repository, permissionChecker)
    }
}
