/**
 * Copyright (C) 2020 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package search.images.domain.interactor

import AndroidTest
import com.search.images.domain.functional.Either.Right
import com.search.images.domain.interactor.UseCase
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class UseCaseTest : AndroidTest() {

    private val useCase = MyUseCase()

    @Test fun `running use case should return 'Either' of use case type`() {
        val params = MyParams(TYPE_PARAM)
        val result = runBlocking { useCase.run(params) }

        result shouldBeEqualTo Right(MyType(TYPE_TEST))
    }

//    @Test fun `should return correct data when executing use case`() {
//        var result: Either<Failure, MyType>? = null
//
//        val params = MyParams("TestParam")
//        val onResult = { myResult: Either<Failure, MyType> -> result = myResult }
//
//        runBlocking { useCase(params, onResult) }
//
//        result shouldBeEqualTo Right(MyType(TYPE_TEST))
//    }

    data class MyType(val name: String)
    data class MyParams(val name: String)

    private inner class MyUseCase : UseCase<MyType, MyParams>() {
        override suspend fun run(params: MyParams) = Right(MyType(TYPE_TEST))
    }

    companion object {
        private const val TYPE_TEST = "Test"
        private const val TYPE_PARAM = "ParamTest"
    }
}
