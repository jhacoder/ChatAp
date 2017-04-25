package sushantkumarjha.chatapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      new CountDownTimer(2000,2000){

          @Override
          public void onTick(long l) {

          }

          @Override
          public void onFinish() {
              startActivity(new Intent(MainActivity.this,SignIn.class));
    }
      }.start();
    }
}
