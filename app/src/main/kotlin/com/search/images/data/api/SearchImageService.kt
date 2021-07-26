/*
 *    Copyright (C) 2021 Paramanathan Ilanthirayan Open Source Project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.search.images.data.api

import com.search.images.domain.model.ImageSearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Ilanthirayan Paramanathan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
@Singleton
class SearchImageService

@Inject
constructor(retrofit: Retrofit) : SearchImageApi {

    private val searchImageApi by lazy { retrofit.create(SearchImageApi::class.java) }

    override fun searchImages(
        rapidApiHost: String,
        rapidApiKey: String,
        query: String,
        page: Int,
        perSize: Int,
        autoCorrect: Boolean,
        safeSearch: Boolean
    ): Call<ImageSearchResponse> = searchImageApi.searchImages(rapidApiHost, rapidApiKey, query, page, perSize, autoCorrect, safeSearch)
}