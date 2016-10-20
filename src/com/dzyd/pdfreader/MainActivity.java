package com.dzyd.pdfreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button loadLocalPdf;
	private Button loadRemotePdf;
	private Button aFinalReadPdf;
	private String oaDir = Environment.getExternalStorageDirectory().getPath() + "/isocache";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initClick();
	}

	private void initView() {
		// TODO Auto-generated method stub
		loadLocalPdf = (Button) findViewById(R.id.bt_webview);
		loadRemotePdf = (Button) findViewById(R.id.bt_urlpdf);
		aFinalReadPdf = (Button) findViewById(R.id.bt_afinalpdf);
		Log.d("Lihao", oaDir);
	}

	private void initClick() {
		// TODO Auto-generated method stub
		loadLocalPdf.setOnClickListener(this);
		loadRemotePdf.setOnClickListener(this);
		aFinalReadPdf.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (view.getId()) {
		case R.id.bt_webview:
			intent = new Intent(this,PDFReaderActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_urlpdf:
			intent = new Intent(this,RemotePdfReaderActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_afinalpdf:
			intent = new Intent(this,AFinalReaderActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
