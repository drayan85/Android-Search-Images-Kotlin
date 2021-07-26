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

import com.search.images.domain.functional.Either
import com.search.images.domain.interactor.image.search.GetSearchImagesUseCase
import com.search.images.domain.model.ImageModel
import com.search.images.domain.model.ImageSearchResponse
import com.search.images.presenter.features.search.SearchImagesViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 2.0.0
 * @since 26th of July 2021
 */
class SearchImagesViewModelTest : AndroidTest() {

    private lateinit var searchImagesViewModel: SearchImagesViewModel

    @MockK
    private lateinit var getSearchImagesUseCase: GetSearchImagesUseCase

    @Before
    fun setUp() {
        searchImagesViewModel = SearchImagesViewModel(getSearchImagesUseCase, )
    }

    @Test
    fun `loading images should update live data`() {
        val imagesList = listOf(ImageModel("Beach 1", 0), ImageModel("Beach 2", 1))
        coEvery { getSearchImagesUseCase.run(any()) } returns Either.Right(ImageSearchResponse.empty)

        searchImagesViewModel.images.observeForever {
            it!!.size shouldEqualTo 2
            it[0].url shouldEqualTo "Beach 1"
            it[0].height shouldEqualTo 0
            it[1].url shouldEqualTo "Beach 2"
            it[1].height shouldEqualTo 1
        }

        runBlocking { searchImagesViewModel.loadImages(forceUpdate = true, "Beach", true) }
    }
}