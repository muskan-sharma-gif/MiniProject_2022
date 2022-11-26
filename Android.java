Android.java


package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    Button button, button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, otp1.class);
            startActivity(intent);
            finish();
        });
        button1.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, Email.class);
            startActivity(intent1);
            finish();
        });
    }
}

package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
public class otp1 extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    EditText editText;
    Button button;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp3);
        editText = findViewById(R.id.editText9);
        imageButton = findViewById(R.id.imageButton);
        countryCodePicker = (CountryCodePicker)
                findViewById(R.id.countryCodeHolder);
        countryCodePicker.registerCarrierNumberEditText(editText);
        button = findViewById(R.id.button73);
        button.setOnClickListener(v -> {
            if(editText.getText().toString().isEmpty())
            {
                Toast.makeText(otp1.this, "Enter  phone number", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent i = new Intent(otp1.this, otp2.class);
                i.putExtra("mobile", countryCodePicker.getFullNumberWithPlus().trim());
                startActivity(i);
            }
        });
        imageButton.setOnClickListener(view -> {
            Intent back = new Intent(otp1.this, MainActivity.class);
            startActivity(back);
            finish();
        });
    }
}

package com.example.android;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.TimeUnit;
public class otp2 extends AppCompatActivity {
    EditText editText;
    String phone ;
    Button button;
    String otp ;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp2);
        phone = getIntent().getStringExtra("mobile").toString();
        editText=findViewById(R.id.editText10);
        button=findViewById(R.id.button74);
        firebaseAuth = FirebaseAuth.getInstance();
        genotp();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty())
                {
                    Toast.makeText(otp2.this, "Pls Enter OTP",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(editText.getText().toString().length()!=6)
                    {
                        Toast.makeText(otp2.this, "Invalid OTP",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        PhoneAuthCredential credential =
                                PhoneAuthProvider.getCredential(otp , editText.getText().toString());
                        signInWithPhoneAuthcredential(credential);
                    }
                }
            }
        });
    }
    private void genotp()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone, 60, TimeUnit.SECONDS, this, new
                        PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull @NotNull String s,@NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                            {
                                super.onCodeSent(s, forceResendingToken);
                                otp = s;
                            }
                            @Override
                            public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthcredential(phoneAuthCredential);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e) {
                                Toast.makeText(otp2.this, "OTP mismatched",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
        );
    }
    private void signInWithPhoneAuthcredential(PhoneAuthCredential
                                                       credential)
    {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(otp2.this, "Login done",
                            Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(otp2.this , Appsmenu.class);
                    startActivity(j);
                    finish();
                }
                else
                {
                    Toast.makeText(otp2.this, "Not done",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
public class Email extends AppCompatActivity {
    EditText editText, editText1;
    ImageButton imageButton;
    Button button,button1;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        imageButton = findViewById(R.id.imageButton36);
        imageButton.setOnClickListener(view -> {
            Intent back = new Intent(Email.this, MainActivity.class);
            startActivity(back);
            finish();
        });
        editText = findViewById(R.id.editText11);
        editText1 = findViewById(R.id.editText12);
        editText1.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        button = findViewById(R.id.button77);
        button1= findViewById(R.id.button78);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth=FirebaseAuth.getInstance();
        button.setOnClickListener(v -> {
            String s1 = editText.getText().toString().trim(); //trim removesextra spaces from string
            String s2 = editText1.getText().toString();
            if(s1.isEmpty())
            {
                editText.setError("Fill username");
                return;
            }
            else {
                if (s2.isEmpty()) {
                    editText1.setError("Fill password");
                    return;
                }
            }
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(s1,s2).addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(Email.this, "login done", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(Email.this , Appsmenu.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Email.this, "Mismatch", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Email.this , Email2.class);
                    startActivity(intent1);
                    finish();
                }
            });
        });
        button1.setOnClickListener(v -> {
            Intent intent2 = new Intent(Email.this , Email2.class);
            startActivity(intent2);
            finish();
        });
    }
}

