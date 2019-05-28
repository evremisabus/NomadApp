package com.android.gezginapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.gezginapp.R;
import com.android.gezginapp.activities.AddNoteActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyNotesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<String> myNoteList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String myNote;
    ProgressDialog progressDialog;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyNotesFragment() {

    }

    public static MyNotesFragment newInstance(String param1, String param2) {
        MyNotesFragment fragment = new MyNotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        myNoteList=getMyNotes();
    }

    private ArrayList<String> getMyNotes() {
        showProgressDialog();
        //final yazma sebebi kodun bi daha değişmiycek olması
        final ArrayList<String> myNotes = new ArrayList<String>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("GezdigimYerler");

        databaseReference.addValueEventListener(new ValueEventListener() {
            //Tablodaki değerleri sırasıyla dönen bir method
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //Tablonun o anki görünümü
                myNotes.clear();
                progressDialog.dismiss();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                   myNote = ds.child("sehiradi").getValue().toString();
                   myNotes.add(myNote);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        return myNotes;
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_notes, container, false);
        ListView lvMyNotes = (ListView) view.findViewById(R.id.lv_my_notes);
        Button button = (Button) view.findViewById(R.id.btn_go_to_add_note);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNoteActivity.class);
                startActivity(intent);
            }
        });

       //Adapter'a mynotelist eklendi sonra set edildi.
        arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_expandable_list_item_1,
                android.R.id.text1,myNoteList);
        lvMyNotes.setAdapter(arrayAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
