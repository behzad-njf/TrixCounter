package ir.mrhib.trixcounter.repo

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single
import ir.mrhib.trixcounter.dao.MatchDAO
import ir.mrhib.trixcounter.model.Match
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchesRepository @Inject constructor(private val matchDAO: MatchDAO) {
    fun insertNewMatch(match: Match): Single<Long> {
        return Single.fromCallable { matchDAO.insertNewMatch(match) }
    }

    suspend fun updateMatch(match: Match) =
        matchDAO.updateMatch(match)

    fun getMatches(): LiveData<List<Match>> =
        matchDAO.getMatches()
}