package com.LightHouse.Fitness;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.LightHouse_Fitness.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GALLERY_INTENT_CODE = 1023;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final String TAG = "GestureDemo";
    private Button button_Logout, button_userPicture;
    private ImageView userProfilePicture;
    private EditText userGoals;
    private DatabaseReference dbReff;
    private FirebaseUser fbUser;
    private FirebaseAuth fbAuth;
    private TextView userProfileName, userEmail, userAge, submitGoals;
    private StorageReference storageRef;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_profile, container, false);

        fbAuth = FirebaseAuth.getInstance();
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageRef.child("users/" + fbUser.getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(userProfilePicture);
            }
        });

        button_Logout = (Button) myView.findViewById(R.id.button_Profilelogout);
        button_userPicture = (Button) myView.findViewById(R.id.button_userImage);
        userProfileName = (TextView) myView.findViewById(R.id.textview_userProfileName);
        userEmail = (TextView) myView.findViewById(R.id.textView_userProfileEmail);
        userAge = (TextView) myView.findViewById(R.id.textView_userProfileAge);
        userProfilePicture = (ImageView) myView.findViewById(R.id.imageView_userImage);
        userGoals = (EditText) myView.findViewById(R.id.editText_multiLine_userGoals);
        submitGoals = (TextView) myView.findViewById(R.id.textView_submitGoals);

        dbReff = FirebaseDatabase.getInstance().getReference().child("Users").child(fbUser.getUid());
        dbReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null) {
                    String userDataName = snapshot.child("userName").getValue().toString();
                    String userDataAge = snapshot.child("userAge").getValue().toString();
                    String userDataEmail = snapshot.child("userEmail").getValue().toString();
                    userProfileName.setText(userDataName);
                    userEmail.setText(userDataEmail);
                    userAge.setText(String.valueOf(Integer.parseInt(userDataAge)));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbAuth.signOut();
                startActivity(new Intent(getActivity(), Login_Activity.class));
            }
        });

        button_userPicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        submitGoals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String goals = userGoals.getText().toString().trim();
                String[] split = goals.split("\n");
                dbReff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot != null) {
                            Vector<String> goals = new Vector<>();
                            DatabaseReference userReff = FirebaseDatabase.getInstance().getReference().child("Users").child(fbUser.getUid()).child("userGoals");
                            for (String goalItem: split) {
                                goals.add(goalItem);
                            }
                            userReff.setValue(goals);
                            userGoals.setText("");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }
                });
            }
        });

        return myView;
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference fileRef = storageRef.child("users/" + fbUser.getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Image Upload", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(userProfilePicture);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(), "Image Failed To Upload", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


