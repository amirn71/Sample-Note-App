package com.note.amirehsan.samplenoteapp.adapter;

/**
 * Created by AMiR ehsan on 2/27/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.note.amirehsan.samplenoteapp.Activityes.Activity_Add_Note;
import com.note.amirehsan.samplenoteapp.Database.Category_Contact;
import com.note.amirehsan.samplenoteapp.Database.Category_DatabaseHandler;
import com.note.amirehsan.samplenoteapp.R;

import java.util.List;


public class Category_ListAdapter extends RecyclerView.Adapter<Category_ListAdapter.MyViewHolder> {

    private List<Category_Contact> list;
    Context context;
    Category_DatabaseHandler db;
    TextView delete;
    Dialog dialog;

    public Category_ListAdapter(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, date, first_character;
        public RelativeLayout main, color;

        public MyViewHolder(View view) {
            super(view);

            main = (RelativeLayout) view.findViewById(R.id.main);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            first_character = (TextView) view.findViewById(R.id.first);
            color = (RelativeLayout) view.findViewById(R.id.color);
            db = new Category_DatabaseHandler(context);
        }


    }


    public Category_ListAdapter(List<Category_Contact> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Category_Contact contact = list.get(position);
        holder.name.setText(contact.getName());
        holder.date.setText(contact.getDate());
        holder.first_character.setText(contact.getName().substring(0, 1));

        if (contact.getColor().equals("green")) {
            holder.color.setBackgroundResource(R.drawable.bc_green);
        } else if (contact.getColor().equals("red")) {
            holder.color.setBackgroundResource(R.drawable.bc_red);
        } else if (contact.getColor().equals("blue")) {
            holder.color.setBackgroundResource(R.drawable.bc_blue);
        } else if (contact.getColor().equals("purple")) {
            holder.color.setBackgroundResource(R.drawable.bc_purple);
        }

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.remove_dialog);
        delete = (TextView) dialog.findViewById(R.id.confirm);

        holder.main.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                delete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(context, "deleted !", Toast.LENGTH_SHORT).show();
                        db.deleteContact(db.getContact(list.get(position).getID()));
                        list.remove(position);
                        notifyItemRangeRemoved(position, list.size());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                dialog.show();
                return true;
            }
        });

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(context, Activity_Add_Note.class);
                nextScreen.putExtra("color", list.get(position).getColor());
                nextScreen.putExtra("category", list.get(position).getName());
                context.startActivity(nextScreen);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}