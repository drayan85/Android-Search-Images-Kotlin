package domain.functional

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

import UnitTest
import com.search.images.domain.exception.Failure.ServerError
import com.search.images.domain.functional.Either.Left
import com.search.images.domain.functional.Either.Right
import com.search.images.domain.functional.*
import org.amshove.kluent.*
import org.junit.Test

class EitherTest : UnitTest() {

    @Test fun `Either Right should return correct type`() {
        val result = Either.Right("beach")

        result shouldBeInstanceOf Either::class.java
        result.isRight shouldBe true
        result.isLeft shouldBe false
        result.fold({},
                { right ->
                    right shouldBeInstanceOf String::class.java
                    right shouldBeEqualTo "beach"
                })
    }

    @Test fun `Either Left should return correct type`() {
        val result = Left("beach")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldBe true
        result.isRight shouldBe false
        result.fold(
                { left ->
                    left shouldBeInstanceOf String::class.java
                    left shouldBeEqualTo "beach"
                }, {})
    }

    @Test fun `Either fold should ignore passed argument if it is Right type`() {
        val success = "Success"
        val result = Right(success).getOrElse("Other")

        result shouldBeEqualTo success
    }

    @Test fun `Either fold should return argument if it is Left type`() {
        val other = "Other"
        val result = Left("Failure").getOrElse(other)

        result shouldBeEqualTo other
    }

    @Test
    fun `given fold is called, when either is Right, applies fnR and returns its result`() {
        val either = Right("Success")
        val result = either.fold({ fail("Shouldn't be executed") }) { 5 }

        result shouldBe 5
        either.isRight shouldBe true
    }

    @Test
    fun `given fold is called, when either is Left, applies fnL and returns its result`() {
        val either = Left(12)

        val foldResult = "Fold Result"
        val result = either.fold({ foldResult }) { fail("Shouldn't be executed") }

        result shouldBe foldResult
        either.isLeft shouldBe true
    }

    @Test
    fun `given flatMap is called, when either is Right, applies function and returns new Either`() {
        val either = Right("Success")

        val result = either.flatMap {
            it shouldBe "Success"
            Left(ServerError)
        }

        result shouldBeEqualTo Left(ServerError)
        result.isLeft shouldBe true
    }

    @Test
    fun `given flatMap is called, when either is Left, doesn't invoke function and returns original Either`() {
        val either = Left(12)

        val result = either.flatMap { Right(20) }

        result.isLeft shouldBe true
        result shouldBeEqualTo either
    }

    @Test
    fun `given onFailure is called, when either is Right, doesn't invoke function and returns original Either`() {
        val success = "Success"
        val either = Right(success)

        val result = either.onFailure { fail("Shouldn't be executed") }

        result shouldBe either
        either.getOrElse("Failure") shouldBe success
    }

    @Test
    fun `given onFailure is called, when either is Left, invokes function with left value and returns original Either`() {
        val either = Left(12)

        var methodCalled = false
        val result = either.onFailure {
            it shouldBe 12
            methodCalled = true
        }

        result shouldBe either
        methodCalled shouldBe true
    }

    @Test
    fun `given onSuccess is called, when either is Right, invokes function with right value and returns original Either`() {
        val success = "Success"
        val either = Right(success)

        var methodCalled = false
        val result = either.onSuccess {
            it shouldBeEqualTo success
            methodCalled = true
        }

        result shouldBe either
        methodCalled shouldBe true
    }

    @Test
    fun `given onSuccess is called, when either is Left, doesn't invoke function and returns original Either`() {
        val either = Left(12)

        val result = either.onSuccess {fail("Shouldn't be executed") }

        result shouldBe either
    }

    @Test
    fun `given map is called, when either is Right, invokes function with right value and returns a new Either`() {
        val success = "Success"
        val resultValue = "Result"
        val either = Right(success)

        val result = either.map {
            it shouldBe success
            resultValue
        }

        result shouldBeEqualTo Right(resultValue)
    }

    @Test
    fun `given map is called, when either is Left, doesn't invoke function and returns original Either`() {
        val either = Left(12)

        val result = either.map { Right(20) }

        result.isLeft shouldBe true
        result shouldBeEqualTo either
    }
}
