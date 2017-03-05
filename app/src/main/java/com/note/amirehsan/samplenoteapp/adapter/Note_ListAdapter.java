package com.note.amirehsan.samplenoteapp.adapter;

/**
 * Created by AMiR ehsan on 2/27/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.note.amirehsan.samplenoteapp.Activityes.Activity_Drawing;
import com.note.amirehsan.samplenoteapp.Activityes.Activity_Note_Writing;
import com.note.amirehsan.samplenoteapp.Database.Note_Contact;
import com.note.amirehsan.samplenoteapp.Database.Note_DatabaseHandler;
import com.note.amirehsan.samplenoteapp.R;

import java.util.List;

public class Note_ListAdapter extends RecyclerView.Adapter<Note_ListAdapter.MyViewHolder> {

    private List<Note_Contact> list;
    Context context;
    Note_DatabaseHandler db;
    TextView delete;
    Dialog dialog;

    public Note_ListAdapter(Context context) {
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
            db = new Note_DatabaseHandler(context);
        }
    }

    public Note_ListAdapter(List<Note_Contact> list, Context context) {
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

        final Note_Contact contact = list.get(position);
        holder.name.setText(contact.getName());
        holder.date.setText(contact.getDate());

        if (contact.getDraw().equals("no")) {
            if (!contact.getName().equals("")) {
                holder.first_character.setText(contact.getName().substring(0, 1));
            }
        } else if (contact.getDraw().equals("yes")) {
            Typeface fontAwesome = Typeface.createFromAsset(context.getAssets(), "font/fontawesome-webfont.ttf");
            holder.first_character.setTypeface(fontAwesome);
            holder.first_character.setText("\uF1FC");
        }

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
                        Toast.makeText(context, "Deleted !", Toast.LENGTH_SHORT).show();
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
                if (list.get(position).getDraw().equals("no")) {
                    Intent nextScreen = new Intent(context, Activity_Note_Writing.class);
                    nextScreen.putExtra("color", list.get(position).getColor());
                    nextScreen.putExtra("category", list.get(position).getCategory());
                    nextScreen.putExtra("id", Integer.toString(list.get(position).getID()));
                    nextScreen.putExtra("title", list.get(position).getName());
                    nextScreen.putExtra("data", list.get(position).getData());
                    context.startActivity(nextScreen);
                    ((Activity) context).finish();
                } else if (list.get(position).getDraw().equals("yes")) {
                    Intent nextScreen = new Intent(context, Activity_Drawing.class);
                    nextScreen.putExtra("color", list.get(position).getColor());
                    nextScreen.putExtra("category", list.get(position).getCategory());
                    nextScreen.putExtra("id", Integer.toString(list.get(position).getID()));
                    nextScreen.putExtra("title", list.get(position).getName());
                    nextScreen.putExtra("data", list.get(position).getData());
                    context.startActivity(nextScreen);
                    ((Activity) context).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}