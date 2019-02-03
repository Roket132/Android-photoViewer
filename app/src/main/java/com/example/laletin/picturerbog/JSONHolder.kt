package com.example.laletin.picturerbog

class JSONHolder {
    companion object {
        private var json: JSONImages? = null
    }

    fun setJSON(json_ : JSONImages) {
        json = json_
    }

    fun get(): JSONImages? {
        return json
    }
}
