/*
 * Copyright (C) 2013 TestUrSelf Project 
 *
 * Guillaume BALAS - Thomas Baccelli
 *
 * Licensed under the Creative Commons BY-NC-ND.
 * You may obtain a copy of the License at  * 
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.supinfo.testurself.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenDataBaseHelper extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "testurself.db";

	public static String PACKAGE_TABLE = "package";

	public static String COLUMN_COURSE_CODE = "course_code";
	public static String COLUMN_COURSE_NAME = "course_name";
	public static String COLUMN_VERSION = "version";

	private static int DATABASE_VERSION = 1;

	private static String CREATE_PACKAGE_TABLE = "CREATE TABLE " + PACKAGE_TABLE + " (" + COLUMN_COURSE_CODE
			+ " TEXT PRIMARY KEY," + COLUMN_COURSE_NAME + " TEXT NOT NULL," + COLUMN_VERSION + " INTEGER NOT NULL);";

	public OpenDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create table
		db.execSQL(CREATE_PACKAGE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// drop all table
		db.execSQL("DROP TABLE IF EXISTS" + PACKAGE_TABLE + ";");

		// re create
		this.onCreate(db);

	}

}
