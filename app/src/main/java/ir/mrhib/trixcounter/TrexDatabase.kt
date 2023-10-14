package ir.mrhib.clinicbookingsystem

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.mrhib.trixcounter.dao.GameDAO
import ir.mrhib.trixcounter.dao.MatchDAO
import ir.mrhib.trixcounter.model.Game
import ir.mrhib.trixcounter.model.Match

@Database(
    entities = [Game::class, Match::class],
    version = 1,
    exportSchema = false
)
abstract class TrexDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDAO
    abstract fun matchDao(): MatchDAO

}
