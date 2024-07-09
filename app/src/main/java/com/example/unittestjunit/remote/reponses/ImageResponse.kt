package com.example.unittestjunit.remote.reponses

data class ImageResponse(val total: Int? = null, val totalHits: Int? = null, val hits: List<ImageInfo>)