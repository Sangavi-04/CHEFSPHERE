package com.chefsphere

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ChefSphereDbHelper(context: Context) : SQLiteOpenHelper(context, "chefsphere.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            );
        """)
        db.execSQL("""
            CREATE TABLE profiles (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                name TEXT,
                age INTEGER,
                gender TEXT,
                height INTEGER,
                weight INTEGER,
                activity_level TEXT,
                primary_goal TEXT,
                target_weight INTEGER,
                timeframe TEXT,
                health_conditions TEXT,
                medications TEXT,
                diet_type TEXT,
                cuisine_prefs TEXT,
                allergies TEXT,
                restrictions TEXT,
                custom_allergies TEXT,
                skill_level TEXT,
                cooking_time TEXT,
                portion_size TEXT,
                completion_percent INTEGER DEFAULT 0,
                FOREIGN KEY(user_id) REFERENCES users(id)
            );
        """)
        db.execSQL("""
            CREATE TABLE history (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                recipe_name TEXT,
                cooked_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                cooking_level TEXT,
                FOREIGN KEY(user_id) REFERENCES users(id)
            );
        """)
        db.execSQL("""
            CREATE TABLE recipes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                description TEXT,
                image_url TEXT,
                nutrition_info TEXT
            );
        """)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS profiles")
        db.execSQL("DROP TABLE IF EXISTS history")
        db.execSQL("DROP TABLE IF EXISTS recipes")
        onCreate(db)
    }
}
