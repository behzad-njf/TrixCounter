package ir.mrhib.trixcounter

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.mrhib.clinicbookingsystem.TrexDatabase
import ir.mrhib.trixcounter.dao.GameDAO
import ir.mrhib.trixcounter.dao.MatchDAO
import ir.mrhib.trixcounter.repo.GameRepository
import ir.mrhib.trixcounter.repo.MatchesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): TrexDatabase {
        return Room.databaseBuilder(
            appContext, TrexDatabase::class.java, "trex_database"
        ).fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideMatchDAO(appDatabase: TrexDatabase) = appDatabase.matchDao()

    @Provides
    @Singleton
    fun provideGameDAO(appDatabase: TrexDatabase) = appDatabase.gameDao()


}
