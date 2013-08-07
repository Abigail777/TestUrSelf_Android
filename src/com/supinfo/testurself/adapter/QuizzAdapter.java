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

package com.supinfo.testurself.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supinfo.testurself.R;
import com.supinfo.testurself.beans.Quizz;

public class QuizzAdapter extends BaseAdapter {

	private List<Quizz> quizzList;
	private LayoutInflater inflater;

	public QuizzAdapter(Context context, List<Quizz> quizzList) {
		inflater = LayoutInflater.from(context);
		this.quizzList = quizzList;
	}

	@Override
	public int getCount() {
		return quizzList.size();
	}

	@Override
	public Quizz getItem(int position) {
		return quizzList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;

		if (convertView == null) {
			layoutItem = (LinearLayout) inflater.inflate(R.layout.adapter_quizz, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		TextView quizzName = (TextView) layoutItem.findViewById(R.id.tv_quizz_big_title);
		TextView questionNumber = (TextView) layoutItem.findViewById(R.id.tv_small_title_quizz);

		quizzName.setText(getItem(position).getQuizzName());
		questionNumber.setText("Number of questions : " + getItem(position).getQuestionNumber());

		return layoutItem;
	}

}
