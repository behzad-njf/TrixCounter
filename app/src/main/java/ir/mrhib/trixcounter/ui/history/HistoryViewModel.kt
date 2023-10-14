package ir.mrhib.trixcounter.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mrhib.trixcounter.model.Match
import ir.mrhib.trixcounter.repo.MatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val matchesRepository: MatchesRepository) :
    ViewModel() {
    val allMatches = matchesRepository.getMatches()

    fun deleteMatch(match: Match) {
        viewModelScope.launch(Dispatchers.IO) {
            matchesRepository.deleteMatch(match)
        }
    }
}