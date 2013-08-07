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

public class Response {

	private Boolean isCorrect;
	private String response;
	private String comment;

	public Response(Boolean isCorrect, String response, String comment) {
		this.isCorrect = isCorrect;
		this.response = response;
		this.comment = comment;
	}

	public Response() {
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

}
