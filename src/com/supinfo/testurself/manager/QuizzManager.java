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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.supinfo.testurself.activity.MainLoadActivity;
import com.supinfo.testurself.beans.Question;
import com.supinfo.testurself.beans.Quizz;
import com.supinfo.testurself.beans.Response;

public class QuizzManager {

	static QuizzManager quizzManager;

	private QuizzManager() {
		// it is private in purpose
	}

	public static QuizzManager getQuizzManager() {
		if (quizzManager == null)
			return new QuizzManager();
		else
			return quizzManager;
	}

	public Quizz convertToQuizz(String name, JSONObject jsMainObj) throws JSONException {

		Quizz quizz = new Quizz();
		quizz.setQuizzName(name);
		quizz.setQuestionNumber(jsMainObj.getInt("nombreQuestions"));

		JSONArray jsArrayQuest = jsMainObj.getJSONArray("questionList");// questions
		for (int i = 0; i < jsArrayQuest.length(); i++) {
			JSONObject questionJson = jsArrayQuest.getJSONObject(i);

			Question question = new Question();
			question.setQuestion(questionJson.getString("question"));
			question.setHtmlMedia(questionJson.getString("htmlMedia"));

			JSONArray jsArrayResp = questionJson.getJSONArray("responseList");// reponses
			for (int j = 0; j < jsArrayResp.length(); j++) {
				JSONObject responseJson = jsArrayResp.getJSONObject(j);

				Response response = new Response();
				response.setResponse(responseJson.getString("response"));
				response.setIsCorrect(responseJson.getBoolean("isCorrect"));
				response.setComment(responseJson.getString("comment"));

				Log.d(MainLoadActivity.TAG, "reponse : " + response.getResponse());

				question.addResponse(response);
			}

			quizz.addQuestion(question);
		}

		return quizz;
	}
}
