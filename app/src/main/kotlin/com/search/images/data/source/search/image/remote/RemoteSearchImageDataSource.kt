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
package com.search.images.data.source.search.image.remote

import com.search.images.BuildConfig
import com.search.images.data.api.SearchImageApi
import com.search.images.data.source.Remote
import com.search.images.data.source.search.image.SearchImageDataSource
import com.search.images.domain.exception.Failure
import com.search.images.domain.functional.Either
import com.search.images.domain.model.ImageModel
import com.search.images.domain.model.ImageSearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
@Remote
class RemoteSearchImageDataSource @Inject constructor(retrofit: Retrofit): SearchImageDataSource {

    private val searchImageApi: SearchImageApi = retrofit.create(SearchImageApi::class.java)

    override fun searchImages(query: String, page: Int, perSize: Int): Either<Failure, ImageSearchResponse> {
        return request(searchImageApi.searchImages(
            BuildConfig.RAPID_API_HOST, BuildConfig.API_KEY, query, page, perSize,
            autoCorrect = true, safeSearch = false), {
            it
        }, ImageSearchResponse.empty)
    }

    override fun insertImageModelEntity(value: Array<ImageModel?>) {
        TODO("No need to implement only need to implement in the DiskDataSource")
    }

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }


}