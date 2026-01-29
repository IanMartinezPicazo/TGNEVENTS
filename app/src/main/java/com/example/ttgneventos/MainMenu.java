package com.example.ttgneventos;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class MainMenu extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_menu);
        ViewCompat.setOnApplyWindowInsetsListener
        (
            findViewById(R.id.main), (v, insets) ->
            {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        );

        List<Event> events = new ArrayList<>();

        // 1. Music Event
        events.add(new Event(
                "Summer Jazz Night",
                "Music",
                LocalDateTime.of(2024, 7, 15, 20, 0),
                "Central Park Garden",
                25.00,
                "An evening of smooth jazz under the stars featuring local quintets."
        ));

        // 2. Tech Workshop
        events.add(new Event(
                "Android Dev Workshop",
                "Education",
                LocalDateTime.of(2024, 8, 10, 10, 30),
                "Tech Hub Room 402",
                0.00,
                "Learn the basics of RecyclerViews and dynamic UI generation."
        ));

        // 3. Sports
        events.add(new Event(
                "City Marathon",
                "Sports",
                LocalDateTime.of(2024, 9, 5, 7, 0),
                "Main Street Plaza",
                15.50,
                "Annual 10k run through the heart of the city."
        ));

        // 4. Food & Drink
        events.add(new Event(
                "Vegan Food Festival",
                "Food",
                LocalDateTime.of(2024, 6, 22, 12, 0),
                "Convention Center",
                10.00,
                "Explore over 50 vendors serving the best plant-based dishes."
        ));

        // 5. Art
        events.add(new Event(
                "Abstract Gallery Opening",
                "Art",
                LocalDateTime.of(2024, 10, 12, 18, 0),
                "Modern Art Museum",
                45.00,
                "Exclusive first look at the 'Colors of Silence' collection."
        ));

        // 6. Business
        events.add(new Event(
                "Startup Pitch Night",
                "Business",
                LocalDateTime.of(2024, 11, 2, 19, 0),
                "Innovation Lab",
                0.00,
                "Watch 5 startups battle for $10k in seed funding."
        ));

        // 7. Cinema
        events.add(new Event(
                "Vintage Movie Marathon",
                "Entertainment",
                LocalDateTime.of(2024, 12, 20, 15, 0),
                "The Grand Theatre",
                12.00,
                "Back-to-back screenings of classic 1950s cinema."
        ));

        // 8. Outdoor/Nature
        events.add(new Event(
                "Stargazing Expedition",
                "Nature",
                LocalDateTime.of(2024, 8, 25, 22, 30),
                "Blue Ridge Peak",
                5.00,
                "Join professional astronomers for a tour of the summer sky."
        ));

        // 9. Community
        events.add(new Event(
                "Charity Bake Sale",
                "Community",
                LocalDateTime.of(2024, 5, 14, 9, 0),
                "St. Jude Community Hall",
                0.00,
                "All proceeds go toward the local youth sports program."
        ));

        // Sorts events by proximity of date
        events.sort((e1, e2) -> e1.getDateTime().compareTo(e2.getDateTime()));

        RecyclerView recyclerView = new RecyclerView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        recyclerView.setLayoutParams(params);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);

        EventItemAdapter adapter = new EventItemAdapter(events);
        recyclerView.setAdapter(adapter);

        ViewGroup rootContainer = findViewById(R.id.main);
        rootContainer.addView(recyclerView);
    }
}
