# 🎬 Movies App - With Unit Testing

## 📌 **Project Overview**
The **Movies App** is a simple project that demonstrates how to implement **ViewModel with StateFlow** to manage and display a list of movies. The project follows the **MVVM (Model-View-ViewModel) architecture**, ensuring a clean separation of concerns. The application fetches movie data from a repository and processes it using a **ResultState** mechanism to handle success and error scenarios.

Additionally, the project includes **unit tests** for **MoviesViewModel, Repository, Api Service, Dao, and UseCases**, ensuring that data flows correctly and behaves as expected. The tests use **Mockk, Turbine, and Kotlin Coroutines Test** to simulate API responses and verify state changes in ViewModel.

---

## 🛠️ **Technologies Used**
- **Kotlin** – The primary programming language.
- **MVI Architecture** – For better code organization and maintainability.
- **Clean Architecture**
- **StateFlow** – To manage UI state reactively.
- **Coroutines** – For handling asynchronous operations efficiently.
- **Dagger Hilt** - For Dependency Injection.
- **Retrofit** - For API Calls.
- **Room** - For Caching Data.
- **Mockk** – A powerful mocking library for unit testing.
- **JUnit 4** – The standard testing framework.
- **Kotlinx Coroutines Test** – To test coroutine-based implementations.
- **Turbine** – A testing library for Flow and StateFlow emissions.

---

## 📂 **Project Structure**

## 1️⃣ **App Layer (Application Management) 📱**
This layer is responsible for managing the **entire lifecycle of the application**. It initializes necessary dependencies and ensures the smooth functioning of the app from launch to termination.

### 📌 **Main Components:**
- **MoviesApplication** → Extends `Application` and initializes **Dagger Hilt** for dependency injection.
- **AppModule** → Provides essential dependencies needed across the app.

---

## 2️⃣ **Presentation Layer (UI Layer) 🎨**
This layer contains UI-related components such as **Fragments, ViewModels, Adapters, and State Management**. It interacts with the **Domain Layer** via **Intents** and observes **State changes** from ViewModel.

### 📌 **Main Components:**
- **MoviesFragment** → Displays the list of movies.
- **MoviesViewModel** → Handles UI logic and state management.
- **MoviesIntent** → Represents user actions (e.g., fetching movies).
- **MoviesState** → Represents UI states (Loading, Success, Error).
- **MoviesAdapter & ShimmerAdapter** → RecyclerView adapters for displaying movies.

### 📌 **Flow in Presentation Layer:**
1. **User action** triggers an Intent (e.g., FetchNowPlayingMovies).
2. **ViewModel processes** the Intent and updates the **StateFlow**.
3. **Fragment observes** StateFlow and updates the UI accordingly.

---

## 3️⃣ **Domain Layer (Business Logic) 🧠**
This layer contains **use cases and business logic**. It acts as an intermediary between the **Presentation and Data layers**, ensuring separation of concerns.

### 📌 **Main Components:**
- **MovieRepository** → Interface defining movie-related operations.
- **MovieDomainModel & MovieDetailsDomainModel** → Represent domain-level movie entities.

### 📌 **Responsibilities:**
- Provides a clean API for fetching data.
- Maps **Data Layer** models to **Domain Layer** models for abstraction.

---

## 4️⃣ **Data Layer (Data Management) 📊**
This layer is responsible for **fetching, caching, and managing data**. It includes **local storage, remote API calls, and repositories**.

### 📌 **Main Components:**

### 🔹 **Dependency Injection Modules (Using Dagger Hilt)**
- **DatabaseModule** → Provides **Room Database** instances.
- **NetworkModule** → Provides **Retrofit API** services.
- **RepositoryModule** → Provides **repository dependencies**.

### 🔹 **Local Data (Room Database) 🗄️**
- **MovieDao** → DAO for accessing movie data from the database.
- **MovieDatabase** → Room Database class.
- **MovieEntity & MovieDetailsEntity** → Data models stored in Room.
- **LocalDataSource (Interface)** → Defines local data access operations.
- **LocalDataSourceImpl** → Implements local data access using Room.

### 🔹 **Remote Data (API Calls) 🌍**
- **ApiService** → Retrofit interface for fetching movies from the API.
- **RemoteDataSource (Interface)** → Defines API-related operations.
- **RemoteDataSourceImpl** → Implements network calls using Retrofit.

### 🔹 **Repository Implementation 🏛️**
- **MovieDataModel & MovieDetailsDataModel** → Models for mapping API responses.
- **MovieRepositoryImpl** → Combines **Remote & Local Data Sources**, implementing business logic for fetching and caching movies.
- **DataState** → Represents **Success, Loading, and Error** states for network responses.

---

## 5️⃣ **Util Layer (Utilities & Constants) ⚙️**
This layer contains **utility functions, constants, and helper classes** used across the app.

### 📌 **Main Components:**
- **APIEndPoint** → Contains **API endpoint URLs**.

---

## 📌 **Conclusion**
This project follows **MVI architecture**, ensuring **clean and scalable code**. By separating **UI, business logic, and data handling**, the app is **maintainable, testable, and efficient**. 🚀

