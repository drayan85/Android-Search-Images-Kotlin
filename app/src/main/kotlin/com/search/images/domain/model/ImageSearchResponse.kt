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
package com.search.images.domain.model

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
data class ImageSearchResponse(var _type: String, val totalCount: Int, val value: Array<ImageModel?>){

    companion object {
        val empty = ImageSearchResponse("", 0, arrayOf())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageSearchResponse

        if (_type != other._type) return false
        if (totalCount != other.totalCount) return false
        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _type.hashCode()
        result = 31 * result + totalCount
        result = 31 * result + value.contentHashCode()
        return result
    }


}