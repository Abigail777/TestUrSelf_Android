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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supinfo.testurself.R;
import com.supinfo.testurself.activity.MainLoadActivity;
import com.supinfo.testurself.beans.Package;

public class PackageAdapter extends BaseAdapter {

	private List<Package> packages;
	private LayoutInflater inflater;

	public PackageAdapter(Context context, List<Package> packages) {
		inflater = LayoutInflater.from(context);
		this.packages = packages;
	}

	@Override
	public int getCount() {
		return packages.size();
	}

	@Override
	public Object getItem(int position) {
		return packages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;

		if (convertView == null) {
			layoutItem = (LinearLayout) inflater.inflate(R.layout.adapter_subject, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		// Show info
		TextView courseCode = (TextView) layoutItem.findViewById(R.id.tv_big_title);
		TextView courseName = (TextView) layoutItem.findViewById(R.id.tv_small_title);
		TextView version = (TextView) layoutItem.findViewById(R.id.tv_version);

		courseName.setText(packages.get(position).getCourse_title());
		courseCode.setText(packages.get(position).getCourse_code());
		version.setText(packages.get(position).getVersion() + "");

		// is downloaded ?
		ImageView iv_downloaded = (ImageView) layoutItem.findViewById(R.id.iv_downloaded);

		int downloadedVersion = MainLoadActivity.prefs.getInt(packages.get(position).getCourse_code(), 0);
		int currentOnlineVersion = packages.get(position).getVersion();

		Log.d(MainLoadActivity.TAG, "Versioning in adapter -> in prefs :" + packages.get(position).getCourse_code()
				+ " " + downloadedVersion + " and online : " + currentOnlineVersion);

		if (currentOnlineVersion > downloadedVersion) {
			packages.get(position).setDownloaded(false);
			iv_downloaded.setVisibility(View.GONE);
		} else {
			packages.get(position).setDownloaded(true);
			iv_downloaded.setVisibility(View.VISIBLE);
		}

		return layoutItem;
	}

}
