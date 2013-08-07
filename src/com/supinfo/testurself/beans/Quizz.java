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

package com.supinfo.testurself.beans;

import java.util.ArrayList;
import java.util.List;

public class Quizz {

	private String quizzName;
	private int questionNumber;
	private List<Question> questionList = new ArrayList<Question>();

	public static List<Quizz> quizzList = new ArrayList<Quizz>();

	public Quizz() {
		quizzList.add(this);
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public void addQuestion(Question question) {
		this.questionList.add(question);
	}

	public String getQuizzName() {
		return quizzName;
	}

	public void setQuizzName(String quizzName) {
		this.quizzName = quizzName;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}
	
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	
}
