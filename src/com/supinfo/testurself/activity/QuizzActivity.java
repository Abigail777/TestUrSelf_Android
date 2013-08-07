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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.supinfo.testurself.R;
import com.supinfo.testurself.beans.Question;
import com.supinfo.testurself.beans.Response;
import com.supinfo.testurself.manager.PackageManager;
import com.supinfo.testurself.tools.MyTools;

public class QuizzActivity extends Activity {

	// final private String bouchonQuestions =
	// "{\"questionList\": [{\"question\": \"How do you create a new servlet ?\",\"responseList\": [{\"isCorrect\": \"false\",\"response\": \"Â With a xml file.\"},{\"isCorrect\": \"false\",\"response\": \"Â With a Java Class.\"},{\"isCorrect\": \"true\",\"response\": \"With both.\"},{\"isCorrect\": \"false\",\"response\": \"Â I don't know\"}]},{\"question\": \"Â What is Tomcat ?\",\"responseList\": [{\"isCorrect\": \"false\",\"response\": \"Â An application server.\"},{\"isCorrect\": \"true\",\"response\": \"A JSP and Servlet container.\"},{\"isCorrect\": \"false\",\"response\": \"Â A cat whose name is Tome.\"},{\"isCorrect\": \"false\",\"response\": \"I don't give a cow.\"}]}]}";
	private List<Question> questions;

	private TextView tvQuestion;
	private TextView tvQuestionNumber;
	private TextView tvQuestionTotal;
	private LinearLayout ll_Reponses;
	private ProgressBar pgbar;
	private Button bSuivant;

	private RadioGroup radioGroup;

