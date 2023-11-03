package com.noureldin.cinemaxplay.Domain

import com.google.gson.annotations.SerializedName


data class ListFilm (

  @SerializedName("data"     ) var data     : ArrayList<Data> = arrayListOf(),
  @SerializedName("metadata" ) var metadata : Metadata?       = Metadata()

)