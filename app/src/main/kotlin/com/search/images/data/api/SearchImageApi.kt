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
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
interface SearchImageApi {

    companion object {
        private const val SEARCH_IMAGES_PATH = "/api/Search/ImageSearchAPI"
    }

    @GET(SEARCH_IMAGES_PATH)
    fun searchImages(@Header("x-rapidapi-host") rapidApiHost: String,
                     @Header("x-rapidapi-key") rapidApiKey: String,
                     @Query("q") query: String,
                     @Query("pageNumber") page: Int,
                     @Query("pageSize") perSize: Int,
                     @Query("autoCorrect") autoCorrect: Boolean,
                     @Query("safeSearch") safeSearch: Boolean): Call<ImageSearchResponse>
}