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

package com.supinfo.testurself.taks;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.supinfo.testurself.R;
import com.supinfo.testurself.activity.ListPackageActivity;
import com.supinfo.testurself.activity.MainLoadActivity;
import com.supinfo.testurself.tools.MyTools;

public class GetAndUnzipPackageTask extends AsyncTask<String, Integer, Boolean> {

	private View view;
	private int position;
	private Context context;

	public GetAndUnzipPackageTask(View view, int position, Context context) {
		this.view = view;
		this.position = position;
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(String... url) {
		URL myUrl = null;
		String pathNewFile = null;
		int fileLength = 0;
		File zipFile = null;

		// ************** get path and length //
		try {
			myUrl = new URL(url[0]);

			Log.d(MainLoadActivity.TAG, "Download and unzip this : " + myUrl.toString());

			URLConnection connection = myUrl.openConnection();
			fileLength = connection.getContentLength();

			pathNewFile = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/"
					+ myUrl.toString().substring(myUrl.toString().lastIndexOf('/') + 1, myUrl.toString().length());

			Log.d(MainLoadActivity.TAG, "File name : " + pathNewFile);
			zipFile = new File(pathNewFile);

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// ************** download file //
		try {
			InputStream input = new BufferedInputStream(myUrl.openStream());
			OutputStream output = new FileOutputStream(pathNewFile);

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				publishProgress((int) (total * 100 / fileLength));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// ************** extract file into app directory//
		try {
			MyTools.unZipFile(pathNewFile, context);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// ************** delete zip file //
		zipFile.delete();

		return true;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		Log.d(MainLoadActivity.TAG, "Downloading... " + values[0] + " %");
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		view.findViewById(R.id.iv_downloaded).setVisibility(View.GONE);
		view.findViewById(R.id.pb_download).setVisibility(View.VISIBLE);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		ListPackageActivity.packages.get(position).setDownloaded(result);

		if (result) {

			view.findViewById(R.id.pb_download).setVisibility(View.GONE);
			view.findViewById(R.id.iv_downloaded).setVisibility(View.VISIBLE);
			// set current package version for course in share prefs
			MainLoadActivity.prefs
					.edit()
					.putInt(ListPackageActivity.packages.get(position).getCourse_code(),
							ListPackageActivity.packages.get(position).getVersion()).commit();
		}

	}
}
