package ir.mrhib.trixcounter.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import ir.mrhib.trixcounter.model.Game
import androidx.room.Query
import ir.mrhib.trixcounter.model.Match

@Dao
interface MatchDAO {
    @Insert
    fun insertNewMatch(match: Match): Long

    @Update
    suspend fun updateMatch(match: Match)

    @Query("SELECT * FROM tbl_match")
    fun getMatches(): LiveData<List<Match>>
}