	private int packagePosition;
	private int quizzPosition;
	private int questionNumber;
	private int goodResponses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out);

		setContentView(R.layout.activity_quizz);

		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		tvQuestionNumber = (TextView) findViewById(R.id.tvQuestionNumber);
		tvQuestionTotal = (TextView) findViewById(R.id.tvQuestionTotal);
		ll_Reponses = (LinearLayout) findViewById(R.id.layoutReponse);
		pgbar = (ProgressBar) findViewById(R.id.refreshProgressBar);
		bSuivant = (Button) findViewById(R.id.bnext);

		packagePosition = getIntent().getIntExtra("packagePosition", 0);
		quizzPosition = getIntent().getIntExtra("quizzPosition", 0);

		// copy list question
		questions = new ArrayList<Question>(PackageManager.packages.get(packagePosition).getListQuizz()
				.get(quizzPosition).getQuestionList());

		if (MainLoadActivity.prefs.getBoolean("pref_random", false)) {
			Collections.shuffle(questions);
		}

		// JSONArray jsonQuestions = new JSONArray();
		// questions = new ArrayList<Question>();

		// try {
		// jsonQuestions = new
		// JSONObject(bouchonQuestions).getJSONArray("questionList");
		// for(int i=0;i<jsonQuestions.length();i++) {
		// JSONArray jsonReponse =
		// jsonQuestions.getJSONObject(i).getJSONArray("responseList");
		// List<Response> reponses = new ArrayList<Response>();
		// for(int j=0;j<jsonReponse.length();j++) {
		// Response reponse = new Response();
		// reponse.setIsCorrect(jsonReponse.getJSONObject(j).getBoolean("isCorrect"));
		// reponse.setResponse(jsonReponse.getJSONObject(j).getString("response"));
		//
		// reponses.add(reponse);
		// }
		// Question question = new
		// Question(jsonQuestions.getJSONObject(i).getString("question"));
		// question.setResponseList(reponses);
		//
		// questions.add(question);
		// }
		//
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		tvQuestionTotal.setText(String.valueOf(questions.size()));
		pgbar.setMax(questions.size());
		nextQuestion();

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dismiss();

			}
		});

		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dismiss();
			}
		});

		bSuivant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int selectedIndex = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
				if (selectedIndex == -1) {
					MyTools.makeToast(getApplicationContext(), "Please choose a response", 0);
				} else {

					boolean response = questions.get(questionNumber - 1).getResponseList().get(selectedIndex)
							.getIsCorrect();
					String comment = questions.get(questionNumber - 1).getResponseList().get(selectedIndex)
							.getComment();

					if (response) { // true

						if (comment.length() < 3)
							comment = ":-)";
						builder.setTitle("True");
						builder.setMessage(comment);
						AlertDialog dialog = builder.create();
						dialog.show();

						goodResponses++;
					} else { // false

						if (comment.length() < 3)
							comment = ":-(";
						builder.setTitle("False");
						builder.setMessage(comment);
						AlertDialog dialog = builder.create();
						dialog.show();

						// MyTools.makeToast(getApplicationContext(), "FAUX",
						// 0);
					}

				}
			}
		});

	}

	void dismiss() {

		if (questionNumber < questions.size()) {
			nextQuestion();
		} else {
			Intent i = new Intent(getApplicationContext(), FinishedQuizzActivity.class);
			i.putExtra("score", Integer.valueOf(goodResponses * 100 / questions.size()));
			i.putExtra("packagePosition", packagePosition);
			i.putExtra("quizzPosition", quizzPosition);
			startActivity(i);
			finish();
		}

	}

	private void nextQuestion() {
		tvQuestion.setText(questions.get(questionNumber).getQuestion());
		radioGroup = new RadioGroup(getApplicationContext());
		List<Response> reponses = questions.get(questionNumber).getResponseList();

		WebView webViewMedia = new WebView(getApplicationContext());
		// webViewMedia.getSettings().setPluginsEnabled(true);
		// webViewMedia.getSettings().setJavaScriptEnabled(true);
		// webViewMedia.getSettings().setAppCacheEnabled(true);

		final ProgressBar progressBarWeb = new ProgressBar(getApplicationContext(), null,
				android.R.attr.progressBarStyle);

		webViewMedia.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				Log.d(MainLoadActivity.TAG, "WebView charging...");
				if (newProgress < 100 && progressBarWeb.getVisibility() == ProgressBar.GONE) {
					progressBarWeb.setVisibility(ProgressBar.VISIBLE);
				}
				// progressBarWeb.setProgress(newProgress);
				if (newProgress == 100) {
					progressBarWeb.setVisibility(ProgressBar.GONE);
					Log.d(MainLoadActivity.TAG, "WebView charged");
				}
			}
		});

		for (int i = 0; i < reponses.size(); i++) {
			RadioButton radioButton = new RadioButton(getApplicationContext());
			radioButton.setText(reponses.get(i).getResponse());
			radioButton.setTextColor(Color.BLACK);
			radioGroup.addView(radioButton);
		}
		ll_Reponses.removeAllViews();

		String htmlMediaBrut = questions.get(questionNumber).getHtmlMedia();

		// remplace images url
		htmlMediaBrut = htmlMediaBrut.replace("../../", MainLoadActivity.DOKEOS_URL_FOR_IMG);
		htmlMediaBrut = htmlMediaBrut.replace("../img", MainLoadActivity.DOKEOS_URL_FOR_IMG + "main/img");

		// resize image
		Document doc = Jsoup.parse(htmlMediaBrut);
		Elements elementList = doc.getElementsByTag("img");
		Display display = getWindowManager().getDefaultDisplay();

		try {
			int widthHtml = Integer.parseInt(elementList.get(0).attr("width"));
			int heightHtml = Integer.parseInt(elementList.get(0).attr("height"));

			// if image size too big
			if (widthHtml > display.getWidth()) {
				int widthScreen = display.getWidth() / 2;

				int widthFinal = widthScreen;
				int heightFinal = (widthFinal * heightHtml) / widthHtml;

				htmlMediaBrut = htmlMediaBrut.replace("width=\"" + widthHtml + "\"", "width=\"" + widthFinal + "\"");

				htmlMediaBrut = htmlMediaBrut.replace("height=\"" + heightHtml + "\"", "width=\"" + heightFinal + "\"");

				Log.d(MainLoadActivity.TAG, "width=\"" + widthHtml + "\"");
				Log.d(MainLoadActivity.TAG, "width=\"" + ll_Reponses.getWidth() + "\"");

				Log.d(MainLoadActivity.TAG, "HTML " + widthHtml + " " + display.getHeight());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		Log.d(MainLoadActivity.TAG, "brute " + htmlMediaBrut);
		Log.d(MainLoadActivity.TAG, "END HTML");

		webViewMedia.loadDataWithBaseURL("", htmlMediaBrut, "text/html", "utf-8", null);

		// gravity
		// LayoutParams paramLayout = new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT);
		// paramLayout.gravity = Gravity.CENTER;
		// webViewMedia.setLayoutParams(paramLayout);

		// add views
		ll_Reponses.addView(progressBarWeb);
		ll_Reponses.addView(webViewMedia);
		ll_Reponses.addView(radioGroup);

		pgbar.setProgress(questionNumber);
		tvQuestionNumber.setText(String.valueOf(questionNumber + 1));

		questionNumber++;
	}
}
