package com.example.tasknotes.mvp.repositories

import android.graphics.Color
import com.example.tasknotes.R
import com.example.tasknotes.mvp.contracts.IntoContract
import com.example.tasknotes.data.models.IntroData

class IntroRepository : IntoContract.Model {
    var list: List<IntroData> = listOf(
        IntroData(
            Color.parseColor(
                "#A31A6D"
            ),
            "Siz foydalangan umumiy ishlar ro'yhati",
            R.drawable.pager3,
            "Ushbu ilova sizning qurilmangizning barcha o'rnatilgan kalendarlari va vazifalarini sinxronlashtiradi va namoyish qiladi "
        ),
        IntroData(
            Color.parseColor(
                "#7A1DC6"
            ),
            "Ish grafikingizning to'liq nazorat ro'yhati",
            R.drawable.pager2,
            " Qilinadigan ishlaringizni ushbu ilovaga saqlash orqali vaqtingizdan unumli foydalaning"
        ),
        IntroData(
            Color.parseColor(
                "#A336C5"
            ),
            "Kuningizni bir necha kunga rejalashtiring",
            R.drawable.pager1,
            "Ushbu ilova sizga  muhim ishlaringizni amalga oshiringizni e'tibor qaratishga yordam beradi"
        )
    )

    override fun getData() = list

}