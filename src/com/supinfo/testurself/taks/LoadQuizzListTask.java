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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.supinfo.testurself.activity.ListQuizzActivity;
import com.supinfo.testurself.activity.MainLoadActivity;
import com.supinfo.testurself.beans.Package;
import com.supinfo.testurself.beans.Quizz;
import com.supinfo.testurself.manager.QuizzManager;

public class LoadQuizzListTask extends AsyncTask<Package, Integer, Void> {

	private Activity activity;
	private Package myPackage;
	private int position;

	public LoadQuizzListTask(Activity activity, int position) {
		this.activity = activity;
		this.position = position;
	}

	@Override
	protected Void doInBackground(Package... params) {

		myPackage = params[0];
		myPackage.getListQuizz().clear();

		File filesDir = activity.getDir(myPackage.getCourse_code(), Context.MODE_PRIVATE);
		Log.d(MainLoadActivity.TAG, "internal : " + filesDir.getAbsolutePath());

		Log.d(MainLoadActivity.TAG, "Liste file");
		for (File f : filesDir.listFiles()) {
			Log.d(MainLoadActivity.TAG,
					"new file found : " + f.getAbsolutePath() + " " + f.getName().replace(".json", ""));

			// read file and parse to json
			BufferedReader br = null;
			String content = "";
			try {
				br = new BufferedReader(new FileReader(f));
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}

				content = sb.toString();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			JSONObject jsTemp = null;
			try {
				jsTemp = new JSONObject(content);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			// at last, add the quizz to the package
			try {
				Quizz myQuizz = QuizzManager.getQuizzManager().convertToQuizz(f.getName().replace(".json", ""), jsTemp);

				myPackage.getListQuizz().add(myQuizz);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		Intent intent = new Intent(activity, ListQuizzActivity.class);
		intent.putExtra("position", position);
		activity.startActivity(intent);
	}

}
