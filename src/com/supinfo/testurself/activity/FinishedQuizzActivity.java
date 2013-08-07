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

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.supinfo.testurself.R;
import com.supinfo.testurself.adapter.ScoreAdapter;
import com.supinfo.testurself.manager.PackageManager;

public class FinishedQuizzActivity extends Activity {

	private TextView tvScore;
	private TextView tvPercent;
	private TextView tvCourseCode;
	private TextView tvQuizzTitle;
	private Button bRetour;
	private ListView lvScores;

	private int packagePosition;
	private int quizzPosition;
	private String[] datesScores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finished_quizz);

		tvScore = (TextView) findViewById(R.id.tvScore);
		tvPercent = (TextView) findViewById(R.id.tvPercent);
		tvCourseCode = (TextView) findViewById(R.id.tvCourseCode);
		tvQuizzTitle = (TextView) findViewById(R.id.tvQuizzTitle);
		bRetour = (Button) findViewById(R.id.b_retour);
		lvScores = (ListView) findViewById(R.id.lvScores);

		packagePosition = getIntent().getIntExtra("packagePosition", 0);
		quizzPosition = getIntent().getIntExtra("quizzPosition", 0);
		int score = getIntent().getIntExtra("score", 0);
		tvScore.setText(String.valueOf(score));
		if (score >= 50) {
			tvScore.setTextColor(getResources().getColor(R.color.BlueSupinfo));
			tvPercent.setTextColor(getResources().getColor(R.color.BlueSupinfo));
		} else {
			tvScore.setTextColor(Color.RED);
			tvPercent.setTextColor(Color.RED);
		}

		tvCourseCode.setText(PackageManager.packages.get(packagePosition).getCourse_code());
		tvQuizzTitle.setText(PackageManager.packages.get(packagePosition).getListQuizz().get(quizzPosition)
				.getQuizzName());

		String savedScores = MainLoadActivity.prefs.getString(tvCourseCode.getText().toString()
				+ tvQuizzTitle.getText().toString(), "");

		if (savedScores.length() > 0) {
			datesScores = savedScores.split(";");
			lvScores.setAdapter(new ScoreAdapter(datesScores, getApplicationContext()));
		}

		String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
		savedScores = String.valueOf(score) + "," + date + ";" + savedScores;
		MainLoadActivity.prefs.edit()
				.putString(tvCourseCode.getText().toString() + tvQuizzTitle.getText().toString(), savedScores).commit();

		bRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflateur = getMenuInflater();
		inflateur.inflate(R.menu.finished_quizz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.clear_score:
			MainLoadActivity.prefs.edit()
					.putString(tvCourseCode.getText().toString() + tvQuizzTitle.getText().toString(), "").commit();
			this.onCreate(null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
