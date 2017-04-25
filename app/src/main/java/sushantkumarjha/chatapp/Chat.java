package sushantkumarjha.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Chat extends AppCompatActivity {
   private DatabaseReference mrf;
    private FirebaseAuth mAuth;
    private FirebaseStorage mfile;
    private  StorageReference storageReference;
    LinearLayoutManager linearLayoutManager;
    String username="";
    FirebaseRecyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mrf= FirebaseDatabase.getInstance().getReference().child("User");
        DatabaseReference photo=FirebaseDatabase.getInstance().getReference().child("profile_database");
        mAuth=FirebaseAuth.getInstance();
        mfile=FirebaseStorage.getInstance();
        storageReference=mfile.getReference().child("message_photo");
        final  String Username;
        linearLayoutManager=new LinearLayoutManager(this);
        final RecyclerView recycler = (RecyclerView) findViewById(R.id.list_of_messages);
        recycler.setHasFixedSize(false);
        recycler.setLayoutManager(linearLayoutManager);
        mAdapter=new FirebaseRecyclerAdapter<Message,ChatHolder>(Message.class,R.layout.chat_row,ChatHolder.class,mrf) {
            @Override
            protected void populateViewHolder(ChatHolder viewHolder,Message model,int position) {
                viewHolder.setName(model.getName());
                viewHolder.setText(getApplicationContext(),model.getPhotoUrl(),model.getText());
                viewHolder.setTextTime(model.getTime());
                viewHolder.setProfilepic(getApplicationContext(),model.getProfieUrl());
            }
        };
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mAdapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recycler.scrollToPosition(positionStart);
                }
                else{
                    recycler.scrollToPosition(lastVisiblePosition);
                    Toast.makeText(Chat.this,"nothing",Toast.LENGTH_LONG).show();
                }
            }
        });
         recycler.setAdapter(mAdapter);
        final EditText mMessage = (EditText) findViewById(R.id.input);
        final FloatingActionButton send= (FloatingActionButton) findViewById(R.id.fab);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mrf.push().setValue(new Message(mMessage.getText().toString(),mAuth.getCurrentUser().getDisplayName(),null,mAuth.getCurrentUser().getPhotoUrl().toString()));
                mMessage.setText("");
            }
        });
      findViewById(R.id.cam).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
              intent.setType("image/*");
              intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
              startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
          }
      });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            Uri uri=data.getData();
            StorageReference file= storageReference.child(uri.getLastPathSegment());
            file.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Uri resulturi=taskSnapshot.getDownloadUrl();
                    mrf.push().setValue(new Message(null,mAuth.getCurrentUser().getDisplayName(),resulturi.toString(),mAuth.getCurrentUser().getPhotoUrl().toString()));
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.chat_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(Chat.this,SignIn.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
