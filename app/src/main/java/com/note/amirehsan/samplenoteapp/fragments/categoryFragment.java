package com.note.amirehsan.samplenoteapp.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.note.amirehsan.samplenoteapp.Activityes.Activity_Category;
import com.note.amirehsan.samplenoteapp.Database.Category_Contact;
import com.note.amirehsan.samplenoteapp.Database.Category_DatabaseHandler;
import com.note.amirehsan.samplenoteapp.Units.Persian_date;
import com.note.amirehsan.samplenoteapp.R;
import com.note.amirehsan.samplenoteapp.adapter.Category_ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class categoryFragment extends Fragment {


    Category_DatabaseHandler db;
    private List<Category_Contact> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Category_ListAdapter mAdapter;
    FloatingActionButton fab;
    View v;

    /////////////create_category_dialog/////////////////
    AlertDialog.Builder dialog;
    AlertDialog alertDialog;
    View s;
    EditText name;
    RelativeLayout green, red, blue, purple;
    TextView color_green, color_red, color_blue, color_purple;
    TextView confirm;

    public categoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.category_fragment, container, false);
        s = inflater.inflate(R.layout.category_dialog, container, false);
        initializing_contents();

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color_green.setVisibility(View.VISIBLE);
                color_red.setVisibility(View.GONE);
                color_blue.setVisibility(View.GONE);
                color_purple.setVisibility(View.GONE);
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color_green.setVisibility(View.GONE);
                color_red.setVisibility(View.VISIBLE);
                color_blue.setVisibility(View.GONE);
                color_purple.setVisibility(View.GONE);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color_green.setVisibility(View.GONE);
                color_red.setVisibility(View.GONE);
                color_blue.setVisibility(View.VISIBLE);
                color_purple.setVisibility(View.GONE);
            }
        });

        purple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color_green.setVisibility(View.GONE);
                color_red.setVisibility(View.GONE);
                color_blue.setVisibility(View.GONE);
                color_purple.setVisibility(View.VISIBLE);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (name.getText().toString() != null && !name.getText().toString().isEmpty()) {
                    Time now = new Time();
                    now.setToNow();
                    String color = new String();
                    if (color_green.getVisibility() == View.VISIBLE) {
                        color = "green";
                    } else if (color_red.getVisibility() == View.VISIBLE) {
                        color = "red";
                    } else if (color_blue.getVisibility() == View.VISIBLE) {
                        color = "blue";
                    } else if (color_purple.getVisibility() == View.VISIBLE) {
                        color = "purple";
                    }
                    db.addContact(new Category_Contact(name.getText().toString(),
                            Persian_date.getCurrentShamsidate() + " - " + now.format("%k:%M")
                            , color));
                    adaptingCategoryList();
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    resetEleman();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please add name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mAdapter = new Category_ListAdapter(list, getActivity());
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
        adaptingCategoryList();
        return v;
    }

    public void initializing_contents() {
        db = new Category_DatabaseHandler(getContext());
        dialog = new AlertDialog.Builder(getContext());
        alertDialog = dialog.create();
        dialog.create();
        alertDialog.setCustomTitle(s);
        green = (RelativeLayout) s.findViewById(R.id.color);
        red = (RelativeLayout) s.findViewById(R.id.view1);
        blue = (RelativeLayout) s.findViewById(R.id.view2);
        purple = (RelativeLayout) s.findViewById(R.id.view3);
        name = (EditText) s.findViewById(R.id.name);
        confirm = (TextView) s.findViewById(R.id.confirm);
        color_green = (TextView) s.findViewById(R.id.choise);
        color_red = (TextView) s.findViewById(R.id.choise2);
        color_blue = (TextView) s.findViewById(R.id.choise3);
        color_purple = (TextView) s.findViewById(R.id.choise4);
        ((Activity_Category) getActivity()).setFragmentRefreshListener(new Activity_Category.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                adaptingCategoryList();
            }
        });
    }

    public void resetEleman() {
        name.setText("");
        color_green.setVisibility(View.VISIBLE);
        color_red.setVisibility(View.GONE);
        color_blue.setVisibility(View.GONE);
        color_purple.setVisibility(View.GONE);
    }

    private void adaptingCategoryList() {
        list.clear();
        List<Category_Contact> contacts = db.getAllContacts();
        for (Category_Contact cn : contacts) {
            Category_Contact contact = new Category_Contact(cn.getID(), cn.getName(),
                    cn.getDate(), cn.getColor());
            list.add(contact);
        }
        mAdapter.notifyDataSetChanged();
    }
}