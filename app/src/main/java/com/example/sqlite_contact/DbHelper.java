package com.example.sqlite_contact;
import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;


//        we need to extend SQLiteOpenHelper class and will  name the database as userdata.db and version as 1
class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
//    this method is used for creating the DataBase Table by specifying the << Primary Keys >>
//    COLLATE NOCASE to make column caseinsensitive
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(name TEXT COLLATE NOCASE, contact TEXT primary key COLLATE NOCASE, dob TEXT COLLATE NOCASE)");
    }

    //    to upgrade the table we use DB.execSQL () method
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails");
    }

    //    to insert the data into the  table we use insertuserdata user define function
    public Boolean insertuserdata(String name, String contact, String dob) {
//        to get the writable permission on the database we use this statement
        SQLiteDatabase DB = this.getWritableDatabase();
//        we will create ContentValues and then use put() function to insert the data
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
//        this DB.insert will insert the contentValues in the table Userdetails and
//        it will return a long value and if the value returned is -1 then that value is not inserted
//        else the value will got inserted
        long result = DB.insert("Userdetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //    to update the DB we will create userdefine updatedata function and will open the file in writeable mode
    public Boolean updateuserdata(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);

//        A object for a cursor (like a pointer is made)
//        using a where query we will find the row and will change the data there
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //    to delete the data  we made a userdefine function
//    it will take only the name as an argument and here also same where clause is being used to search for the data in entire database
    public Boolean deletedata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //    the function getdata will return all the data that has been written into the file
    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;
    }


//    public Boolean searchdata(String name) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = '" + name + "'", null);
//        if (cursor.getCount() > 0) {
//            long result = DB.delete("Userdetails", "name=?", new String[]{name});
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
//    }
//

    public Cursor Searchdata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = '" + name + "'", null);
        return cursor;
    }

}