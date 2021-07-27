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
package com.search.images.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.search.images.data.database.entity.IImageModel
import com.search.images.data.database.entity.ImageModelEntity
import io.reactivex.Maybe

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
@Dao
abstract class SearchImageDao {

    @Query("SELECT * FROM tbl_image WHERE " + IImageModel.Columns.TITLE + " LIKE '%' || :query || '%'" + " LIMIT :per_page OFFSET :offSet")
    abstract suspend fun getPaginatedImagesBasedOnQuery(per_page: Int, offSet: Int, query: String): Array<ImageModelEntity>

    @Query("SELECT COUNT(*) FROM tbl_image WHERE " + IImageModel.Columns.TITLE + " LIKE '%' || :query || '%'")
    abstract suspend fun getTotalNumberOfItemsForGivenSearchQuery(query: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertImageModelEntities(vararg entity: ImageModelEntity)
}