/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sweet.qianqian.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.sweet.qianqian.R;

public class TempFragment extends BaseFragment {

	private static final String EXTRA_POSITION = "position";

	private int position;

	public static TempFragment newInstance(int position) {
		TempFragment tempFragment = new TempFragment();
		Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, position);
        tempFragment.setArguments(bundle);
		return tempFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(EXTRA_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		TextView v = new TextView(getActivity());
		v.setLayoutParams(params);
		v.setLayoutParams(params);
		v.setGravity(Gravity.CENTER);
        v.setBackgroundColor(getContext().getResources().getColor(R.color.white));
		v.setText("TEMP");

		fl.addView(v);
		return fl;
	}

}