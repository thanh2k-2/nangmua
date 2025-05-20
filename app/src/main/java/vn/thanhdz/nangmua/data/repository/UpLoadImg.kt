package vn.thanhdz.nangmua.data.repository

import vn.thanhdz.nangmua.R


class UpLoadImg {
    fun load(icon: Int): Int {
        return when(icon) {
            1, 2, 3, 4, 5 -> R.drawable.troi_nang
            6, 7, 8, 11 -> R.drawable.nhieu_may
            12, 13, 14, 15, 16, 17, 18, 19, 25, 29, 39, 40, 41, 42 -> R.drawable.troi_mua
            else -> {
                R.drawable.troi_toi
            }
        }
    }

}