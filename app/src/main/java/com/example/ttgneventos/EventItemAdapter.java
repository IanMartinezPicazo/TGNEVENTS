package com.example.ttgneventos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public final class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.EventItem>
{
    private List<Event> _events;

    public EventItemAdapter(List<Event> events) { _events = events; }

    @NonNull
    @Override
    public EventItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new EventItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventItem holder, int position)
    {
        Event event = _events.get(position);

        holder.getEventItemTitle().setText(event.getTitle());
        holder.getEventItemDescription().setText(event.getDescription());
        holder.getEventItemTime().setText(event.getDateTime().toString());
    }

    @Override
    public int getItemCount() { return _events.size(); }

    public static final class EventItem extends RecyclerView.ViewHolder
    {
        // ID references
        private final TextView
            _eventItemTitle,
            _eventItemDescription,
            _eventItemTime;

        private final ImageView _eventItemImage;

        // Constructor
        public EventItem(View itemView)
        {
            super(itemView);

            // Initialize IDs
            _eventItemTitle = itemView.findViewById(R.id.eventItemTitle);
            _eventItemDescription = itemView.findViewById(R.id.eventItemDescription);
            _eventItemTime = itemView.findViewById(R.id.eventItemTime);
            _eventItemImage = itemView.findViewById(R.id.eventItemImage);
        }

        // Getters
        public TextView getEventItemTitle() { return _eventItemTitle; }
        public TextView getEventItemDescription() { return _eventItemDescription; }
        public TextView getEventItemTime() { return _eventItemTime; }
        public ImageView getEventItemImage() { return _eventItemImage; }
    }
}
