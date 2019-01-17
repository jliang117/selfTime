package jimmyliang.selftimer.Model

import android.os.CountDownTimer
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import android.telecom.VideoProfile.isPaused




class CardModel(var name: String, var startTime: Long) {
    //duration, active, startTime

    var active: Boolean = true
    private val remainingTime by lazy {
        startTime
    }
    private lateinit var timer: CountDownTimer

    fun toggle() {
        if (active) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    fun startTimer() {
        timer = object : CountDownTimer(startTime, startTime) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {}
        }
        timer.start()
    }

    fun stopTimer() {
        timer.cancel()
    }

    fun longTimeToString(input:Long):String{
        //returning "%02d:%02d:%02d" format
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(input),
                TimeUnit.MILLISECONDS.toMinutes(input) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(input)),
                TimeUnit.MILLISECONDS.toSeconds((input)) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(input)))
    }


    fun countDownTimer(
            time: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
            tick: Long = 1, tickUnit: TimeUnit = TimeUnit.MILLISECONDS
    ): Observable<Long> {
        val timeNanos = timeUnit.toNanos(time).also { require(it >= 0) }
        val tickNanos = tickUnit.toNanos(tick).also { require(it > 0) }
        val ticks = timeNanos / tickNanos

        return Observable
                .intervalRange(
                        1L, ticks, timeNanos % tickNanos, tickNanos, TimeUnit.NANOSECONDS)
                .map { ticks - it }
                .startWith(ticks)
    }

    fun testTimer(){
        Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())
                .observeOn(Schedulers.io())
    }


}