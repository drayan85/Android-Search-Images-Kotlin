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
package com.search.images.domain.interactor.image.search

import com.search.images.domain.interactor.UseCase
import com.search.images.domain.interactor.image.search.GetSearchImagesUseCase.Params
import com.search.images.domain.model.ImageSearchResponse
import com.search.images.domain.repository.SearchImagesRepository
import javax.inject.Inject

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
class GetSearchImagesUseCase
@Inject
constructor(private val searchImagesRepository: SearchImagesRepository) : UseCase<ImageSearchResponse, Params> (){

    override suspend fun run(params: Params) = searchImagesRepository.searchImages(params.query, params.page, params.perPage, params.isInternetAvailable)

    data class Params(val query: String, val page: Int, val perPage: Int, val isInternetAvailable: Boolean)
}