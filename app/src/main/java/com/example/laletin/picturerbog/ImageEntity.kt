package com.example.laletin.picturerbog

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class ImageEntity {

    @PrimaryKey
    var id: Long = 0

    var title: String? = null

    var urlL: String? = null

    var urlS: String? = null
}