
# ComicApp


![image](https://github.com/user-attachments/assets/16ea3f0a-5060-4778-88b7-76dc13e4471a)



![image](https://github.com/user-attachments/assets/ca4edc77-fcab-4dc7-8e53-207e15ee83e2)


A modern Android application for browsing and viewing comic details, built with **Kotlin** and **Jetpack Compose**. This project showcases current industry best practices, including a clean architecture, reactive UI, and robust testing strategies.

-----

## 🌟 Features

  * **Browse Comics:** View a list of available comics.
  * **Comic Details:** See detailed information about each comic, including description, page count, and creators.
  * **Error Handling:** Graceful handling and display of network or data loading errors.
  * **Loading Indicators:** User-friendly loading states for data fetching.
  * **Responsive UI:** Built with Jetpack Compose for a modern, declarative, and adaptive user interface.
  * **Offline Support (Optional/Future):** Implement data caching for offline access. *(Remove if not planned)*

-----

## 🛠️ Tech Stack

The `ComicApp` is built using the following core technologies and libraries:

  * **Kotlin:** A modern, concise, and safe programming language for Android development.
  * **Jetpack Compose:** Android's modern toolkit for building native UI.
  * **Android Architecture Components:**
      * **ViewModel:** Manages UI-related data in a lifecycle-conscious way.
      * **LiveData / StateFlow:** Observable data holders that are lifecycle-aware.
      * **SavedStateHandle:** For handling process death.
  * **Coroutines & Flow:** For asynchronous programming and reactive data streams.
      * `kotlinx-coroutines-core`
      * `kotlinx-coroutines-android`
  * **Dependency Injection:**
      * **Koin / Dagger Hilt:** *(Choose one and specify, e.g., Koin)* For managing and injecting dependencies.
  * **Networking:**
      * **Retrofit:** A type-safe HTTP client for Android and Java.
      * **OkHttp:** An HTTP & HTTP/2 client for Android and Java applications.
      * **Moshi / Gson:** *(Choose one and specify, e.g., Moshi)* A JSON deserialization library.
  * **Image Loading:**
      * **Coil:** An image loading library for Android backed by Kotlin Coroutines.
  * **Testing:**
      * **JUnit 4:** For unit testing.
      * **MockK:** A mocking library for Kotlin.
      * **Kotlinx Coroutines Test:** Utilities for testing Kotlin Coroutines.
      * **Truth:** A fluent assertion library for Java and Android.
      * **Jetpack Compose Test:** Testing utilities for Jetpack Compose UI.
      * **AndroidX Test:** Core testing utilities for Android.

-----

## 🏗️ Architecture

The project adheres to a **Clean Architecture** (or **MVVM with Use Cases**) pattern, promoting separation of concerns, testability, and maintainability.

### **Layers:**

  * **Presentation Layer:**
      * **UI (`@Composable` functions):** Built with Jetpack Compose, responsible for rendering the UI and collecting user input.
      * **ViewModels:** Expose UI state as `StateFlow` (or `LiveData`) and handle UI logic, interacting with Use Cases.
  * **Domain Layer:**
      * **Use Cases (Interactors):** Contain the application's business logic. They orchestrate data flow between the Presentation and Data layers, operating on domain models.
      * **Domain Models:** Pure Kotlin data classes representing the core business entities.
  * **Data Layer:**
      * **Repositories:** Define contracts for data operations and abstract the data sources. They decide whether to fetch data from the network, database, or cache.
      * **Data Sources (Remote/Local):** Implement the data fetching logic (e.g., Retrofit for network calls, Room for local database).
      * **Data Transfer Objects (DTOs):** Data models used for network communication, mapped to domain models by repositories.

-----

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### **Prerequisites**

  * Android Studio Arctic Fox (or newer)
  * JDK 11 or higher
  * An Android device or emulator running API 21+

### **Installation**

1.  Clone the repo:
    ```bash
    git clone https://github.com/BobJones82/Comic-App-2025.git
    ```
2.  Open the project in Android Studio.
3.  Build the project to download all dependencies.
    ```bash
    # In Android Studio, go to Build -> Rebuild Project
    ```
4.  Run the application on your desired emulator or device.

-----

## 🧪 Testing

This project emphasises a strong testing culture with comprehensive unit and instrumentation tests.

### **Unit Tests**

Located in `app/src/test/java/`, these tests focus on the business logic and state management within isolation (e.g., ViewModels, Use Cases, Repositories with mocked dependencies).

To run unit tests:

```bash
./gradlew testDebugUnitTest
```

### **Instrumentation Tests**

Located in `app/src/androidTest/java/`, these tests run on an actual device or emulator to verify UI interactions, navigation, and integration of various components.

To run instrumentation tests:

```bash
./gradlew connectedCheck
```

-----


