package com.noureldin.cinemaxplay.Domain

import com.google.gson.annotations.SerializedName


class Genres {
  private var genres: List<GenresItem>? = null

  fun getGenres(): List<GenresItem>? {
    return genres
  }

  fun setGenres(genres: List<GenresItem>?) {
    this.genres = genres
  }
}