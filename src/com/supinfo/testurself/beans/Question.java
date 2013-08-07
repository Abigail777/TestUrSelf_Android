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

public class Question {

	private String question;
	private String htmlMedia;

	private List<Response> responseList = new ArrayList<Response>();

	public Question() {
	}

	public Question(String question) {
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Response> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<Response> responseList) {
		this.responseList = responseList;
	}

	public void addResponse(Response response) {
		this.responseList.add(response);
	}

	public String getHtmlMedia() {
		return htmlMedia;
	}

	public void setHtmlMedia(String htmlMedia) {
		this.htmlMedia = htmlMedia;
	}
}
