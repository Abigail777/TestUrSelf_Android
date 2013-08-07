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

package com.supinfo.testurself.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.supinfo.testurself.activity.MainLoadActivity;

public class MyTools {

	public static void makeToast(Context context, String content, int time) {

		if (time == 0) {
			Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, content, Toast.LENGTH_LONG).show();
		}

	}

	public static void unZipFile(String zipFile, Context context) throws IOException {

		byte[] buffer = new byte[1024];

		// get the zip file content
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		// get the zipped file list entry
		ZipEntry ze = zis.getNextEntry();

		File file = new File(zipFile);

		File mydir = context.getDir(file.getName().replace(".zip", ""), Context.MODE_PRIVATE); // Creating
																								// an
																								// internal
																								// dir;

		Log.d(MainLoadActivity.TAG, "myDir : " + mydir.getAbsolutePath());

		while (ze != null) {

			String fileName = ze.getName();

			Log.d(MainLoadActivity.TAG, "file unzip : " + fileName);

			File fileWithinMyDir = new File(mydir, fileName); // Getting a file
																// within the
																// dir.

			Log.d(MainLoadActivity.TAG, "file save : " + fileWithinMyDir);

			FileOutputStream fos = new FileOutputStream(fileWithinMyDir); // Use
																			// the
																			// stream
																			// as
																			// usual
																			// to
																			// write
																			// into
																			// the
																			// file

			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			fos.close();
			ze = zis.getNextEntry();
		}

		zis.closeEntry();
		zis.close();

	}
}
