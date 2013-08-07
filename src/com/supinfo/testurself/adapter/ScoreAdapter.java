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

import com.supinfo.testurself.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreAdapter extends BaseAdapter {

	private String[] scores;
	private LayoutInflater inflater;
	private Context context;

	public ScoreAdapter(String[] scores, Context context) {
		this.context = context;
		this.scores = scores;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return scores.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return scores[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;

		if (convertView == null) {
			layoutItem = (LinearLayout) inflater.inflate(R.layout.adapter_score, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		TextView tvScore = (TextView) layoutItem.findViewById(R.id.tvSavedScore);
		TextView tvDate = (TextView) layoutItem.findViewById(R.id.tvSavedDate);

		String[] score = getItem(position).split(",");

		if (score[0].toString().length() > 0 && Integer.valueOf(score[0]) < 50) {
			tvScore.setTextColor(Color.RED);
		} else {
			tvScore.setTextColor(context.getResources().getColor(R.color.BlueSupinfo));
		}

		tvScore.setText(score[0] + "%");
		tvDate.setText(score[1]);

		return layoutItem;
	}

}
