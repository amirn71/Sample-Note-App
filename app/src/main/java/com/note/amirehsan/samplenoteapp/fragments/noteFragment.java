package com.note.amirehsan.samplenoteapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.note.amirehsan.samplenoteapp.Activityes.Activity_Drawing;
import com.note.amirehsan.samplenoteapp.Activityes.Activity_Note_Writing;
import com.note.amirehsan.samplenoteapp.Database.Note_Contact;
import com.note.amirehsan.samplenoteapp.Database.Note_DatabaseHandler;
import com.note.amirehsan.samplenoteapp.R;
import com.note.amirehsan.samplenoteapp.adapter.Note_ListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class noteFragment extends Fragment {

    Note_DatabaseHandler db;
    private List<Note_Contact> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Note_ListAdapter mAdapter;
    public static FloatingActionButton fab, note, draw;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    public static View v;
    private Boolean isFabOpen = false;
    public String color, categoryName;

    public noteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.take_note_fragment, container, false);
        Intent colorIntent = getActivity().getIntent();
        Intent nameIntent = getActivity().getIntent();
        color = colorIntent.getStringExtra("color");
        categoryName = nameIntent.getStringExtra("category");
        initializing_contents();
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        note = (FloatingActionButton) v.findViewById(R.id.fab_note);
        draw = (FloatingActionButton) v.findViewById(R.id.fab_draw);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getContext(), Activity_Note_Writing.class);
                nextScreen.putExtra("color", color);
                nextScreen.putExtra("category", categoryName);
                nextScreen.putExtra("id", "null");
                startActivity(nextScreen);
                getActivity().finish();
            }
        });

        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getContext(), Activity_Drawing.class);
                nextScreen.putExtra("color", color);
                nextScreen.putExtra("category", categoryName);
                nextScreen.putExtra("id", "null");
                startActivity(nextScreen);
                getActivity().finish();
            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mAdapter = new Note_ListAdapter(list, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && fab.isShown()) {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        adaptingNoteList();
        return v;
    }

    public void initializing_contents() {
        db = new Note_DatabaseHandler(getContext());
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
    }

    private void adaptingNoteList() {
        list.clear();
        List<Note_Contact> contacts = db.getAllContacts();
        for (Note_Contact cn : contacts) {
            if (categoryName.equals(cn.getCategory())) {
                Note_Contact noteContact = new Note_Contact(cn.getID(), cn.getName(), cn.getCategory(),
                        cn.getData(), cn.getDate(), cn.getColor(), cn.getDraw());
                list.add(noteContact);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void animateFAB() {
        if (isFabOpen) {
            fab.startAnimation(rotate_backward);
            draw.startAnimation(fab_close);
            note.startAnimation(fab_close);
            draw.setClickable(false);
            note.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            draw.startAnimation(fab_open);
            note.startAnimation(fab_open);
            draw.setClickable(true);
            note.setClickable(true);
            isFabOpen = true;
        }
    }
}