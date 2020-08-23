package app.qrscan.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import app.qrscan.data.db.QrDao
import app.qrscan.data.db.QrDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun providePreferences(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): QrDatabase {
        return Room.databaseBuilder(app, QrDatabase::class.java, "qr_scanner-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @Singleton
    fun provideDao(db: QrDatabase): QrDao {
        return db.qrDao()
    }
}
