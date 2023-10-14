package ir.mrhib.trixcounter.ui.mainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mrhib.trixcounter.model.Match
import ir.mrhib.trixcounter.repo.MatchesRepository
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val matchesRepository: MatchesRepository): ViewModel() {

    private val disposable = CompositeDisposable()
    var status = MutableLiveData<Boolean?>()
    var insertId = -1L
    var playersNames: ArrayList<String> = ArrayList()

    fun createNewGame(
        player1Name: String, player2Name: String, player3Name: String, player4Name: String
    ) {
        disposable.add(matchesRepository.insertNewMatch(
            Match(
                player1 = player1Name,
                player2 = player2Name,
                player3 = player3Name,
                player4 = player4Name
            )
        ).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Long>() {
                override fun onSuccess(t: Long) {
                    status.postValue(true)
                    insertId = t
                    playersNames.clear()
                    playersNames.add(player1Name)
                    playersNames.add(player2Name)
                    playersNames.add(player3Name)
                    playersNames.add(player4Name)
                }

                override fun onError(e: Throwable) {
                    status.postValue(false)
                }
            }))
    }
}