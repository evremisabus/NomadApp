package com.android.gezginapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.gezginapp.R;
import com.android.gezginapp.adapters.CustomPostAdapter;
import com.android.gezginapp.models.PostModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<PostModel> postList = new ArrayList<PostModel>() ;
    CustomPostAdapter customPostAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }
    
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ListView lvPostList = view.findViewById(R.id.post_list);

        PostModel postModel1 = new PostModel(R.drawable.foto1,"Trabzon","Karadeniz'in incisi olarak tabir edilen nefes kesen şehir" );
        PostModel postModel2 = new PostModel(R.drawable.foto2,"Mardin","Dicle ve Fırat nehirleri arasında yer alan en çok merak edilen şehirlerdendir");
        PostModel postModel3 = new PostModel(R.drawable.foto3,"İzmir","Ege'nin incisi izmir");
        PostModel postModel4 = new PostModel(R.drawable.foto4,"İstanbul","Ülkenin en kalabalık, ekonomik, tarihi ve sosyo-kültürel açıdan en önemli şehridir");

        postList.add(postModel1);
        postList.add(postModel2);
        postList.add(postModel3);
        postList.add(postModel4);

        //ekleme işlemi bu şekilde de yapılabilir
        //postList.add(PostModel postModel4 = new PostModel(R.drawable.foto4,"İstanbul","Ülkenin en kalabalık, ekonomik, tarihi ve sosyo-kültürel açıdan en önemli şehridir");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("GezecegimYerler");
        String placeId = databaseReference.push().getKey();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();




        for (PostModel postModel:postList) {
            databaseReference.child(placeId).child("baslik").setValue(postModel.getPostName());
            databaseReference.child(placeId).child("baslik").child(postModel.getPostName()).setValue(postModel.getPostDescription());

        }
        ImageView ımageView = (ImageView) view.findViewById(R.id.post_picture);

        CustomPostAdapter customPostAdapter = new CustomPostAdapter(getLayoutInflater(),postList);
        lvPostList.setAdapter(customPostAdapter);

        lvPostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bilgiler");
                String selectedName = postList.get(position).getPostName();
                String selectedDescription = postList.get(position).getPostDescription();
                int selectedPicture = postList.get(position).getPostPicture();

                String message= selectedName+""+selectedDescription;
                builder.setIcon(selectedPicture);
                builder.setMessage(message);
                builder.setNegativeButton("TAMAM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
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
