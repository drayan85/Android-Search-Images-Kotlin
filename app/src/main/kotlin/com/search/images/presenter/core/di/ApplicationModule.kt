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
package com.search.images.presenter.core.di

import android.content.Context
import com.search.images.BuildConfig
import com.search.images.data.database.AppDataBase
import com.search.images.data.source.Local
import com.search.images.data.source.Remote
import com.search.images.data.source.search.image.SearchImageDataSource
import com.search.images.data.source.search.image.SearchImagesRepositoryImpl
import com.search.images.data.source.search.image.local.DiskSearchImageDataSource
import com.search.images.data.source.search.image.remote.RemoteSearchImageDataSource
import com.search.images.domain.repository.SearchImagesRepository
import com.search.images.presenter.utils.JsonFormatHttLogging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 21st of July 2021
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(JsonFormatHttLogging())
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }

    @Provides
    @Local
    fun provideDiskSearchDataSource(appDataBase: AppDataBase): SearchImageDataSource = DiskSearchImageDataSource(appDataBase)

    @Provides
    @Remote
    fun provideRemoteSearchDataSource(retrofit: Retrofit): SearchImageDataSource = RemoteSearchImageDataSource(retrofit)

    @Provides
    fun providesSearchImageRepository(@Local diskSearchImageDataSource: SearchImageDataSource,
                                      @Remote remoteSearchImageDataSource: SearchImageDataSource
    ): SearchImagesRepository = SearchImagesRepositoryImpl(diskSearchImageDataSource, remoteSearchImageDataSource)
}