
# QuoteGen

QuoteGen is an Android application that displays random quotes fetched from an API and allows users to save their favorite quotes for later viewing. The app features a clean, modern UI built with Jetpack Compose and follows MVVM architecture.

## Table of Contents
- [Features](#features)
- [Project Setup](#project-setup)
- [Architecture](#architecture)
- [File Structure](#file-structure)
- [Workflow](#workflow)
- [Dependencies](#dependencies)

## Features

- Fetch random quotes from an external API
- Save favorite quotes to a local database
- View and manage saved quotes
- Dynamic background colors that change with each quote
- Responsive UI built with Jetpack Compose

## Project Setup

### Prerequisites
- Android Studio Flamingo or newer
- JDK 17
- Android SDK 35 (Android 15)

### Getting Started

1. Clone the repository:
```bash
git clone https://github.com/Iftiarchowdhury/QuoteGen.git
```

2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Build and run the application on an emulator or physical device running Android 15 or higher.

### API Key

The application uses the API Ninjas quotes API. The API key is already included in the `QuoteApiService.kt` file. If you want to use your own API key:

1. Get an API key from [API Ninjas](https://api-ninjas.com/)
2. Replace the existing key in `QuoteApiService.kt`:
```kotlin
@Headers(
    "X-Api-Key: your-api-key-here"
)
```

## Architecture

QuoteGen follows the MVVM (Model-View-ViewModel) architecture pattern:

- **Model**: Represented by the data classes and Room database
- **View**: Implemented using Jetpack Compose
- **ViewModel**: Manages UI-related data and handles business logic

The app also uses the Repository pattern to abstract data sources and provide a clean API to the ViewModel.

## File Structure

### Main Components

#### API
- **QuoteApiService.kt**: Defines the Retrofit service for fetching quotes from the API Ninjas endpoint.

#### Data
- **Quote.kt**: Contains data classes for quotes and response mapping.
- **QuoteDao.kt**: Data Access Object interface for Room database operations.
- **QuoteDatabase.kt**: Room database configuration and singleton instance.

#### UI
- **MainActivity.kt**: Entry point of the application, contains the main Compose UI.
- **ui/theme/**: Contains theme-related files for styling the app.

#### ViewModel
- **QuoteViewModel.kt**: Manages UI state and business logic, connects the UI with data sources.

### Detailed File Descriptions

#### app/src/main/java/com/example/quotegen/api/QuoteApiService.kt
Defines the Retrofit service interface for fetching quotes from the API. Includes:
- API endpoint definition
- API key header configuration
- Retrofit instance creation

#### app/src/main/java/com/example/quotegen/data/Quote.kt
Contains:
- `Quote` data class (Room entity)
- `QuoteResponse` data class for API responses
- Extension function to convert API responses to database entities

#### app/src/main/java/com/example/quotegen/data/QuoteDao.kt
Room DAO interface with methods for:
- Getting all saved quotes
- Inserting a new quote
- Deleting a quote

#### app/src/main/java/com/example/quotegen/data/QuoteDatabase.kt
Room database configuration:
- Database class definition
- Singleton pattern implementation
- Database builder with proper context handling

#### app/src/main/java/com/example/quotegen/MainActivity.kt
Main activity containing:
- App entry point
- Navigation setup with NavHost
- Home screen UI with quote display
- Saved quotes screen UI

#### app/src/main/java/com/example/quotegen/viewmodel/QuoteViewModel.kt
ViewModel handling:
- UI state management with `QuoteUiState` data class
- Quote fetching from API
- Database operations for saving and deleting quotes
- Dynamic color generation for the UI

## Workflow

1. **App Launch**:
   - The app initializes the ViewModel
   - The ViewModel fetches a random quote from the API
   - The quote is displayed on the home screen with a random background color

2. **User Interactions**:
   - **Next Quote**: Fetches another random quote from the API
   - **Save Quote**: Saves the current quote to the local database
   - **View Saved Quotes**: Navigates to the saved quotes screen
   - **Delete Quote**: Removes a quote from the saved quotes list
   - **Select Saved Quote**: Shows the selected quote on the home screen

3. **Data Flow**:
   - API → ViewModel → UI (for fetching quotes)
   - UI → ViewModel → Database (for saving quotes)
   - Database → ViewModel → UI (for displaying saved quotes)

## Dependencies

- **Jetpack Compose**: Modern UI toolkit for building native Android UI
- **Navigation Compose**: Navigation component for Compose
- **Room**: SQLite object mapping library for local database storage
- **Retrofit**: Type-safe HTTP client for API communication
- **OkHttp**: HTTP client used by Retrofit
- **Lifecycle Components**: ViewModel and LiveData for lifecycle-aware data handling
- **Kotlin Coroutines**: For asynchronous programming

All dependencies are managed through Gradle and defined in the `gradle/libs.versions.toml` file.

---