package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
public class Email2 extends AppCompatActivity {
    EditText editText, editText1;
    Button button, button1;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email2);
        editText = findViewById(R.id.editText13);
        editText1 = findViewById(R.id.editText14);
        editText1.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        button = findViewById(R.id.button79);
        button1= findViewById(R.id.button80);
        progressBar = findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(v -> {
            String s1 = editText.getText().toString().trim(); //trim removesextra spaces from string
            String s2 = editText1.getText().toString();
            if(s1.isEmpty())
            {
                editText.setError("Fill username");
                return;
            }
            else
            {
                if(s2.isEmpty())
                {
                    editText1.setError("Fill password");
                    return;
                }
            }
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(s1,s2).addOnCompleteListener(task
                    -> {
                if(task.isSuccessful())
                {
                    Toast.makeText(Email2.this, "Registration done",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(Email2.this , Email.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Email2.this, "User already exist",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent1 = new Intent(Email2.this , Email.class);
                    startActivity(intent1);
                }
                finish();
            });
        });
        button1.setOnClickListener(v -> {
            Intent intent2 = new Intent(Email2.this , Email.class);
            startActivity(intent2);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.google.firebase.auth.FirebaseAuth;
public class Appsmenu extends AppCompatActivity {
    Button button, button1, button2,  button4, button5;
    ImageButton imageButton;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appsmenu);
        button = findViewById(R.id.button2);
        button1 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button4);
        button4 = findViewById(R.id.button6);
        button5 = findViewById(R.id.button76);
        firebaseAuth = FirebaseAuth.getInstance();
        imageButton = findViewById(R.id.imageButton);
        button.setOnClickListener(view ->
        {
            Intent i1= new Intent(Appsmenu.this,TextToSpe.class);
            startActivity(i1);
            finish();
        });
        button1.setOnClickListener(view -> {
            Intent intent1 = new Intent(Appsmenu.this, Mobiletools.class);
            startActivity(intent1);
            finish();
        });
        button2.setOnClickListener(view -> {
            Intent intent2 = new Intent(Appsmenu.this, Sensors.class);
            startActivity(intent2);
            finish();
        });
        button4.setOnClickListener(view -> {
            Intent intent4 = new Intent(Appsmenu.this, Moreapps.class);
            startActivity(intent4);
            finish();
        });
        button5.setOnClickListener(view -> {
            firebaseAuth.signOut();
            Intent intent5 = new Intent(Appsmenu.this, MainActivity.class);
            startActivity(intent5);
            finish();
        });
        imageButton.setOnClickListener(view -> {
            Intent back = new Intent(Appsmenu.this, Email
                    .class);
            startActivity(back);
            finish();
        });
    }
}

package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.google.firebase.auth.FirebaseAuth;
public class Appsmenu extends AppCompatActivity {
    Button button, button1, button2,  button4, button5;
    ImageButton imageButton;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appsmenu);
        button = findViewById(R.id.button2);
        button1 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button4);
        button4 = findViewById(R.id.button6);
        button5 = findViewById(R.id.button76);
        firebaseAuth = FirebaseAuth.getInstance();
        imageButton = findViewById(R.id.imageButton);
        button.setOnClickListener(view ->
        {
            Intent i1= new Intent(Appsmenu.this,TextToSpe.class);
            startActivity(i1);
            finish();
        });
        button1.setOnClickListener(view -> {
            Intent intent1 = new Intent(Appsmenu.this, Mobiletools.class);
            startActivity(intent1);
            finish();
        });
        button2.setOnClickListener(view -> {
            Intent intent2 = new Intent(Appsmenu.this, Sensors.class);
            startActivity(intent2);
            finish();
        });
        button4.setOnClickListener(view -> {
            Intent intent4 = new Intent(Appsmenu.this, Moreapps.class);
            startActivity(intent4);
            finish();
        });
        button5.setOnClickListener(view -> {
            firebaseAuth.signOut();
            Intent intent5 = new Intent(Appsmenu.this, MainActivity.class);
            startActivity(intent5);
            finish();
        });
        imageButton.setOnClickListener(view -> {
            Intent back = new Intent(Appsmenu.this, Email
                    .class);
            startActivity(back);
            finish();
        });
    }
}

