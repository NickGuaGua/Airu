# Airu
Airu demonstrates modern Android development with Hilt, Coroutines, Flow and Jetpack (ViewModel, Compose) based on MVVM architecture.

<img src="/preview/preview.gif" align="right" width="300"/>

## Download
Go to [Release](https://github.com/NickGuaGua/Airu/releases) to download the latest APK.

## Build Up

- [Apply API key](https://data.epa.gov.tw/paradigm)
- Add your API key in local.properties.
  ```
  EpaApiKey="your API key here"
  ```

## Tech stack & Open-source libraries

- [Kotlin](https://kotlinlang.org/)
  based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- [Compose](https://developer.android.com/jetpack/compose) for building up all screens.
- Jetpack
    - ViewModel: UI state holder. Allows data to survive configuration changes such as screen rotations.
- Architecture
    - MVVM Architecture (View - ViewModel - Model)
    - Clean Architecture (UI - Domain - Data)
    - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.