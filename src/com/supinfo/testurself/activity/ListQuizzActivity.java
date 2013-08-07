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

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.supinfo.testurself.R;
import com.supinfo.testurself.adapter.QuizzAdapter;
import com.supinfo.testurself.beans.Quizz;
import com.supinfo.testurself.manager.PackageManager;

public class ListQuizzActivity extends Activity {

	private ListView lv_quizzList;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quizzs);

		activity = this;

		// get currentPackage
		final int packagePosition = getIntent().getIntExtra("position", 0);

		lv_quizzList = (ListView) findViewById(R.id.lv_quizzs);

		List<Quizz> myListQuizz = PackageManager.packages.get(packagePosition).getListQuizz();

		Log.d(MainLoadActivity.TAG, "Postion : " + packagePosition + " listSize : " + myListQuizz.size());

		lv_quizzList.setAdapter(new QuizzAdapter(this, PackageManager.packages.get(packagePosition).getListQuizz()));
		lv_quizzList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(activity, QuizzActivity.class);
				intent.putExtra("packagePosition", packagePosition);
				intent.putExtra("quizzPosition", position);
				activity.startActivity(intent);
			}

		});

	}

}
