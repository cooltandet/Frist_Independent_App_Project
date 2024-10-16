package edu.highpoint.time;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Stopwatch extends AppCompatActivity {

    // Declare UI components
    TextView textViewStopwatchDisplay; // TextView to display stopwatch time
    Button buttonStartStopwatch, buttonPauseStopWatch, buttonResetStopwatch; // Buttons for stopwatch control
    Handler handlertimer; // Handler to manage timer updates
    long startTime; // Holds the start time in milliseconds
    long timeInMillis; // Holds the elapsed time in milliseconds
    long timeSwapBuff; // Buffers the time when paused
    long updateTime; // Holds the total elapsed time
    Runnable updateTimerThread; // Runnable for updating the timer display

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call to the superclass
        EdgeToEdge.enable(this); // Enable edge-to-edge layout for the activity
        setContentView(R.layout.activity_stopwatch); // Set the content view to the stopwatch activity layout

        // Apply window insets to the main view for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Get system bar insets
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Set padding based on insets
            return insets; // Return insets for further processing
        });

        // Initialize UI elements
        textViewStopwatchDisplay = findViewById(R.id.textViewStopwatchDisplay); // Find and assign TextView for stopwatch
        buttonStartStopwatch = findViewById(R.id.buttonStartStopwatch); // Find and assign Start button
        buttonPauseStopWatch = findViewById(R.id.buttonPauseStopWatch); // Find and assign Pause button
        buttonResetStopwatch = findViewById(R.id.buttonResetStopwatch); // Find and assign Reset button

        // Initialize timer variables
        startTime = 0L; // Initial start time
        timeInMillis = 0L; // Initial elapsed time
        timeSwapBuff = 0L; // Initial buffer time
        updateTime = 0L; // Initial update time


        // I used Handler is used to manage the timing for the stopwatch.
        // It allows the app to schedule updates to the UI without blocking the main thread.
        // The Handler repeatedly posts a Runnable that updates the stopwatch display every millisecond.
        // This ensures the timer runs smoothly and updates the UI in real-time.

        handlertimer = new Handler();// Initialize handler for updating timer

        // I had to learn how to use Runnables, so I asked ChatGPT to explain them.
        // Using that information, I set up my first Runnable, which runs until the updateTimerThread is removed.
        updateTimerThread = new Runnable() {// Runnable to update the timer display

            public void run() {
                // I calculated the elapsed time using the system time.
                // I initially tried using a counter, but it was too slow.
                // Instead, I utilized the system as a ready-built timer by grabbing the time and
                // calculating the elapsed time, allowing me to see
                // what time has passed and update the display accordingly.
                timeInMillis = System.currentTimeMillis() - startTime; // Calculate elapsed time
                updateTime = timeSwapBuff + timeInMillis; // Update total time


                //This involves basic math by splitting the milliseconds from System.currentTimeMillis(),
                //converting it into seconds, then into minutes,
                //and back into seconds while also grabbing the milliseconds.
                //This is how we update the time on the stopwatch.
                // Convert milliseconds to minutes, seconds, and milliseconds
                int secs = (int) (updateTime / 1000); // Calculate total seconds
                int mins = secs / 60; // Calculate total minutes
                secs = secs % 60; // Remaining seconds
                int milliseconds = (int) ((updateTime % 1000) / 10); // Convert to milliseconds

                // Update the stopwatch display
                textViewStopwatchDisplay.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs) + "." + String.format("%02d", milliseconds));

                // Post the Runnable again to keep updating the timer
                handlertimer.postDelayed(this, 0); // Re-post the Runnable with a delay of 0 milliseconds
            }
        };
    }

    // Start button logic
    public void startStopwatch(View view) {
        // Capture the start time and start updating the timer
        startTime = System.currentTimeMillis(); // Record the current time
        handlertimer.postDelayed(updateTimerThread, 0); // Start the timer updates
    }
    // Pause button logic
    public void pauseStopWatch(View view) {
        // Stop updating the timer and save the current time
        timeSwapBuff += timeInMillis; // Add elapsed time to buffer
        //Learn this when doing reacher on handler
        // This command stops any pending executions of the updateTimerThread,
        // pausing the stopwatch and ensuring that updates don't continue when they shouldn't.
        handlertimer.removeCallbacks(updateTimerThread); // Stop the timer updates
    }
    // Reset button logic
    public void resetStopwatch(View view) {
        // Reset all timer variables and update the UI to "00:00:00"
        startTime = 0L; // Reset start time
        timeSwapBuff = 0L; // Reset buffer time
        timeInMillis = 0L; // Reset elapsed time
        updateTime = 0L; // Reset total time
        textViewStopwatchDisplay.setText("00:00.00"); // Update the display to 00:00.00
        //Learn this when doing reacher on handler
        // This command stops any pending executions of the updateTimerThread,
        // pausing the stopwatch and ensuring that updates don't continue when they shouldn't.
        handlertimer.removeCallbacks(updateTimerThread); // Stop any running timer updates
    }

    // Method to navigate to WorldClockActivity
    public void WorldClock(View view) {
        // I learn this form chat how to  creates an intent to start the other class.
        // This enabls a smooth transitions between different functionalities within the app,
        // enhancing user navigation
        Intent intent = new Intent(this, WorldClockActivity.class); // Create an intent for WorldClockActivity
        startActivity(intent); // Start the WorldClockActivity
    }

    // Method to navigate to Timer activity
    public void Timer(View view) {
        // I learn this form chat how to  creates an intent to start the other class.
        // This enabls a smooth transitions between different functionalities within the app,
        // enhancing user navigation
        Intent intent = new Intent(this, TimerActivity.class); // Create an intent for TimerActivity
        startActivity(intent); // Start the Timer activity
    }
}
