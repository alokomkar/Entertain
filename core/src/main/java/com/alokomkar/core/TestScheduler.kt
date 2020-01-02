package com.alokomkar.core

import androidx.annotation.VisibleForTesting
import com.alokomkar.core.networking.Scheduler
import io.reactivex.schedulers.Schedulers

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
class TestScheduler : Scheduler {

    override fun mainThread(): io.reactivex.Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): io.reactivex.Scheduler {
        return Schedulers.trampoline()
    }
}