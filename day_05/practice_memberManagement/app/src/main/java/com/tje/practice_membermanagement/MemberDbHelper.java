package com.tje.practice_membermanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MemberDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "member.db";

    public static class MemberInfo implements BaseColumns {
        public static final String TABLE_NAME = "member";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_REGISTDATE = "registDate";
    }

    private static final String SQL_CREATE_TABLE_MEMBER =
            String.format("create table %s (%s text primary key, %s text, %s text, %s int, %s text, %s text, %s text)",
                    MemberInfo.TABLE_NAME, MemberInfo.COLUMN_NAME_ID, MemberInfo.COLUMN_NAME_PASSWORD, MemberInfo.COLUMN_NAME_NAME, MemberInfo.COLUMN_NAME_AGE, MemberInfo.COLUMN_NAME_PHONE, MemberInfo.COLUMN_NAME_ADDRESS, MemberInfo.COLUMN_NAME_REGISTDATE);

    public MemberDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MEMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table " + MemberInfo.TABLE_NAME);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MEMBER);
    }

    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 DD일 HH시 mm분 ss초", Locale.KOREA);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    public int count(String id){
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select count(*) from %s where id = ?",
                MemberInfo.TABLE_NAME);
        Cursor cursor = db.rawQuery(sql,new String[]{id});
        int r = 0 ;
        if(cursor.moveToFirst()){
            r = cursor.getInt(0);
        }
        return r;
    }

    public int count(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select count(*) from %s",
                MemberInfo.TABLE_NAME);
        Cursor cursor = db.rawQuery(sql,null);
        int r = 0 ;
        if(cursor.moveToFirst()){
            r = cursor.getInt(0);
        }
        return r;
    }

    public boolean insert(Member member){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MemberInfo.COLUMN_NAME_ID,member.getId());
        values.put(MemberInfo.COLUMN_NAME_PASSWORD,member.getPassword());
        values.put(MemberInfo.COLUMN_NAME_NAME,member.getName());
        values.put(MemberInfo.COLUMN_NAME_AGE,member.getAge());
        values.put(MemberInfo.COLUMN_NAME_PHONE,member.getPhone());
        values.put(MemberInfo.COLUMN_NAME_ADDRESS,member.getAddress());
        values.put(MemberInfo.COLUMN_NAME_REGISTDATE,getDate());

        return db.insert(MemberInfo.TABLE_NAME, null, values) > 0 ? true:false;
    }

    public ArrayList<Member> select(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Member> list = new ArrayList<>();
        String [] colums = {
                MemberInfo.COLUMN_NAME_ID,
                MemberInfo.COLUMN_NAME_PASSWORD,
                MemberInfo.COLUMN_NAME_NAME,
                MemberInfo.COLUMN_NAME_AGE,
                MemberInfo.COLUMN_NAME_PHONE,
                MemberInfo.COLUMN_NAME_ADDRESS,
                MemberInfo.COLUMN_NAME_REGISTDATE
        };
        Cursor cursor = db.query(MemberInfo.TABLE_NAME, colums, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            Member member = new Member();
            member.setId(cursor.getString(cursor.getColumnIndex(MemberInfo.COLUMN_NAME_ID)));
            member.setPassword(cursor.getString(cursor.getColumnIndex(MemberInfo.COLUMN_NAME_PASSWORD)));
            member.setName(cursor.getString(cursor.getColumnIndex(MemberInfo.COLUMN_NAME_NAME)));
            member.setAge(cursor.getInt(cursor.getColumnIndex(MemberInfo.COLUMN_NAME_AGE)));
            member.setPhone(cursor.getString(cursor.getColumnIndex(MemberInfo.COLUMN_NAME_PHONE)));
            member.setAddress(cursor.getString(cursor.getColumnIndex(MemberInfo.COLUMN_NAME_ADDRESS)));
            member.setRegistDate(cursor.getString(cursor.getColumnIndex(MemberInfo.COLUMN_NAME_REGISTDATE)));
            list.add(member);
        }
        return list;
    }
}
