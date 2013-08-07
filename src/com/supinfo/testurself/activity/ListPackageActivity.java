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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.supinfo.testurself.R;
import com.supinfo.testurself.adapter.PackageAdapter;
import com.supinfo.testurself.beans.Package;
import com.supinfo.testurself.manager.PackageManager;
import com.supinfo.testurself.taks.GetAndUnzipPackageTask;
import com.supinfo.testurself.taks.LoadPackageListTask;
import com.supinfo.testurself.taks.LoadQuizzListTask;

public class ListPackageActivity extends Activity {

	private ListView subjectsList;
	private ImageButton refreshButton;
	private ProgressBar refreshProgressBar;
	private Activity activity;
	private BaseAdapter baseAdapter;

	public static List<Package> packages = PackageManager.packages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_packages);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

		this.activity = this;

		subjectsList = (ListView) findViewById(R.id.lvPackages);
		refreshButton = (ImageButton) findViewById(R.id.ib_refresh);
		refreshProgressBar = (ProgressBar) findViewById(R.id.refreshProgressBar);

		refreshButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshButton.setVisibility(View.INVISIBLE);
				refreshProgressBar.setVisibility(View.VISIBLE);
				LoadPackageListTask checkUser = new LoadPackageListTask(ListPackageActivity.this);
				checkUser.execute();
			}
		});

		boolean connection = getIntent().getBooleanExtra("connection", false);

		if (connection) {

			TextView tv_connexion = (TextView) findViewById(R.id.tv_connexion);
			tv_connexion.setVisibility(View.GONE);

			baseAdapter = new PackageAdapter(this, packages);

			subjectsList.setAdapter(baseAdapter);
			subjectsList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					Package p = packages.get(position);

					if (p.isDownloaded()) {
						LoadQuizzListTask myTask = new LoadQuizzListTask(activity, position);
						myTask.execute(p);
					} else {

						// test big file
						// new
						// GetAndUnzipPackageTask(view).execute(MainLoadActivity.APPLICATION_URL
						// + "/courses/" + "BIGFILE.zip");

						GetAndUnzipPackageTask myTask = new GetAndUnzipPackageTask(view, position,
								getApplicationContext());

						myTask.execute(MainLoadActivity.APPLICATION_URL + "/" + p.getUrl_dl());

					}

				}
			});

			subjectsList.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

					final Package p = packages.get(position);

					final AlertDialog.Builder alertInfobuilder = new AlertDialog.Builder(activity);
					alertInfobuilder.setPositiveButton("Retour", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
					alertInfobuilder.setTitle(p.getCourse_title());

					String message = "";
					message += "Course code : " + p.getCourse_code();
					message += "\n Package version : " + p.getVersion();
					// message += "\n Language : " + p.getLanguage();
					// message += "\n Creation date : " +
					// p.getCreation_date();
					// message += "\n Tutor : " + p.getTutor_name();
					//

					if (p.isDownloaded()) {
						alertInfobuilder.setNeutralButton("Supprimer", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								MainLoadActivity.prefs.edit().putInt(p.getCourse_code(), 0).commit();
								p.setDownloaded(false);

								baseAdapter.notifyDataSetChanged();

								Log.d(MainLoadActivity.TAG, "Code : " + p.getCourse_code());
								// activity.finish();
								// Intent intent = new
								// Intent(activity,ListPackageActivity.class);
								// startActivity(intent);
							}
						});
					}

					alertInfobuilder.setMessage(message);
					alertInfobuilder.show();

					return false;
				}
			});

		} else {
			TextView tv_connexion = (TextView) findViewById(R.id.tv_connexion);
			ImageView iv_connexion = (ImageView) findViewById(R.id.ivNoConnexion);
			tv_connexion.setVisibility(View.VISIBLE);
			iv_connexion.setVisibility(View.VISIBLE);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflateur = getMenuInflater();
		inflateur.inflate(R.menu.subjects_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.settings:
			Intent intent = new Intent(ListPackageActivity.this, SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
