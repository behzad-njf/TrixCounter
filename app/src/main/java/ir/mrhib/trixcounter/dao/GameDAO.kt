package ir.mrhib.trixcounter.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import ir.mrhib.trixcounter.model.Game
import androidx.room.Query

@Dao
interface GameDAO {
    @Insert
    suspend fun insertNewGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("SELECT * FROM tbl_games WHERE match_id= :matchId")
    fun getAllGames(matchId: Int): LiveData<List<Game>>
}