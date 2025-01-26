package dev.gmarques.remotewincontrol.domain

import com.google.gson.Gson

class JsonMapper {
    companion object {

        private val gson = Gson()

        fun <T> fromJson(json: String, obj: Class<T>): T {
            return gson.fromJson(json, obj)
        }

        fun toJson(obj: Any): String {
            return gson.toJson(obj)
        }

    }
}