package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.Locale;
public class TextToSpe extends AppCompatActivity {
    ImageButton ib;
    Button b1;
    EditText e1;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_spe);
        ib =(ImageButton) findViewById(R.id.imageButton2);
        b1 = (Button) findViewById(R.id.button7);
        e1 =(EditText) findViewById(R.id.editText);
        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.ENGLISH);
                textToSpeech.setSpeechRate(0.9f);
            }
        });
        b1.setOnClickListener(view -> {
            String s1 = e1.getText().toString();
            textToSpeech.speak(s1,TextToSpeech.QUEUE_FLUSH,null);
        });
        ib.setOnClickListener(view -> {
            Intent i = new Intent(TextToSpe.this, Appsmenu.class);
            startActivity(i);
            finish();
        });
    }
}

package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
public class Mobiletools extends AppCompatActivity {
    Button button, button1, button2, button3, button4;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobiletools);
        button = findViewById(R.id.button15);
        button4 = findViewById(R.id.button19);
        imageButton = findViewById(R.id.imageButton9);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(Mobiletools.this, Bluetooth.class);
            startActivity(intent);
            finish();
        });
        button4.setOnClickListener(view -> {
            Intent intent4 = new Intent(Mobiletools.this, Torch.class);
            startActivity(intent4);
            finish();
        });
        imageButton.setOnClickListener(view -> {
            Intent back = new Intent(Mobiletools.this, Appsmenu.class);
            startActivity(back);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
public class Bluetooth extends AppCompatActivity {
    Button button, button1;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        button1 = findViewById(R.id.button33);
        imageButton = findViewById(R.id.imageButton22);
        button1.setOnClickListener(v -> {
            Intent intent1 = new Intent(Bluetooth.this,Bluewithbutton.class);
            startActivity(intent1);
            finish();
        });

        imageButton.setOnClickListener(v -> {
            Intent back = new Intent(Bluetooth.this, Mobiletools.class);
            startActivity(back);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
public class Bluewithbutton extends AppCompatActivity {
    Button button, button1;
    ImageButton imageButton;
    BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluewithbutton);
        button = findViewById(R.id.button35);
        button1 = findViewById(R.id.button36);
        imageButton = findViewById(R.id.imageButton24);
        bluetoothAdapter =BluetoothAdapter.getDefaultAdapter();
        button.setOnClickListener(view -> bluetoothAdapter.enable());
        button1.setOnClickListener(view -> bluetoothAdapter.disable());
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Bluewithbutton.this, Bluetooth.class);
            startActivity(intent);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
public class Torch extends AppCompatActivity {
    Button button, button1, button2;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torch);
        button1 = findViewById(R.id.button23);
        button2 = findViewById(R.id.button24);
        imageButton = findViewById(R.id.imageButton12);
        button1.setOnClickListener(v -> {
            Intent intent1 = new Intent(Torch.this, Torwithbutton.class);
            startActivity(intent1);
            finish();
        });
        button2.setOnClickListener(v -> {
            Intent intent2 = new Intent(Torch.this, Torwithtoggle.class);
            startActivity(intent2);
            finish();
        });
        imageButton.setOnClickListener(v -> {
            Intent intent3 = new Intent(Torch.this, Mobiletools.class);
            startActivity(intent3);
            finish();
        });
    }
}
package com.example.android;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
public class Torwithbutton extends AppCompatActivity {
    Button button, button1;
    ImageButton imageButton;
    CameraManager cameraManager;
    private boolean torch = false;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torwithbutton);
        button = findViewById(R.id.button25);
        button1 = findViewById(R.id.button26);
        imageButton = findViewById(R.id.imageButton14);
        cameraManager = (CameraManager)getSystemService(CAMERA_SERVICE);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                try {
                    String id = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(id, true);
                    torch = true;
                }
                catch (CameraAccessException e){
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                try {
                    String id = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(id, false);
                    torch = false;
                } catch (CameraAccessException ignored) {
                }
            }
        });
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Torwithbutton.this, Torch.class);
            startActivity(intent);
            finish();
        });
    }
}
package com.example.android;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
public class Torwithtoggle extends AppCompatActivity {
    ImageButton imageButton;
    ToggleButton toggleButton;
    CameraManager cameraManager;
    private boolean torch = false;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torwithtoggle);
        imageButton = findViewById(R.id.imageButton13);
        toggleButton = findViewById(R.id.toggleButton);
        cameraManager = (CameraManager)getSystemService(CAMERA_SERVICE);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,
                                         boolean b) {
                if(b){
                    try{
                        String id = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(id,true);
                        Toast.makeText(Torwithtoggle.this, "Torch is on",
                                Toast.LENGTH_SHORT).show();
                    }
                    catch (CameraAccessException ignored){
                    }
                }
                else{
                    try{
                        String id = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(id,false);
                        Toast.makeText(Torwithtoggle.this, "Torch is off",
                                Toast.LENGTH_SHORT).show();
                    }catch (CameraAccessException ignored){
                    }
                }
            }
        });
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Torwithtoggle.this, Torch.class);
            startActivity(intent);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
