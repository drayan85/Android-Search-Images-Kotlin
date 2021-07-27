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
package com.search.images.data.source.search.image

import com.search.images.domain.exception.Failure
import com.search.images.domain.functional.Either
import com.search.images.data.source.Local
import com.search.images.data.source.Remote
import com.search.images.domain.model.ImageSearchResponse
import com.search.images.domain.repository.SearchImagesRepository
import javax.inject.Inject

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
class SearchImagesRepositoryImpl
@Inject
constructor(
    @Local private val diskSearchImageDataSource: SearchImageDataSource,
    @Remote private val remoteSearchImageDataSource: SearchImageDataSource
) : SearchImagesRepository {

    override fun searchImages(
        query: String,
        page: Int,
        perSize: Int,
        isInternetAvailable: Boolean
    ): Either<Failure, ImageSearchResponse> {
        return when (isInternetAvailable) {
            true -> {
                val either = remoteSearchImageDataSource.searchImages(query, page, perSize)
                either.isRight.let {
                    either.fold({}, { right ->
                        diskSearchImageDataSource.insertImageModelEntity(right.value)
                    })
                }
                either
            }
            false -> diskSearchImageDataSource.searchImages(query, page, perSize)
        }
    }


}

