# Make Your Own App Assignment

## Overview
The Make Your Own App Assignment goal is to make an app that provide users with a useful tool that addresses a specific need, showcasing creativity and advanced problem-solving skills in Android app development.


## Project Details
**Course:** CSC 3312 - Make Your Own App Assignment  
**Instructor:** Dr. Williams, High Point University  
**Semester:** Fall 2024

### Assignment Goals
The objective of this assignment is to create a fully functional and innovative Android app that demonstrates:
- Effective user interface (UI) design
- Robust event handling and input validation
- Integration of advanced features beyond the basic requirements

### Features
- **C-Level Requirements:**
  - You create an APP that is extremely simple does not have much code or function.
  - You have a working APP, but I can make it fail in simple testing.
  - *note* The failure in testing can be waived for APP that are especially involved or innovative. If
you try something quite ambitious, you may only reach a "proof of concept" in two weeks. If you
are uncertain if your APP qualifies for this waiver, ask me before next Tuesday

- **B-Level Requirements:**
  - Useful and fully functional APP
  - Can be simple so long as it has clear and compelling usefulness

- **A-Level Requirements:**
  - You create a fully functional and useful APP
  - Your APP uses a significant technology beyond what I showed you in class

## Getting Started

### Prerequisites
- Android Studio

### Installation
1. Clone the repository or download the source code.
2. Open Android Studio and import the project.
3. Build and run the application on an emulator or physical device.

# Code Structure

## Java Classes

### `StopwatchActivity.java`
This class handles the stopwatch functionality, allowing users to start, pause, reset, and interact with other timekeeping features.
- **UI components include:**
  - **`run`**: Executes the Runnable interface to update the stopwatch's time display at regular intervals.
  - **`startStopwatch`**: Starts the stopwatch by initializing the `Handler` and scheduling the time update Runnable.
  - **`pauseStopwatch`**: Pauses the stopwatch by removing any scheduled updates from the `Handler`, freezing the time display.
  - **`resetStopwatch`**: Resets the stopwatch time back to 00:00:00, stopping any ongoing updates and clearing the elapsed time.
  - **`WorldClock`**: Navigates to the `WorldClockActivity` for managing and displaying clocks from different time zones.
  - **`Timer`**: Navigates to the `TimerActivity` for using the countdown timer feature.

### `TimerActivity.java`
This class manages the countdown timer, allowing users to start, pause, reset, and configure their timers.
- **UI components include:**
  - **`startTimer`**: Starts the countdown timer using a `CountDownTimer`, tracking the remaining time until it finishes.
  - **`onTick`**: Called at regular intervals while the timer is running, updating the UI to reflect the time remaining.
  - **`onFinish`**: Triggered when the timer reaches zero, stopping the timer and potentially triggering an alarm or notification.
  - **`pauseTimer`**: Pauses the active countdown by canceling the `CountDownTimer`, freezing the remaining time.
  - **`resetTimer`**: Resets the timer's duration to the initial input, canceling any ongoing countdowns.
  - **`updateTimerDisplay`**: Updates the displayed time on the screen based on the current timer state or user input.
  - **`parseInput`**: Processes user input for setting the countdown time, ensuring valid values before starting the timer.
  - **`WorldClock`**: Navigates to the `WorldClockActivity` for viewing and managing multiple time zones.
  - **`Stopwatch`**: Navigates to the `StopwatchActivity` for using the stopwatch feature.

### `WorldClockActivity.java`
This class handles adding and displaying multiple clocks from various time zones, updating them in real time.
- **UI components include:**
  - **`onItemSelected`**: Responds to user selection in a dropdown or spinner, typically used for selecting a time zone to display.
  - **`onNothingSelected`**: Triggered when no selection is made in the dropdown, providing a fallback or handling empty selections.
  - **`addClock`**: Adds a new clock for the selected time zone to the list of displayed clocks, allowing for multiple time zone tracking.
  - **`onClick`**: Handles button clicks or other user interactions, often linked to adding/removing clocks or changing views.
  - **`startClockUpdates`**: Initiates the real-time clock updates by starting a handler that runs at regular intervals.
  - **`run`**: Executes the Runnable interface to update the time display for each clock in the list.
  - **`updateAllClocks`**: Loops through all added clocks and refreshes their displayed time to stay in sync with real time.
  - **`updateClockDisplay`**: Updates the UI to reflect the current time for a specific clock, formatting the output accordingly.
  - **`getCurrentTime`**: Fetches the current time for a specific time zone, used in the clock display and updates.
  - **`Stopwatch`**: Navigates to the `StopwatchActivity` for using the stopwatch feature.
  - **`Timer`**: Navigates to the `TimerActivity` for using the countdown timer feature.

## Layouts
These XML layout files define the structure and appearance of the UI for each activity.

- **`activity_timer.xml`**: The layout for the timer screen, including input fields for setting time, buttons for controlling the timer, and a display area for the remaining time.
- **`activity_stopwatch.xml`**: The layout for the stopwatch screen, featuring buttons to start, pause, and reset the stopwatch, along with a display for the elapsed time.
- **`activity_worldclock.xml`**: The layout for the world clock screen, allowing users to select time zones and add clocks, with a list of displayed clocks that update in real time.


## Built With
- Java
- Android SDK
- AndroidX libraries
