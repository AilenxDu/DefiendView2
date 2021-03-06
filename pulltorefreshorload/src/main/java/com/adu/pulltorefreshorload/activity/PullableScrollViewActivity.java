package com.adu.pulltorefreshorload.activity;


import android.app.Activity;
import android.os.Bundle;

import com.adu.pulltorefreshorload.MyListener;
import com.adu.pulltorefreshorload.PullToRefreshLayout;
import com.adu.pulltorefreshorload.R;

public class PullableScrollViewActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
	}
}
