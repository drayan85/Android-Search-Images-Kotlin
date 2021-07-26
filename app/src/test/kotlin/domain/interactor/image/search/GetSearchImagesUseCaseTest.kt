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
package search.images.domain.interactor.image.search

import UnitTest
import com.search.images.domain.functional.Either.Right
import com.search.images.domain.interactor.image.search.GetSearchImagesUseCase
import com.search.images.domain.model.ImageModel
import com.search.images.domain.model.ImageSearchResponse
import com.search.images.domain.repository.SearchImagesRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 2.0.0
 * @since 26th of July 2021
 */
class GetSearchImagesUseCaseTest : UnitTest() {

    private lateinit var getSearchImagesUseCase: GetSearchImagesUseCase

    @MockK private lateinit var searchImagesRepository: SearchImagesRepository

    @Before
    fun setUp() {
        getSearchImagesUseCase = GetSearchImagesUseCase(searchImagesRepository)
        every {
            searchImagesRepository.searchImages("beach", 1, 30, true)
        } returns Right(ImageSearchResponse.empty))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getSearchImagesUseCase.run(
            GetSearchImagesUseCase.Params(
                "beach",
                1,
                30,
                true
            )
        ) }

        verify(exactly = 1) { searchImagesRepository.searchImages("beach", 1, 30, true) }
    }
}