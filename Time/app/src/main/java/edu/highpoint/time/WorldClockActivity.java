package edu.highpoint.time;

// Import necessary Android and Java classes
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

public class WorldClockActivity extends AppCompatActivity {

    // Declare UI components
    LinearLayout clocksContainer; // Layout to hold multiple clocks
    Spinner spinnerTimeZone; // Spinner for selecting time zones
    Handler handlerWorldClock = new Handler(); // Handler to manage clock updates
    Set<String> addedTimeZones = new HashSet<>(); // Set to track added time zones and avoid duplicates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity
        setContentView(R.layout.activity_world_clock);

        // Initialize UI components
        clocksContainer = findViewById(R.id.clocksContainer); // Find the LinearLayout for displaying clocks
        spinnerTimeZone = findViewById(R.id.spinnerTimeZone); // Find the Spinner for time zones

        // Get all available time zones dynamically
        String[] timeZones = TimeZone.getAvailableIDs();

        // Create an ArrayAdapter to populate the Spinner with time zones
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeZones);
        // Set the layout for the dropdown view of the Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Assign the adapter to the Spinner to display the list of time zones
        spinnerTimeZone.setAdapter(adapter);

// Set a listener on the spinner to respond to item selections
        spinnerTimeZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Method called when an item is selected from the spinner
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected time zone from the spinner
                String selectedTimeZone = (String) spinnerTimeZone.getSelectedItem();
                // Check if the selected time zone has already been added to the list of clocks
                if (!addedTimeZones.contains(selectedTimeZone)) {
                    // If the time zone is not already added, invoke the method to add a clock for this time zone
                    addClock(selectedTimeZone);
                    // Add the time zone to the set to prevent adding duplicate clocks
                    addedTimeZones.add(selectedTimeZone);
                }
                else {
                    // Show a message to the user if the clock for the selected time zone is already present
                    Toast.makeText(WorldClockActivity.this, "Clock for this time zone already added.", Toast.LENGTH_SHORT).show();
                }
            }
            // Method called when no item is selected (optional)
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed when no item is selected
            }
        });
        // Start clock updates for all added clocks
        startClockUpdates();

    }

    // Method to add a clock for the selected time zone
    public void addClock(String selectedTimeZone) {
        // Create a new horizontal layout for the clock and its remove button
        LinearLayout clockLayout = new LinearLayout(this);
        clockLayout.setOrientation(LinearLayout.HORIZONTAL); // Set layout orientation

        // Create a TextView to display the clock
        TextView clockView = new TextView(this);
        clockView.setTextSize(20); // Set text size for readability
        clockView.setTag(selectedTimeZone); // Tag the TextView with the time zone for easy retrieval
        clockView.setPadding(0, 10, 0, 10); // Add padding for spacing

        // Initialize the clock display with the current time for the selected time zone
        updateClockDisplay(clockView, selectedTimeZone);

        // Create a button to remove this clock
        Button removeButton = new Button(this);
        // Set the background color of the remove button to white using the color resource
        removeButton.setBackgroundColor(getResources().getColor(R.color.white));
        // Set the text displayed on the remove button
        removeButton.setText("Remove");
        // Set an OnClickListener on the button to define its behavior when clicked
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Remove the clock layout from the container when the remove button is clicked
                clocksContainer.removeView(clockLayout);
                // Remove the associated time zone from the set to allow re-adding it in the future if needed
                addedTimeZones.remove(selectedTimeZone);
            }
        });

        // Add the clock view and remove button to the horizontal layout
        clockLayout.addView(clockView);
        clockLayout.addView(removeButton);

        // Finally, add the new clock layout to the container
        clocksContainer.addView(clockLayout);
    }

    // Method to start periodic updates of the clocks
    private void startClockUpdates() {
        // Use the handler to schedule updates every second
        handlerWorldClock.postDelayed(new Runnable() {
            public void run() {
                // Update all clocks
                updateAllClocks();
                // Schedule the next update after 1 second (1000 milliseconds)
                handlerWorldClock.postDelayed(this, 1000);
            }
        }, 1000); // Initial delay of 1 second
    }

    // Method to update all clocks in the container
    private void updateAllClocks() {
        // Loop through each child view in the clocks container to update the clock displays
        for (int i = 0; i < clocksContainer.getChildCount(); i++) {
            // Get the child view at the current index in the container
            View child = clocksContainer.getChildAt(i);
            // Check if the child is an instance of LinearLayout (expected type for clock layouts)
            if (child instanceof LinearLayout) {
                // Cast the child view to LinearLayout to access its properties
                LinearLayout clockLayout = (LinearLayout) child;
                // Retrieve the first child of the layout, which should be the TextView displaying the clock
                TextView clockView = (TextView) clockLayout.getChildAt(0);
                // Get the time zone associated with this clock from the TextView's tag
                String timeZone = (String) clockView.getTag();
                // Update the display for this clock with the current time in the specified time zone
                updateClockDisplay(clockView, timeZone);
            }
        }
    }

    // Method to update the clock display for a specific time zone
    private void updateClockDisplay(TextView clockView, String timeZone) {
        // Get the current time formatted for the specified time zone
        String currentTime = getCurrentTime(timeZone);
        // Set the text of the clock view to show the time zone and current time
        clockView.setText(timeZone + " - " + currentTime);
    }

    // Method to get the current time for a specified time zone
    private String getCurrentTime(String timeZone) {
        // Create a SimpleDateFormat to format the time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a"); // Use 12-hour format with AM/PM
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone)); // Set the time zone for the formatter
        // Return the formatted current time
        return simpleDateFormat.format(new Date());
    }


    // Method to start the Stopwatch activity
    public void Stopwatch(View view) {
        Intent intent = new Intent(this, Stopwatch.class);// Create an intent for Stopwatch
        startActivity(intent); // Start the Stopwatch activity
    }

    // Method to start the Timer activity
    public void Timer(View view) {
        Intent intent = new Intent(this, TimerActivity.class);// Create an intent for Timer
        startActivity(intent); // Start the Timer activity
    }
}
