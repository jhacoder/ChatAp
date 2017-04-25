package sushantkumarjha.chatapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sushant kumar jha on 02-04-2017.
 */

public class ChatHolder extends RecyclerView.ViewHolder {
    private final TextView mNameField;
    private final TextView mTextField;
    private final TextView mTextTime;
    private final  ImageView mimageView;
    private final CircleImageView circleImageView;

    public ChatHolder(View itemView) {
        super(itemView);
        mNameField = (TextView) itemView.findViewById(R.id.nameid);
        mTextField = (TextView) itemView.findViewById(R.id.messageid);
        mTextTime=(TextView) itemView.findViewById(R.id.timeid);
        mimageView=(ImageView)itemView.findViewById(R.id.photourl);
        circleImageView=(CircleImageView)itemView.findViewById(R.id.imgProfilePicture);
    }

    public void setName(String name) {
        mNameField.setText(name);
    }

    public void setText(final Context context,String image, String text) {
        if (text != null) {
            mTextField.setText(text);
            mTextField.setVisibility(TextView.VISIBLE);
            mimageView.setVisibility(ImageView.GONE);
        } else {
            mTextField.setVisibility(TextView.GONE);
            mimageView.setVisibility(ImageView.VISIBLE);
                StorageReference storageReference = FirebaseStorage.getInstance()
                        .getReferenceFromUrl(image);
                storageReference.getDownloadUrl().addOnCompleteListener(
                        new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    String downloadUrl = task.getResult().toString();
                                    Picasso.with(context)
                                            .load(downloadUrl)
                                            .resize(200, 200)
                                            .into(mimageView);
                                } else {
                                    Log.d("sss","sgsgdj hdh");
                                }
                            }
                        }
                );
            }
        }
    public void setTextTime(long time){
        mTextTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",time));

    }
    public void setProfilepic(final Context context,String image) {
                            Glide.with(context)
                                    .load(image)
                                    .override(200,200)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .fitCenter()
                                    .centerCrop()
                                    .into(circleImageView);
    }
}
