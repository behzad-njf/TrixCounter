package ir.mrhib.trixcounter.repo

import androidx.lifecycle.LiveData
import ir.mrhib.trixcounter.dao.GameDAO
import ir.mrhib.trixcounter.model.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(private val gameDAO: GameDAO) {
    suspend fun insertNewGame(game: Game) =
        gameDAO.insertNewGame(game)

    suspend fun updateGame(game: Game) =
        gameDAO.updateGame(game)

    suspend fun deleteGame(game: Game) = gameDAO.deleteGame(game)

    fun getAllGames(matchId: Int): LiveData<List<Game>> =
        gameDAO.getAllGames(matchId)
}