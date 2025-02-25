# Weather Forecast App

This Weather Forecast application is built using modern Android development technologies and provides users with up-to-date weather information.

## Features

- Current weather conditions
- 3-day weather forecast
- Search for weather by city
- Save favorite locations
- Toggle between imperial and metric units

## Technologies Used

- Jetpack Compose for UI
- Hilt for dependency injection
- Coroutines for asynchronous programming
- Retrofit for API calls
- Room Database for local data storage
- Coil for image loading
- Kotlinx Serialization for JSON parsing (Type-Safe Navigation)

## Setup and Usage

1. Download and install [Android Studio](https://developer.android.com/studio) if you haven't already.

2. Clone this project repository.

3. Open the project in Android Studio.

4. Create an account at [WeatherAPI.com](https://www.weatherapi.com/) and obtain your API key.

5. In the main project directory (weather-forecast-jc), create a file named `bartosboth.properties`.

6. Add your API key to the file as follows:

```
  WEATHER_API_KEY=your_api_key_here
```

7. Build and run the project using either an Android emulator or a physical Android device connected to your computer.

## Using the App

- Launch the app to view the current weather for searched locations
- Use the search function to find weather information for other cities
- Save locations as favorites for quick access
- Toggle between imperial and metric units in the settings
