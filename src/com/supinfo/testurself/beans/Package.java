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

public class Package {

	private List<Quizz> listQuizz;

	private String course_code;
	private String course_title;
	private int version;
	private boolean downloaded = false;
	
	private String creation_date;
	private String language;
	private String tutor_name;
	private String url_dl;

	

	public Package() {
		listQuizz = new ArrayList<Quizz>();
	}

	public Package(String course_title, String course_code, String url_dl,
			int version) {
		this();
		this.course_title = course_title;
		this.course_code = course_code;
		this.url_dl = url_dl;
		this.version = version;
	}

	public List<Quizz> getListQuizz() {
		return listQuizz;
	}

	public void setListQuizz(List<Quizz> listQuizz) {
		this.listQuizz = listQuizz;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public String getCourse_title() {
		return course_title;
	}

	public void setCourse_title(String course_title) {
		this.course_title = course_title;
	}

	public String getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTutor_name() {
		return tutor_name;
	}

	public void setTutor_name(String tutor_name) {
		this.tutor_name = tutor_name;
	}

	public String getUrl_dl() {
		return url_dl;
	}

	public void setUrl_dl(String url_dl) {
		this.url_dl = url_dl;
	}

	public boolean isDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