public class Sensors extends AppCompatActivity {
    Button button, button1, button2, button3;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        button = findViewById(R.id.button11);
        button2 = findViewById(R.id.button13);
        button3 = findViewById(R.id.button14);
        imageButton = findViewById(R.id.imageButton3);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(Sensors.this, Lightsensor.class);
            startActivity(intent);
            finish();
        });
        button2.setOnClickListener(view -> {
            Intent intent2 = new Intent(Sensors.this, Tiltsensor.class);
            startActivity(intent2);
            finish();
        });
        button3.setOnClickListener(view -> {
            Intent intent3 = new Intent(Sensors.this, Torchsensor.class);
            startActivity(intent3);
            finish();
        });
        imageButton.setOnClickListener(view -> {
            Intent back = new Intent(Sensors.this, Appsmenu.class);
            startActivity(back);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
public class Lightsensor extends AppCompatActivity implements SensorEventListener {
    ImageView imageView;
    MediaPlayer mediaPlayer;
    SensorManager sensorManager;
    ImageButton imageButton;
    Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsensor);
        imageView = findViewById(R.id.imageView);
        imageButton = findViewById(R.id.imageButton5);
        imageButton.setOnClickListener(v -> {
            mediaPlayer.pause();
            Intent intent = new Intent(Lightsensor.this, com.example.android.Sensors.class);
            startActivity(intent);
            finish();
        });
        mediaPlayer = MediaPlayer.create(this, R.raw.y);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.values[2]>3)
        {
            mediaPlayer.start();
            imageView.setImageResource(R.drawable.on);
        }
        else
        {
            mediaPlayer.pause();
            imageView.setImageResource(R.drawable.off);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
public class Tiltsensor extends AppCompatActivity implements
        SensorEventListener {
    MediaPlayer mediaPlayer;
    ImageView imageView;
    SensorManager sensorManager;
    Sensor sensor;
    ImageButton imageButton;
    TextView textView,textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiltsensor);
        mediaPlayer = MediaPlayer.create(this, R.raw.y);
        imageView = findViewById(R.id.imageView3);
        imageButton = findViewById(R.id.imageButton7);
        imageButton.setOnClickListener(v -> {
            mediaPlayer.pause();
            Intent intent = new Intent(Tiltsensor.this, com.example.android.Sensors.class);
            startActivity(intent);
            finish();
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        textView = findViewById(R.id.textView8);
        textView1 = findViewById(R.id.textView9);
        textView2 = findViewById(R.id.textView10);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x1 = sensorEvent.values[0];
        float y1 = sensorEvent.values[1];
        float z1 = sensorEvent.values[2];
        String s1 = Float.toString(x1);
        String s2 = Float.toString(y1);
        String s3 = Float.toString(z1);
        textView.setText(s1);
        textView1.setText(s2);
        textView2.setText(s3);
        int x = (int)x1;
        int y = (int)y1;
        int z = (int)z1;
        if(z!=0) //It means position change hua hai
        {
            mediaPlayer.start();
            imageView.setImageResource(R.drawable.on);
        }
        else
        {
            mediaPlayer.pause();
            imageView.setImageResource(R.drawable.off);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
package com.example.android;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.TargetApi;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Torchsensor extends AppCompatActivity implements
        SensorEventListener {
    ImageView imageView;
    SensorManager sensorManager;
    Sensor sensor;
    ImageButton imageButton;
    CameraManager cameraManager;
    private boolean torch = false;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torchsensor);
        imageView = findViewById(R.id.imageView4);
        imageButton = findViewById(R.id.imageButton8);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Torchsensor.this, com.example.android.Sensors.class);
            startActivity(intent);
            finish();
        });
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor , SensorManager.SENSOR_DELAY_NORMAL);
        cameraManager = (CameraManager)getSystemService(CAMERA_SERVICE);
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x1 = sensorEvent.values[0];
        float y1 = sensorEvent.values[1];
        float z1 = sensorEvent.values[2];
        int x = (int) x1;
        int y = (int) y1;
        int z = (int) z1;
        if (x != 0) //mtlb position change hua hai
        {
            try
            {
                String id = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(id , true);
                torch = true;
                imageView.setImageResource(R.drawable.on);
            }
            catch(CameraAccessException e) {
            }
        }
        else
        {
            try
            {
                String id = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(id,false);
                torch = false;
                imageView.setImageResource(R.drawable.off);
            }
            catch(CameraAccessException e)
            {
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
public class Moreapps extends AppCompatActivity {
    Button button, button1, button2, button3, button4, button5;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreapps);
        button = findViewById(R.id.button37);
        button1 = findViewById(R.id.button38);
        button2 = findViewById(R.id.button39);
        button3 = findViewById(R.id.button40);
        button4 = findViewById(R.id.button41);
        button5 = findViewById(R.id.button71);
        imageButton = findViewById(R.id.imageButton27);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Moreapps.this, MediaPlayer.class);
            startActivity(intent);
            finish();
        });
        button1.setOnClickListener(v -> {
            Intent intent1 = new Intent(Moreapps.this, VideoPlayer.class);
            startActivity(intent1);
            finish();
        });
        button2.setOnClickListener(v -> {
            Intent intent2 = new Intent(Moreapps.this, Webbrowser.class);
            startActivity(intent2);
            finish();
        });
        button3.setOnClickListener(v -> {
            Intent intent3 = new Intent(Moreapps.this, Calculator.class);
            startActivity(intent3);
            finish();
        });
        button4.setOnClickListener(v -> {
            Intent intent4 = new Intent(Moreapps.this, Vibrate.class);
            startActivity(intent4);
            finish();
        });
        button5.setOnClickListener(v -> {
            Intent intent4 = new Intent(Moreapps.this, Realtime.class);
            startActivity(intent4);
            finish();
        });
        imageButton.setOnClickListener(v -> {
            Intent back = new Intent(Moreapps.this, Appsmenu.class);
            startActivity(back);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MediaPlayer extends AppCompatActivity {
    Button button, button1, button2;
    android.media.MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        button = findViewById(R.id.button44);
        button1 = findViewById(R.id.button45);
        button2 = findViewById(R.id.button46);
        mediaPlayer = android.media.MediaPlayer.create(this, R.raw.b2);
        button.setOnClickListener(view -> mediaPlayer.start());
        button1.setOnClickListener(view -> mediaPlayer.pause());
        button2.setOnClickListener(v -> {
            mediaPlayer.pause();
            Intent intent = new Intent(MediaPlayer.this, MediaPlayer1.class);
            startActivity(intent);
            finish();
        });

    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
public class MediaPlayer1 extends AppCompatActivity {
    Button button, button1, button2;
    ImageButton imageButton;
    android.media.MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player1);
        button = findViewById(R.id.button47);
        button2 = findViewById(R.id.button48);
        button1 = findViewById(R.id.button49);
        imageButton = findViewById(R.id.imageButton30);
        mediaPlayer = android.media.MediaPlayer.create(this, R.raw.b1);
        button.setOnClickListener(view -> mediaPlayer.start());
        button1.setOnClickListener(view -> mediaPlayer.pause());
        button2.setOnClickListener(v -> {
            mediaPlayer.pause();
            Intent intent = new Intent(MediaPlayer1.this, MediaPlayer.class);
            startActivity(intent);
            finish();
        });
        imageButton.setOnClickListener(v -> {
            mediaPlayer.pause();
            Intent back = new Intent(MediaPlayer1.this,Moreapps.class);
            startActivity(back);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Realtime extends AppCompatActivity {
    EditText editText, editText1, editText2, editText3;
    Button button;
    ImageButton imageButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);
        editText = findViewById(R.id.editText5);
        editText1 = findViewById(R.id.editText6);
        editText1.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText2 = findViewById(R.id.editText7);
        editText3 = findViewById(R.id.editText8);
        button = findViewById(R.id.button72);
        imageButton = findViewById(R.id.imageButton33);
        firebaseDatabase = FirebaseDatabase.getInstance();
        button.setOnClickListener(v -> {
            databaseReference = firebaseDatabase.getReference("users");
            String s1 = editText.getText().toString();
            String s2 = editText1.getText().toString();
            String s3 = editText2.getText().toString();
            String s4 = editText3.getText().toString();
            if(s4.length() != 10)
            {
                Toast.makeText(Realtime.this, "enter valid phone",Toast.LENGTH_SHORT).show();
            }
            else {
                users users = new users(s1,s2,s3,s4);
                databaseReference.child(s4).setValue(users); //to fix this value
                Toast.makeText(Realtime.this, "Database completed",
                        Toast.LENGTH_SHORT).show();
            }
        });
        imageButton.setOnClickListener(view -> {
            Intent back = new Intent(Realtime.this, Moreapps.class);
            startActivity(back);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
public class VideoPlayer extends AppCompatActivity {
    Button button;
    VideoView videoView;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        button = findViewById(R.id.button50);
        videoView = findViewById(R.id.videoView2);
        mediaController = new MediaController(this);
        videoView.setVideoPath("android.resource://" +getPackageName()+ "/" +R.raw.video1);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
        button.setOnClickListener(v -> {
            Intent intent = new Intent(VideoPlayer.this, VideoPlayer1.class);
            startActivity(intent);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;
public class VideoPlayer1 extends AppCompatActivity {
    Button button;
    VideoView videoView;
    ImageButton imageButton;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player1);
        button = findViewById(R.id.button51);
        imageButton = findViewById(R.id.imageButton31);
        videoView = findViewById(R.id.videoView3);
        mediaController = new MediaController(this);
        videoView.setVideoPath("android.resource://" +getPackageName()+ "/" +R.raw.video2);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
        button.setOnClickListener(v -> {
            Intent intent = new Intent(VideoPlayer1.this, VideoPlayer.class);
            startActivity(intent);
            finish();
        });
        imageButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(VideoPlayer1.this, Moreapps.class);
            startActivity(intent1);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
public class Webbrowser extends AppCompatActivity {
    Button button;
    ImageButton imageButton;
    EditText editText;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webbrowser);
        button = findViewById(R.id.button43);
        imageButton = findViewById(R.id.imageButton29);
        imageButton.setOnClickListener(v -> {
            Intent back = new Intent(Webbrowser.this, Moreapps.class);
            startActivity(back);
            finish();
        });
        editText = findViewById(R.id.editText2);
        webView = findViewById(R.id.webView);
        button.setOnClickListener(view -> {
            String string = editText.getText().toString();
            webView.loadUrl(string);
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Locale;
public class Calculator extends AppCompatActivity {
    EditText editText, editText1;
    TextView textView;
    ImageButton imageButton;
    Button button, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, button10, button11, button12, button13, button14,
            button15, button16, button17, button18;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        editText = findViewById(R.id.editText3);
        editText1 = findViewById(R.id.editText4);
        textView = findViewById(R.id.textView11);
        imageButton = findViewById(R.id.imageButton32);
        button = findViewById(R.id.button52);
        button1 = findViewById(R.id.button53);
        button2 = findViewById(R.id.button54);
        button3 = findViewById(R.id.button55);
        button4 = findViewById(R.id.button56);
        button5 = findViewById(R.id.button57);
        button6 = findViewById(R.id.button58);
        button7 = findViewById(R.id.button59);
        button8 = findViewById(R.id.button60);
        button9 = findViewById(R.id.button61);
        button10 = findViewById(R.id.button62);
        button11 = findViewById(R.id.button63);
        button12 = findViewById(R.id.button64);
        button13 = findViewById(R.id.button65);
        button14 = findViewById(R.id.button66);
        button15 = findViewById(R.id.button67);
        button16 = findViewById(R.id.button68);
        button17 = findViewById(R.id.button69);
        button18 = findViewById(R.id.button70);
        textToSpeech = new TextToSpeech(this, i -> {
            textToSpeech.setLanguage(Locale.ENGLISH);
            textToSpeech.setSpeechRate(0.8f);
        });
        editText.setOnClickListener(view -> {
            button.setOnClickListener(view14 ->
                    editText.setText(editText.getText() + "1"));
            button1.setOnClickListener(view121 ->
                    editText.setText(editText.getText() + "2"));
            button2.setOnClickListener(view117 ->
                    editText.setText(editText.getText() + "3"));
            button3.setOnClickListener(view110 ->
                    editText.setText(editText.getText() + "4"));
            button4.setOnClickListener(view123 ->
                    editText.setText(editText.getText() + "5"));
            button5.setOnClickListener(view19 ->
                    editText.setText(editText.getText() + "6"));
            button6.setOnClickListener(view118 ->
                    editText.setText(editText.getText() + "7"));
            button7.setOnClickListener(view124 ->
                    editText.setText(editText.getText() + "8"));
            button8.setOnClickListener(view13 ->
                    editText.setText(editText.getText() + "9"));
            button9.setOnClickListener(view120 ->
                    editText.setText(editText.getText() + "0"));
            button10.setOnClickListener(view18 ->
                    editText.setText(editText.getText() + "."));
            button11.setOnClickListener(view122 -> editText.setText(""));
        });
        editText1.setOnClickListener(view -> {
            button.setOnClickListener(view116 ->
                    editText1.setText(editText1.getText() + "1"));
            button1.setOnClickListener(view15 ->
                    editText1.setText(editText1.getText() + "2"));
            button2.setOnClickListener(view17 ->
                    editText1.setText(editText1.getText() + "3"));
            button3.setOnClickListener(view16 ->
                    editText1.setText(editText1.getText() + "4"));
            button4.setOnClickListener(view12 ->
                    editText1.setText(editText1.getText() + "5"));
            button5.setOnClickListener(view119 ->
                    editText1.setText(editText1.getText() + "6"));
            button6.setOnClickListener(view111 ->
                    editText1.setText(editText1.getText() + "7"));
            button7.setOnClickListener(view113 ->
                    editText1.setText(editText1.getText() + "8"));
            button8.setOnClickListener(view114 ->
                    editText1.setText(editText1.getText() + "9"));
            button9.setOnClickListener(view1 ->
                    editText1.setText(editText1.getText() + "0"));
            button10.setOnClickListener(view115 ->
                    editText1.setText(editText1.getText() + "."));
            button11.setOnClickListener(view112 -> editText1.setText(""));
        });
        button12.setOnClickListener(view -> {
            String s1 = editText.getText().toString();
            String s2 = editText1.getText().toString();
            Float f1 = Float.parseFloat(s1);
            Float f2 = Float.parseFloat(s2);
            float f3 = f1 + f2;
            String s3 = Float.toString(f3);
            textView.setText(s3);
            textToSpeech.speak(s3, TextToSpeech.QUEUE_FLUSH, null);
        });
        button13.setOnClickListener(view -> {
            String s1 = editText.getText().toString();
            String s2 = editText1.getText().toString();
            Float f1 = Float.parseFloat(s1);
            Float f2 = Float.parseFloat(s2);
            float f3 = f1 - f2;
            String s3 = Float.toString(f3);
            textView.setText(s3);
            textToSpeech.speak(s3, TextToSpeech.QUEUE_FLUSH, null);
        });
        button14.setOnClickListener(view -> {
            String s1 = editText.getText().toString();
            String s2 = editText1.getText().toString();
            Float f1 = Float.parseFloat(s1);
            Float f2 = Float.parseFloat(s2);
            float f3 = f1 * f2;
            String s3 = Float.toString(f3);
            textView.setText(s3);
            textToSpeech.speak(s3, TextToSpeech.QUEUE_FLUSH, null);
        });
        button15.setOnClickListener(view -> {
            String s1 = editText.getText().toString();
            String s2 = editText1.getText().toString();
            Float f1 = Float.parseFloat(s1);
            Float f2 = Float.parseFloat(s2);
            float f3 = f1 / f2;
            String s3 = Float.toString(f3);
            textView.setText(s3);
            textToSpeech.speak(s3, TextToSpeech.QUEUE_FLUSH, null);
        });
        button16.setOnClickListener(view -> {
            String s1 = editText.getText().toString();
            double d1 = Double.parseDouble(s1);
            double a = Math.toRadians(d1);
            double c = Math.sin(a);
            String s3 = Double.toString(c);
            textView.setText(s3);
            textToSpeech.speak(s3, TextToSpeech.QUEUE_FLUSH, null);
        });
        button17.setOnClickListener(view -> {
            String s1 = editText.getText().toString();
            double d1 = Double.parseDouble(s1);
            double a = Math.toRadians(d1);
            double c = Math.cos(a);
            String s3 = Double.toString(c);
            textView.setText(s3);
            textToSpeech.speak(s3, TextToSpeech.QUEUE_FLUSH, null);
        });
        button18.setOnClickListener(view -> {
            String s1 = editText.getText().toString();
            double d1 = Double.parseDouble(s1);
            double a = Math.toRadians(d1);
            double c = Math.tan(a);
            String s3 = Double.toString(c);
            textView.setText(s3);
            textToSpeech.speak(s3, TextToSpeech.QUEUE_FLUSH, null);
        });
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Calculator.this, Moreapps.class);
            startActivity(intent);
            finish();
        });
    }
}
package com.example.android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.ImageButton;
public class Vibrate extends AppCompatActivity {
    Button button;
    Vibrator vib;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrate);
        button = findViewById(R.id.button42);
        imageButton = findViewById(R.id.imageButton28);
        vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        button.setOnClickListener(v -> vib.vibrate(2000));
        imageButton.setOnClickListener(v -> {
            Intent back = new Intent(Vibrate.this, Moreapps.class);
            startActivity(back);
            finish();
        });
    }
}
