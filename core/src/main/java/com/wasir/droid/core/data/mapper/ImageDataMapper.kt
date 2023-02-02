package com.wasir.droid.core.data.mapper

import com.wasir.droid.core.data.db.entity.ImageEntity
import com.wasir.droid.core.data.model.ImageData
import com.wasir.droid.core.data.model.ImageResponse

fun ImageData.toEntity(imageData: ImageData): ImageEntity = ImageEntity(
    collections = imageData.collections,
    comments = imageData.comments,
    downloads = imageData.downloads,
    id = imageData.id,
    imageHeight = imageData.imageHeight,
    imageSize = imageData.imageSize,
    imageWidth = imageData.imageWidth,
    largeImageURL = imageData.largeImageURL,
    likes = imageData.likes,
    pageURL = imageData.pageURL,
    previewHeight = imageData.previewHeight,
    previewURL = imageData.previewURL,
    previewWidth = imageData.previewWidth,
    tags = imageData.tags,
    type = imageData.type,
    user = imageData.user,
    userImageURL = imageData.userImageURL,
    userId = imageData.user_id,
    views = imageData.views,
    webformatHeight = imageData.webformatHeight,
    webformatURL = imageData.webformatURL,
    webformatWidth = imageData.webformatWidth
)

fun ImageEntity.toImageData(imageEntity: ImageEntity): ImageData = ImageData(
    collections = imageEntity.collections,
    comments = imageEntity.comments,
    downloads = imageEntity.downloads,
    id = imageEntity.id,
    imageHeight = imageEntity.imageHeight,
    imageSize = imageEntity.imageSize,
    imageWidth = imageEntity.imageWidth,
    largeImageURL = imageEntity.largeImageURL,
    likes = imageEntity.likes,
    pageURL = imageEntity.pageURL,
    previewHeight = imageEntity.previewHeight,
    previewURL = imageEntity.previewURL,
    previewWidth = imageEntity.previewWidth,
    tags = imageEntity.tags,
    type = imageEntity.type,
    user = imageEntity.user,
    userImageURL = imageEntity.userImageURL,
    user_id = imageEntity.userId,
    views = imageEntity.views,
    webformatHeight = imageEntity.webformatHeight,
    webformatURL = imageEntity.webformatURL,
    webformatWidth = imageEntity.webformatWidth
)

fun ImageData.toDomain(imageList: List<ImageData>): ImageResponse = ImageResponse(
    imageLists = imageList
)