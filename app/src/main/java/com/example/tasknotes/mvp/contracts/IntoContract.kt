package com.example.tasknotes.mvp.contracts

import com.example.tasknotes.data.models.IntroData


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