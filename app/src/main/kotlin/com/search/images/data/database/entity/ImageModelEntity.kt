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
package com.search.images.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
@Entity(tableName = "tbl_image", indices = [Index(value = [IImageModel.Columns.URL], unique = true)])
data class ImageModelEntity(
    var url: String,
    var height: Int,
    var width: Int,
    var thumbnail: String,
    var thumbnailHeight: Int,
    var thumbnailWidth: Int,
    var name: String,
    var title: String?,
    var provider: String,
    var imageWebSearchUrl: String,
    var webpageUrl: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "base_id") var id: Long = 0
}
