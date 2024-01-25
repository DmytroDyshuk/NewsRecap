package com.example.newsrecap.domain.model

enum class Sources {
    ALL_NEWS, TIME, THE_WALL_STREET_JOURNAL, THE_WASHINGTON_POST, BBC_NEWS,
    ABC_NEWS, CNN_NEWS, FOX_NEWS, IGN, NATIONAL_GEOGRAPHIC;

    val source: String
        get() = when (this) {
            ALL_NEWS -> "all-news"
            TIME -> "time"
            THE_WALL_STREET_JOURNAL -> "the-wall-street-journal"
            THE_WASHINGTON_POST -> "the-washington-post"
            BBC_NEWS -> "bbc-news"
            ABC_NEWS -> "abc-news"
            CNN_NEWS -> "cnn"
            FOX_NEWS -> "fox-news"
            IGN -> "ign"
            NATIONAL_GEOGRAPHIC -> "national-geographic"
        }
}