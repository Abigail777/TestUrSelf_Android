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

package com.supinfo.testurself.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.supinfo.testurself.R;
import com.supinfo.testurself.taks.LoadPackageListTask;

public class MainLoadActivity extends Activity {

	public static String TAG = "TestUrSelf";
	public static String ADRESSEIP = "http://" + "37.187.0.191";
	public static String DOKEOS_URL_FOR_IMG = ADRESSEIP + "/" + "dokeos-2.1.1/";
	public static String APPLICATION_URL = MainLoadActivity.ADRESSEIP + ":8080"+ "/TestUrSelfServer";

	public static SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		LoadPackageListTask checkUser = new LoadPackageListTask(this);
		checkUser.execute();
	}

}
