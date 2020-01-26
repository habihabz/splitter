package com.example.roomsplitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{
    MyRecyclerViewAdapter adapter;
    TextView tv_room_name;
    DatePickerDialog picker;
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    File newfile;
    Room room;
    String file;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        // data to populate the RecyclerView with
        ArrayList<String> users = new ArrayList<>();
        users.add("Habeeb");
        users.add("Noushad");
        users.add("Linesh");
        users.add("Baiju");
        users.add("Azhar");

        ArrayList<String> Type = new ArrayList<>();
        Type.add("Food");
        Type.add("Net");
        Type.add("Fuel");
        Type.add("grocery");


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, users);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        /////

        Spinner spinnerUser;
        spinnerUser= (Spinner) findViewById(R.id.sp_paidBy);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, users);//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);
//if you want to set any action you can do in this listener
        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        ////


        /////

        Spinner spinnerType;
        spinnerType= (Spinner) findViewById(R.id.sp_Type);//fetch the spinner from layout file
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Type);//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
//if you want to set any action you can do in this listener
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        ////
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("rooms");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    room=postSnapshot.getValue(Room.class);
                    tv_room_name.setText(room.getRoom_name());


                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
              System.out.println("Failed to read value"+ error.toException());
            }
        });
       // String userId = myRef.push().getKey();
// creating user object
       // Member member = new Member("121345","habeeb","0559901805","abimanjeri@gmail.com",0.0);

      //  myRef.child(userId).setValue(member);
        tv_room_name=(TextView) findViewById(R.id.tv_room_name);
        final EditText eText=(EditText) findViewById(R.id.edDate);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        // Here, we are making a folder named picFolder to store
        // pics taken by the camera using this application.
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newdir = new File(dir);
        newdir.mkdirs();

        ImageButton capture = (ImageButton) findViewById(R.id.imageButton);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Here, the counter will be incremented each time, and the
                // picture taken by camera will be stored as 1.jpg,2.jpg
                // and likewise.
                count++;
                file = dir+count+".jpg";
                newfile = new File(file);
                try {
                    newfile.createNewFile();
                }
                catch (IOException e)
                {
                }

                Uri outputFileUri = Uri.fromFile(newfile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getApplicationContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.saveButton:
                saveInvoice();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
            Log.d("CameraDemo", file);
            ImageButton imageButton=(ImageButton) findViewById(R.id.imageButton);
            imageButton.setImageResource(R.drawable.invoice);

        }
    }

    public  void saveInvoice()

    {
        final EditText edDate=(EditText) findViewById(R.id.edDate) ;
        final EditText edRemarks=(EditText) findViewById(R.id.edRemarks) ;
        final EditText edAmount=(EditText) findViewById(R.id.edAmount) ;
        final Spinner spType=(Spinner) findViewById(R.id.sp_Type) ;
        final Spinner sp_paidBy=(Spinner) findViewById(R.id.sp_paidBy) ;

        String [] strings=edDate.getEditableText().toString().split("/");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final  DatabaseReference myRef = database.getReference().child("Invoices")
                .child(room.getRoom_id()+"").child(strings[2   ]+strings[1]).child(sp_paidBy.getSelectedItem().toString()+"");

        final String key = myRef.push().getKey();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(newfile);
        final StorageReference riversRef = mStorageRef.child(room.getRoom_id()+"/"+edDate.getEditableText().toString()+".jpg");

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Invoices invoices=new Invoices();
                                invoices.setInv_id(132134);
                                invoices.setInv_amount(Long.parseLong(edAmount.getEditableText().toString()));
                                invoices.setInv_remarks(edRemarks.getEditableText().toString());
                                invoices.setInv_date(edDate.getEditableText().toString());
                                invoices.setInv_paid_by(sp_paidBy.getSelectedItem().toString());
                                invoices.setInv_type(spType.getSelectedItem().toString());
                                invoices.setInv_room(room.getRoom_id());
                                invoices.setInv_doc_link( uri.toString());
                                myRef.child(key).setValue(invoices);
                                Toast.makeText(getApplicationContext(), "Saved Successfully !!", Toast.LENGTH_SHORT).show();
                                Clear();

                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
    public void Clear(){
        final EditText edDate=(EditText) findViewById(R.id.edDate) ;
        final EditText edRemarks=(EditText) findViewById(R.id.edRemarks) ;
        final EditText edAmount=(EditText) findViewById(R.id.edAmount) ;
        final Spinner spType=(Spinner) findViewById(R.id.sp_Type) ;
        final Spinner sp_paidBy=(Spinner) findViewById(R.id.sp_paidBy) ;
        edDate.setText("");
        edAmount.setText("");
        edRemarks.setText("");
        spType.setSelection(0);
        sp_paidBy.setSelection(0);
    }

}
