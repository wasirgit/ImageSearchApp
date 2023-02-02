package com.wasir.droid.core.data.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("hits")
    var imageLists: List<ImageData>?=null,
    var total: Int=0,
    var totalHits: Int=0
)