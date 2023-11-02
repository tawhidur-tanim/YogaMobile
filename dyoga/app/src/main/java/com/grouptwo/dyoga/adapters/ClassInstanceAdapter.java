package com.grouptwo.dyoga.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.data.entities.ClassInstance;
import com.grouptwo.dyoga.interfaces.ItemClickListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClassInstanceAdapter extends RecyclerView.Adapter<ClassInstanceAdapter.ClassInstanceViewHolder>{

    private final List<ClassInstance> classInstances;

    public ItemClickListener<ClassInstance> itemClickListener;

    public ClassInstanceAdapter() {
        this.classInstances = new ArrayList<>();
    }

    @NonNull
    @Override
    public ClassInstanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_instance_layout_item, parent, false);
        return new ClassInstanceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassInstanceViewHolder holder, int position) {
        ClassInstance classInstance = classInstances.get(position);
        holder.classInstanceDate.setText(DateTimeFormatter.ofPattern("dd MMM, yyyy").format(classInstance.getDate()));
        holder.classInstanceInstructor.setText(classInstance.getInstructor());
        holder.classInstanceRoom.setText(classInstance.getRoom());
    }

    @Override
    public int getItemCount() {
        return classInstances.size();
    }

    public void setItemClickListener(ItemClickListener<ClassInstance> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setClassInstances(List<ClassInstance> classInstances) {
        this.classInstances.clear();
        this.classInstances.addAll(classInstances);
        notifyDataSetChanged();
    }

    public class ClassInstanceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private final TextView classInstanceDate;
        private final TextView classInstanceInstructor;
        private final TextView classInstanceRoom;

        public ClassInstanceViewHolder(@NonNull View itemView) {
            super(itemView);

            classInstanceDate = itemView.findViewById(R.id.classInstanceDate);
            classInstanceInstructor = itemView.findViewById(R.id.classInstanceInstructor);
            classInstanceRoom = itemView.findViewById(R.id.classInstanceRoom);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null){
                final ClassInstance classInstance = classInstances.get(getAdapterPosition());
                itemClickListener.onItemClicked(v, getAdapterPosition(), classInstance);
            } else {
                Log.e(this.getClass().getName(), "Item click listener is null");
            }
        }
    }
}
