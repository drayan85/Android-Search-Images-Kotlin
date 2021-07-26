# Android-Search-Images-Kotlin
This app is built using https://rapidapi.com/contextualwebsearch/api/web-search API

This repository contains a detailed Image Search app that implementsClean Architecture with MVVM designpattern using Hilt, Room, RxJava2, OkHttp3, Glide and Retrofit2

<p align="center">
  <img src="https://user-images.githubusercontent.com/4456232/126933529-f306c2c1-cc9e-4b62-86b9-c1454c29b517.png" width="250">
  <img src="https://user-images.githubusercontent.com/4456232/126933535-ff90508b-74dc-4d9b-aca7-851fcbf90407.png" width="250">
  <img src="https://user-images.githubusercontent.com/4456232/126933538-9b1e79b6-fa04-4e96-8a3e-9e078d66a213.png" width="250">
</p>

## - How to build on your environment
You will need to create an account from [RapidAPI](https://rapidapi.com/) to obtain the API key as per 
instructions given in https://docs.rapidapi.com/docs.

In data layer `RemoteSearchImageDataSource.kt`
```
return searchImageApi.searchImages(
            BuildConfig.RAPID_API_HOST, BuildConfig.API_KEY, query, page, perSize,
            autoCorrect = true, safeSearch = false)
```
**Option 1**
============
Replace `BuildConfig.API_KEY` with your key you have obtain from RapidAPI

**Option 2**
============

On Your MacBook Open the Terminal

Put this into ``` ~/.gradle/gradle.properties```

``` RAPID_API_KEY={api_key_received_from_rapidapi_com}```

Basic layers overview
---------------------
- Modular approch followed
- It is heavily implemented by following standard clean architecture principle.
- Unit testing written for domain and data layers.
- Offline capability in progress.
- [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID) priciple followed for more understandable, flexible and maintainable.

App layers/module:
- **domain** - Would execute business logic which is independent of any layer and is just a pure kotlin package with no android specific dependency.The domain layer responsibility is to simply contain the UseCase instance used to retrieve data from the Data layer and pass it onto the Presentation layer. 

- **data** - Would dispense the required data for the application to the domain layer by implementing interface exposed by the domain. The Data layer is our access point to external data layers and is used to fetch data from multiple sources (the cache and remote in our case).

- **presenter** - This layer's responsibility is to handle the presentation of the User Interface, but at the same time knows nothing about the user interface itself. This layer has no dependence on the Android Framework, it is a pure Kotlin module. Each ViewModel class that is created implements the ViewModel class found within the Architecture components library. This ViewModel can then be used by the UI layer to communicate with UseCases and retrieve data.

### Library reference resources:
1. [RxJava2](https://github.com/ReactiveX/RxJava) - RxJava is a library is used for asynchronous requests
2. [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - For dependency injection which help
3. [Retrofit2](https://square.github.io/retrofit/) - Invoke RESTful web services
4. [OkHttp3](https://square.github.io/okhttp/) - Sending and receive HTTP-based network requests
5. [Gson](https://github.com/google/gson) - Convert Java object => JSON and JSON => Java object
6. [Mockito](http://site.mockito.org) - Used to mock interfaces which can be used for Unit Testing

### License
```
   Copyright (C) 2021 Paramanathan Ilanthirayan

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
