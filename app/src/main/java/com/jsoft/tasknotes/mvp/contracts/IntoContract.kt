package com.jsoft.tasknotes.mvp.contracts

import com.jsoft.tasknotes.data.models.IntroData


interface IntoContract {
    interface Model {
        fun getData(): List<IntroData>
    }

    interface View {
        fun moveToNextScreen(data:List<IntroData>)
    }

    interface Presenter {
        fun clickNext()
    }
}