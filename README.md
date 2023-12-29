[![N|Solid][logo]][site]
### Simple Android Application
-----------------------------
* - * - *  * - * - * 
-----------------------------

## Overview 🚀

Welcome to the PassEntry Android application repository! This project is designed to showcase a simple Android application integrated with a mock HTTP API.

## Application Features 📱

### 1. Login Page 🔐

The application features a login page that accepts a username and password. Upon submission, the credentials are validated against the `/login` endpoint of the included mock API.

### 2. Tap History Page 🔄

Authenticated users can access the tap history page, displaying a list of pass taps retrieved from the `/taps` endpoint of the mock API.

## Preview 📱
  ### APK file📥
  You can download the precompiled APK file of the debug version from [HERE][apk].

  ### Images 🖼️
  <img src="https://github.com/hexfa/Files/blob/main/PassEntry/Preview/1.jpg?raw=true" height="649" width="300" alt="PassEntry App hexfa">         <img src="https://github.com/hexfa/Files/blob/main/PassEntry/Preview/2.jpg?raw=true" height="649" width="300" alt="PassEntry App amir">
  
  ### Video 📽️
  <img src="https://github.com/hexfa/Files/blob/main/PassEntry/Preview/demo.gif" height="649" width="300" alt="PassEntry App dehdarian fallah">

## Documentation 📜

- ### Online Document
    **`For looking at the last document code version of this project, you can generate it by`** [Dokka section][dokka].
  
    **`Also, you can see the first document version of the project on this link`** [Documentation][doc].

- ### Dokka
  - _I recommend Html document type_
  - _I recommend to use the JAVA JDK 18 and set it in your JAVA_HOME environment. after putting it in the system needs a restart_

    - ###### Dokka terminal commands
      | Type | Syntax |
      | ------ | ------ |
      | Html | ./gradlew.bat dokkaHtml |
      | Java doc | ./gradlew.bat dokkaJavadoc |
      | Gfm | ./gradlew.bat dokkaGfm |

    - ###### Output Location
      `app -> build -> dokka`

## Mock API Integration 🤖

**🌐 I've hosted the mock API on an online server accessible at `http://154.62.108.207:3000`. This server will be available until December 31, 2023. You can use this IP address for API integration. 🌐**

Alternatively, if you prefer to run the mock API locally, execute the following command:

```sh
docker-compose up --build
```

This will launch the mock HTTP API, accessible at `http://localhost:3000`.

## Mock API Endpoints 🌐

### 1. POST /login

#### Description 🚪

Authenticate a user and generate an API token.

#### Request 📤

- Method: `POST`
- URL: `/login`
- Headers:
  - Content-Type: `application/json`

##### Request Body

```json
{
  "username": "hello@passentry.com",
  "password": "securepass"
}
```

#### Success Response ✅

If correct credentials are provided, the response body contains an API token for use with the `/taps` endpoint.

- Status Code: `200 OK`

##### Success Response Body

```json
{
  "api-token": "your-api-token"
}
```

#### Error Response ❌

If incorrect credentials are provided, no token is returned.

- Status Code: `401 Unauthorized`

##### Error Response Body

```json
{
  "error": "Unauthorized"
}
```

### 2. GET /taps

#### Description 📊

Retrieve a collection of pass tap history data.

**Note: Include the bearer token returned by the `/login` endpoint in the request header for successful authentication.**

#### Request 📥

- Method: `GET`
- URL: `/taps`
- Headers:
  - Authorization: `Bearer your-api-token`

#### Success Response ✅

- Status Code: `200 OK`
- Body:

```json
[
  {
    "tappedAt": "2023-12-22T12:34:56.789Z",
    "status": "success",
    "readerId": "someReaderId"
  },
  {
    "tappedAt": "2023-12-23T12:34:56.789Z",
    "status": "fail",
    "readerId": "someReaderId"
  }
  // ... more data
]
```

#### Error Response ❌

If the authorization token is missing or invalid, no data is returned.

- Status Code: `401 Unauthorized`

##### Error Response Body

```json
{
  "error": "Unauthorized"
}
```

## Technologies and Libraries 🛠️

The PassEntry Android application is built using the following technologies and libraries:

- [Kotlin][kotlin]: A modern and concise programming language for Android development.

- [Hilt][hilt]: A dependency injection library for Android that simplifies the process of injecting dependencies into Android components.

- [LiveData][live-data]: A lifecycle-aware data holder class in Android Jetpack that allows data to be observed for changes and provides automatic updates to the UI.

- [Dokka][dokka-site]: A documentation generation tool for Kotlin and Java that generates API documentation in various formats, including HTML and Markdown.

- [Junit][junit]: A popular unit testing framework for Java and Kotlin that provides a set of annotations and assertions for writing automated tests.

- [Mockito][mockito]: A mocking framework for unit testing in Java and Kotlin that allows the creation of mock objects to simulate dependencies and behavior during testing.

- [Room][room]: An SQLite database library for Android that provides an abstraction layer over SQLite and enables efficient database operations using annotations and generated code.

- [Moshi][moshi]: A modern JSON parsing and serialization library for Kotlin and Java that makes it easy to convert JSON data to Kotlin/Java objects and vice versa.

- [Gson][gson]: Gson is a powerful JSON library that simplifies JSON parsing and serialization in Android through a flexible reflection-based approach.

- [RxJava][rx-java]:RxJava is a Java VM library that provides a reactive programming model for composing asynchronous and event-based programs using observable sequences.

- [Retrofit][retrofit]:Retrofit is a type-safe HTTP client for Android and Java, making it easier to consume RESTful web services.



[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen.)

   [logo]: <https://passentry.com/static/media/logoDarkMode.c064f74f3cd5b4ad0db200660f294052.svg> 
   [site]: <https://github.com/hexfa/PassEntry>
   [apk]: <https://github.com/hexfa/Files/raw/main/PassEntry/apk/PassEntry-Debug.apk>
   [dokka]: <#dokka>
   [doc]: <https://hexfa.com/my-git-doc/passentry>
   [hilt]: <https://dagger.dev/hilt/>
   [coroutine]: <https://kotlinlang.org/docs/coroutines-guide.html>
   [live-data]: <https://developer.android.com/topic/libraries/architecture/livedata>
   [dokka-site]: <https://kotlinlang.org/docs/dokka-gradle.html>
   [kotlin]: <https://kotlinlang.org/>
   [junit]: <https://junit.org/>
   [room]: <https://developer.android.com/training/data-storage/room>
   [moshi]: <https://github.com/square/moshi>
   [gson]: <https://github.com/google/gson>
   [mockito]: <https://site.mockito.org/>
   [rx-java]: <https://github.com/ReactiveX/RxJava>
   [retrofit]: <https://square.github.io/retrofit/>
