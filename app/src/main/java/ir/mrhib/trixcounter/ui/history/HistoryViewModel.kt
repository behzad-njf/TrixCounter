package ir.mrhib.trixcounter.ui.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mrhib.trixcounter.repo.MatchesRepository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(matchesRepository: MatchesRepository) : ViewModel() {
    val allMatches = matchesRepository.getMatches()
}