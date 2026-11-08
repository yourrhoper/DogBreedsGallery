package com.example.dogbreedsgallery.data.remote // модель данных, в которую Retrofit будет преобразовывать JSON-ответ сервера

import com.squareup.moshi.Json

data class BreedDto( // описывает одну породу собаки
    val id: Int,
    val name: String,
    val temperament: String? = null,
    val origin: String? = null,
    @Json(name = "life_span") // связывает название поля из JSON с переменной lifeSpan в Kotlin
    val lifeSpan: String? = null,
    val description: String? = null,
    val image: BreedImageDto? = null
)

data class BreedImageDto( // содержит информацию об изображении породы
    val id: String? = null,
    val url: String? = null
)

data class DogImageDto( // получение дополнительных фотографий
    val id: String,
    val url: String
)
