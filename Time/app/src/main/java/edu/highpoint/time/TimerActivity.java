package edu.highpoint.time;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    // Declare UI components
    EditText editTextHours, editTextMinutes, editTextSeconds; // Input fields for hours, minutes, and seconds
    TextView textViewTimerDisplay; // TextView to display the timer
    Button buttonStartTimer, buttonPauseTimer, buttonResetTimer; // Buttons for timer control

    CountDownTimer countDownTimer; // Instance of CountDownTimer for countdown functionality
    long timeLeftInMillis; // Holds the remaining time in milliseconds
    boolean timerRunning; // Boolean to track whether the timer is running

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call to the superclass
        setContentView(R.layout.activity_timer); // Set the content view to the timer activity layout

        // Initialize UI components
        editTextHours = findViewById(R.id.editTextHours); // Find and assign EditText for hours
        editTextMinutes = findViewById(R.id.editTextMinutes); // Find and assign EditText for minutes
        editTextSeconds = findViewById(R.id.editTexttSeconds); // Find and assign EditText for seconds
        textViewTimerDisplay = findViewById(R.id.textViewTimerDisplay); // Find and assign TextView for displaying timer

        buttonStartTimer = findViewById(R.id.buttonStartTimer); // Find and assign Start button
        buttonPauseTimer = findViewById(R.id.buttonPauseTimer); // Find and assign Pause button
        buttonResetTimer = findViewById(R.id.buttonResetTimer); // Find and assign Reset button
    }

    public void startTimer(View view) {
        // Method to start the timer
        if (!timerRunning) { // Check if the timer is not already running
            int hours = parseInput(editTextHours); // Parse hours input
            int minutes = parseInput(editTextMinutes); // Parse minutes input
            int seconds = parseInput(editTextSeconds); // Parse seconds input

            // Convert input time to milliseconds
            timeLeftInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;

            // Check if the input time is valid
            if (timeLeftInMillis == 0) {

                // This displays a Toast notification to inform the user that they need to input a valid time.
                // Toast messages provide quick feedback to the user without interrupting their flow
                // I lean this this form Chat GPT.
                Toast.makeText(this, "Please enter a valid time", Toast.LENGTH_SHORT).show(); // Show a message if invalid
                return; // Exit the method if the time is invalid
            }

            // Start the countdown
            // The CountDownTimer is initialized with the total time in milliseconds and ticks every 1 second.
            // This will handle the countdown logic, triggering the onTick method every second.
            // I lean this this form Chat GPT.

            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { // Create a countdown timer
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished; // Update remaining time
                    updateTimerDisplay(); // Update the displayed time
                }
                public void onFinish() {
                    timerRunning = false; // Timer is no longer running
                    textViewTimerDisplay.setText("00:00:00"); // Reset the display to 00:00:00
                }
            }.start(); // Start the countdown timer
            timerRunning = true; // Set the timerRunning flag to true
        }
    }

    public void pauseTimer(View view) {
        // Method to pause the timer
        if (timerRunning) { // Check if the timer is running
            countDownTimer.cancel(); // Cancel the countdown timer
            timerRunning = false; // Set the timerRunning flag to false
        }
    }

    public void resetTimer(View view) {
        // Method to reset the timer
        if (timerRunning) { // Check if the timer is running
            countDownTimer.cancel(); // Cancel the countdown timer
            timerRunning = false; // Set the timerRunning flag to false
        }
        timeLeftInMillis = 0; // Reset remaining time
        textViewTimerDisplay.setText("00:00:00"); // Reset the display to 00:00:00
    }

    private void updateTimerDisplay() {
        // Method to update the timer display
        int hours = (int) (timeLeftInMillis / 1000) / 3600; // Calculate remaining hours
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60; // Calculate remaining minutes
        int seconds = (int) (timeLeftInMillis / 1000) % 60; // Calculate remaining seconds

        // Format the time as HH:MM:SS
        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        textViewTimerDisplay.setText(timeFormatted); // Update the timer display with the formatted time
    }

    private int parseInput(EditText editText) {
        // Method to parse input from EditText
        String input = editText.getText().toString(); // Get input as a string
        if (input.isEmpty()) { // Check if the input is empty
            return 0; // Return 0 if empty
        } else {
            return Integer.parseInt(input); // Return the parsed integer value
        }
    }

    // Method to navigate to WorldClockActivity
    public void WorldClock(View view) {
        Intent intent = new Intent(this, WorldClockActivity.class); // Create an intent for WorldClockActivity
        startActivity(intent); // Start the WorldClockActivity
    }

    // Method to start the Stopwatch activity
    public void Stopwatch(View view) {
        Intent intent = new Intent(this, Stopwatch.class); // Create an intent for Stopwatch activity
        startActivity(intent); // Start the Stopwatch activity
    }
}
