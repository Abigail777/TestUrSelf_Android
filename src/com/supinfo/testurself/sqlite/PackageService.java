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

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.supinfo.testurself.beans.Package;

public class PackageService {

	private OpenDataBaseHelper openHelper;
	private SQLiteDatabase myDataBase;

	public PackageService(Context context) {
		this.openHelper = new OpenDataBaseHelper(context);
	}

	public void open() {
		myDataBase = openHelper.getWritableDatabase();
	}

	public void insert(Package p) {
		ContentValues values = new ContentValues();

		values.put(OpenDataBaseHelper.COLUMN_COURSE_CODE, p.getCourse_code());
		values.put(OpenDataBaseHelper.COLUMN_COURSE_NAME, p.getCourse_code());
		values.put(OpenDataBaseHelper.COLUMN_VERSION, p.getCourse_code());

		myDataBase.insert(OpenDataBaseHelper.PACKAGE_TABLE, null, values);
	}

	public List<Package> getAllPackages() {
		List<Package> packageList = new ArrayList<Package>();

		Cursor cursor = myDataBase.query(OpenDataBaseHelper.PACKAGE_TABLE, null, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			packageList.add(cursorToPackage(cursor));
			cursor.moveToNext();
		}
		cursor.close();

		return packageList;
	}

	private Package cursorToPackage(Cursor cursor) {
		Package myPackage = new Package();
		myPackage.setCourse_code(cursor.getString(0));
		myPackage.setCourse_title(cursor.getString(1));
		myPackage.setVersion(cursor.getInt(2));
		return myPackage;
	}

	public void close() {
		myDataBase.close();
	}

}
