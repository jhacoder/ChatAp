package sushantkumarjha.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Welcome extends AppCompatActivity {
    private ImageView mCropImageView;
    private CircleImageView circleImageView;
    private Uri mCropImageUri;
    private EditText nameid, phoneid;
    private FirebaseAuth mAuth;
    private DatabaseReference mr;
    private StorageReference storageReference;
    private Uri resuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        mr = FirebaseDatabase.getInstance().getReference().child("profile_database");
        storageReference = FirebaseStorage.getInstance().getReference();
        mCropImageView = (ImageView) findViewById(R.id.cropImageView);
        mCropImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        nameid = (EditText) findViewById(R.id.name_id);
        phoneid = (EditText) findViewById(R.id.number_id);
        nameid.setText(mAuth.getCurrentUser().getDisplayName());
        Glide.with(Welcome.this)
                .load(mAuth.getCurrentUser().getPhotoUrl().toString())
                .into(mCropImageView);
        findViewById(R.id.finishid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(nameid.getText().toString()) && !TextUtils.isEmpty(phoneid.getText().toString())) {
                    startActivity(new Intent(Welcome.this, Chat.class));
                } else {
                    Toast.makeText(Welcome.this, "awerrr", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mCropImageUri = data.getData();
            Glide.with(Welcome.this)
                    .load(mCropImageUri.toString())
                    .into(mCropImageView);
            StorageReference path = storageReference.child("image").child(mCropImageUri.getLastPathSegment());
            path.putFile(mCropImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    resuri = taskSnapshot.getDownloadUrl();
                    mr.child(mAuth.getCurrentUser().getUid()).child("name").setValue(mAuth.getCurrentUser().getDisplayName());
                    mr.child(mAuth.getCurrentUser().getUid()).child("phone").setValue(phoneid.getText().toString());
                    mr.child(mAuth.getCurrentUser().getUid()).child("image").setValue(resuri.toString());

                }
            });
        }
    }
}
