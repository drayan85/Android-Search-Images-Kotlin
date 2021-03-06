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
import com.search.images.domain.model.ImageModel
import com.search.images.domain.model.ImageSearchResponse

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
interface SearchImageDataSource {

    fun searchImages(query: String, page: Int, perSize: Int) : Either<Failure, ImageSearchResponse>

    fun insertImageModelEntity(value: Array<ImageModel?>)
}