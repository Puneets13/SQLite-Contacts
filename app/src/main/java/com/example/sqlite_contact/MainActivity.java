package com.example.sqlite_contact;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText name, contact, dob;
    Button insert, update, delete, view , search;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        search = findViewById(R.id.button);

//        we will create iobject for DBHelper
        DB = new DBHelper(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();
// if the insertdata function will return true then we will make the toast entry inserted else not
                // to add privacy to the account use

                if (nameTXT.equals("") || contactTXT.equals("") || dobTXT.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkinsertdata = DB.insertuserdata(nameTXT, contactTXT, dobTXT);
                    if (checkinsertdata == true) {
                        Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
//                to remove the already inserted text
       name.setText("");
                contact.setText("");
                dob.setText("");
            }
        }                    );


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(nameTXT, contactTXT, dobTXT);
                if(checkupdatedata==true)
                    Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();

                name.setText("");
                contact.setText("");
                dob.setText("");
            }        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                Boolean checkudeletedata = DB.deletedata(nameTXT);
                if(checkudeletedata==true)
                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
                name.setText("");
                contact.setText("");
                dob.setText("");
            }        });

//        to search for the data
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String n  = name.getText().toString();
//           boolean searchdata = DB.searchdata(n);
//                if(searchdata==true)
//                    Toast.makeText(MainActivity.this, "Item Found", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(MainActivity.this, "Item not Found", Toast.LENGTH_SHORT).show();
//                name.setText("");
//                contact.setText("");
//                dob.setText("");
//            }
//        }
//        );

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n  = name.getText().toString();
                Cursor res1 = DB.Searchdata(n);
                if (res1.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                buffer.append("ITEM FOUND..\n");
                while (res1.moveToNext()) {
                    buffer.append("Name :" + res1.getString(0) + "\n");
                    buffer.append("Contact :" + res1.getString(1) + "\n");
                    buffer.append("Date of Birth :" + res1.getString(2) + "\n\n");
                }
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);

//             setCancelable method will close the dialogue box whenever clicked on any side on the screen
                builder1.setCancelable(true);
                builder1.setTitle("Searched Data");
                builder1.setMessage(buffer.toString());
                builder1.show();
            }        });



//        to viw the data that we have created
//        we will create the cursor DB.getdata()
//
//        and if the text exits then we will create a << String Buffer >>
//while the res cursir will have some value we will append that value in the buffer and will movetoNext()  value


//        To display the value we will create a << AlertDialog box >>

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("Contact :"+res.getString(1)+"\n");
                    buffer.append("Date of Birth :"+res.getString(2)+"\n\n");
                }

//                the buffer will then be shown over the alertBox with title UserEntries and
//                the context would be set to << MainActivity.this >>
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
             
//             setCancelable method will close the dialogue box whenever clicked on anyside on the screen
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
    }

        }

