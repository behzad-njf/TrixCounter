package ir.mrhib.trixcounter.ui.tableActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mrhib.trixcounter.model.Game
import ir.mrhib.trixcounter.repo.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TableActivityViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    var matchId = MutableLiveData<Int>()
    val allGames: LiveData<List<Game>>

    init {
        allGames = matchId.switchMap {
            gameRepository.getAllGames(it)
        }
    }


    fun insertNewGame(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.insertNewGame(game)
        }
    }

    fun deleteGame(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.deleteGame(game)
        }
    }

}