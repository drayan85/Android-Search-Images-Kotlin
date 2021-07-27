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

package com.search.images.data.source.search.image.local

import com.google.gson.Gson
import com.search.images.data.database.AppDataBase
import com.search.images.data.database.entity.ImageModelEntity
import com.search.images.data.source.search.image.SearchImageDataSource
import com.search.images.domain.exception.Failure
import com.search.images.domain.functional.Either
import com.search.images.domain.model.ImageModel
import com.search.images.domain.model.ImageSearchResponse
import com.search.images.domain.model.Provider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
class DiskSearchImageDataSource @Inject constructor(private val appDataBase: AppDataBase):
    SearchImageDataSource {


    override fun searchImages(query: String, page: Int, perSize: Int): Either<Failure, ImageSearchResponse> {
        return runBlocking {Either.Right(getSearchImagesBasedOnQuery(query, page, perSize))}
    }

    private suspend fun getSearchImagesBasedOnQuery (query: String, page: Int, perSize: Int) =
        withContext(Dispatchers.IO) {
            val offset: Int = (page - 1) * perSize
            val total: Int = appDataBase.searchImageDao.getTotalNumberOfItemsForGivenSearchQuery(query)
            val imageModelEntities: Array<ImageModelEntity> = appDataBase.searchImageDao.getPaginatedImagesBasedOnQuery(perSize, offset, query)
            val imageModels: Array<ImageModel?> = arrayOfNulls(perSize)
            var index = 0
            val gson = Gson()
            for (entity in imageModelEntities) {
                imageModels[index++] = ImageModel(
                    entity.url,
                    entity.height,
                    entity.width,
                    entity.thumbnail,
                    entity.thumbnailHeight,
                    entity.thumbnailWidth,
                    entity.name,
                    entity.title,
                    gson.fromJson(entity.provider, Provider::class.java),
                    entity.imageWebSearchUrl,
                    entity.webpageUrl)
            }
            ImageSearchResponse("_images", total, imageModels)
        }

    override fun insertImageModelEntity(value: Array<ImageModel?>) {
        runBlocking {
            val gson = Gson()
            for (imageModel in value) {
                appDataBase.searchImageDao.insertImageModelEntities(ImageModelEntity(
                    imageModel!!.url,
                    imageModel.height,
                    imageModel.width,
                    imageModel.thumbnail,
                    imageModel.thumbnailHeight,
                    imageModel.thumbnailWidth,
                    imageModel.name,
                    imageModel.title,
                    gson.toJson(imageModel.provider),
                    imageModel.imageWebSearchUrl,
                    imageModel.webpageUrl
                ))
            }
        }
    }
}