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

package com.supinfo.testurself.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.supinfo.testurself.beans.Package;

public class PackageManager {

	public static List<Package> packages = new ArrayList<Package>();

	// Get Request
	public JSONArray sendGetRequest(String address) throws Exception {
		String result = null;
		JSONArray js = null;
		try {
			// timeout
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30 * 1000);
			HttpConnectionParams.setSoTimeout(httpParameters, 30 * 1000);

			HttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpGet httpGet = new HttpGet(address);

			HttpResponse response = httpClient.execute(httpGet);

			result = EntityUtils.toString(response.getEntity());
			
			try {
				//if only one course
				JSONObject jsTemp = new JSONObject(result);
				js = jsTemp.getJSONArray("packageInfo");
			} catch (JSONException e) {
				//if many course
				e.printStackTrace();
				js = new JSONArray(result);
			}
			

		} catch (Exception e) {
			Log.e("sendGet", e.getMessage(), e);
			throw e;
		}
		return js;
	}

	public void convertToSubject(JSONArray jsonArray) throws JSONException {
		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject infoPackage = jsonArray.getJSONObject(i);

			packages.add(new Package(infoPackage.getString("course_title"), infoPackage.getString("course_code"),
					infoPackage.getString("url_dl"), infoPackage.getInt("version")));

		}

	}

}
