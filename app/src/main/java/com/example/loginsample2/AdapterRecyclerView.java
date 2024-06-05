package com.example.loginsample2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder> {



    public static class Item {
        public int imageResId;
        public String title;
        public String location;
        public String author;

        public Item(int imageResId, String title, String location, String author) {
            this.imageResId = imageResId;
            this.title = title;
            this.location = location;
            this.author = author;
        }
    }

    private List<Item> items;

    public AdapterRecyclerView(List<Item> items) {
        this.items = items;
        //this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = items.get(position);
        //holder.imageView.setImageResource(item.imageResId);



        //PARA EJEMPLO
        holder.imageView.setImageResource(R.drawable.carpintero_de_nidos);
        holder.titleTextView.setText(item.title);
        holder.locationTextView.setText(item.location);
        holder.authorTextView.setText(item.author);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(List<Item> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleTextView;
        public TextView locationTextView;
        public TextView authorTextView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.item_image);
            titleTextView = view.findViewById(R.id.item_title);
            locationTextView = view.findViewById(R.id.item_location);
            authorTextView = view.findViewById(R.id.item_author);
        }
    }
}

