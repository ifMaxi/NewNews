# New News

News app 🗞️.

## Description

Simple application written in Kotlin and Jetpack compose. Uses the API [The Guardian](https://open-platform.theguardian.com/) to get the content.

> [!IMPORTANT]
> In order to have access to the use of the application you need an API key or Token that you can obtain by registering on the website [The Guardian](https://open-platform.theguardian.com/).
>
> Once you have obtained the key you will need to place it in your ***"local.properties"*** file in the root folder of the project and name the variable as ***"api-key"***, the rest of the configurations in the build gradle
> file have already been done. Then rebuild the project and you can start the app from the emulator.

## Architecture
The type of architecture used for this project was MVVM(Model-View-ViewModel).

This is divided into the:

- Model: Which represents the data and business logic
- View: Which represents the UI
- ViewModel: Which represents the bridge between the View and the Model

![Mvvm arch](https://github.com/user-attachments/assets/2c913a4f-6b1b-4f55-aa84-3d0cd5382bcf)

## Language and libraries

- **Kotlin**
  - Serialization
  - Coroutines
  - Kps
- **Android**
  - Intents
- **Jetpack Libraries**
  - Compose
  - Material 3
  - Hilt
  - ViewModel
  - Room Data base
  - Paging 3
    - Remote Mediator
- **Other libraries**
  - Retrofit
  - OkHttp
  - Lottie
