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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.supinfo.testurself.activity.ListPackageActivity;
import com.supinfo.testurself.activity.MainLoadActivity;
import com.supinfo.testurself.manager.PackageManager;
import com.supinfo.testurself.tools.MyTools;

public class LoadPackageListTask extends AsyncTask<Void, String, Boolean> {

	private Activity activity;

	// private TextView tv_debug;

	public LoadPackageListTask(Activity activity) {
		this.activity = activity;
		// tv_debug = (TextView) activity.findViewById(R.id.tv_debug);
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		Log.d(MainLoadActivity.TAG, "doInBackground");

		boolean online = isOnline();
		boolean isServerUp = serverIsUp();
		boolean listIsEmpty = false;

		if (online && isServerUp) {

			publishProgress("Downloading course list...");
			PackageManager packageManager = new PackageManager();
			PackageManager.packages.clear();

			try {
				JSONArray json = packageManager.sendGetRequest(MainLoadActivity.APPLICATION_URL
						+ "/resources/list/info");

				publishProgress("Converting list...");
				packageManager.convertToSubject(json);
				listIsEmpty = packageManager.packages.isEmpty();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

		if (!online || !isServerUp || listIsEmpty) {

			String message = "";

			if (!online) {
				message = "No internet connexion";
			} else {
				message = "Server not reachable";
			}

			final String toast = message;

			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					MyTools.makeToast(activity.getApplicationContext(), toast, 1);
				}
			});
			return false;
		}
		return false;
	}

	private boolean serverIsUp() {
		try {

			URL url = new URL(MainLoadActivity.APPLICATION_URL);
			HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
			urlc.setRequestProperty("Connection", "close");
			urlc.setConnectTimeout(1000 * 30); // mTimeout is in seconds
			urlc.connect();
			if (urlc.getResponseCode() == 200) {
				Log.d(MainLoadActivity.TAG, "getResponseCode == 200");
				return true;
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);

		// tv_debug.setText(tv_debug.getText() + "\n" + values[0]);
		Log.d(MainLoadActivity.TAG, "Status : " + values[0]);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		Intent intent = new Intent(activity, ListPackageActivity.class);
		intent.putExtra("connection", result);
		activity.startActivity(intent);
		activity.finish();
	}

	public boolean isOnline() {
		publishProgress("Testing connection...");
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}