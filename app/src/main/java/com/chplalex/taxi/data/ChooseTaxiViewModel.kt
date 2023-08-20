package com.chplalex.taxi.data

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.Random
import java.util.concurrent.TimeUnit

class ChooseTaxiViewModel(private val pickUpPoint: String, private val destinationPoint: String) : ViewModel() {

    private val random = Random()
    private val subject = BehaviorSubject.create<List<TaxiUiModel>>()
    private var disposable: Disposable? = null

    fun dispose() {
        disposable?.dispose()
    }

    fun getData(): BehaviorSubject<List<TaxiUiModel>> {

        println("Imitate data request for the rout from $pickUpPoint to $destinationPoint")

        disposable = Observable.interval(DELAY_IN_SECONDS, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                subject.onNext(getItems())
            }

        return subject.also { it.onNext(getItems()) }
    }

    private fun getItems(): List<TaxiUiModel> {
        return List(getListLength() + LIST_LENGTH_MIN) {
            val etaTime = getRandomEtaTime()
            TaxiUiModel(
                taxiId = getRandomTaxiId(),
                etaString = getEtaString(etaTime),
                etaTime = etaTime,
            )
        }.sortedBy {
            it.etaTime
        }
    }

    private fun getListLength(): Int {
        return random.nextInt(LIST_LENGTH_BOUND - LIST_LENGTH_MIN)
    }

    private fun getRandomTaxiId(): Int {
        return random.nextInt(TAXI_ID_BOUND)
    }

    private fun getRandomEtaTime(): Int {
        return random.nextInt(MINUTES_BOUND)
    }

    private fun getEtaString(etaTime: Int): String {
        val hours = etaTime / 60
        val minutes = etaTime % 60
        return if (hours == 0) {
            if (minutes == 0) "now" else "${minutes}m"
        } else {
            "${hours}h ${minutes}m"
        }
    }

    companion object {
        private const val DELAY_IN_SECONDS = 5L
        private const val TAXI_ID_BOUND = 3
        private const val MINUTES_BOUND = 180
        private const val LIST_LENGTH_BOUND = 8
        private const val LIST_LENGTH_MIN = 2
    }
}