package com.example.newsrecap.network.model

import com.example.newsrecap.domain.model.Source
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceDto(
    val id: String,
    val name: String
)

fun SourceDto.asDomainModel(): Source {
    return Source(
        id = this@asDomainModel.id,
        name = this@asDomainModel.name
    )
}