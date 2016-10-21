package com.dzyd.pdfreader;

import static java.lang.String.format;

import java.io.File;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dzyd.service.DownloadService;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

public class RemotePdfReaderActivity extends Activity implements OnPageChangeListener {

	private TextView progress;
	private PDFView pdfView;
	private Context mContext;
	private int pageNumber;
	private DownloadCompletedReciever mReciever;
	public static final String DOWNLOAD_ACTION = "com.lihao.downloadcase";
	private static  final String oaDir = Environment.getExternalStorageDirectory().getPath() + "/isocache";
	private static final String fileUrl = "https://tj-yun-ftn.weiyun.com/ftn_handler/acc301e1919604c0dbe040fc773817410b1d904487f67afc34e4b4b586ec997a8da2ce04acf6982727e26bbea455bf568349756bb0b5436d7804a410850143b0?compressed=0&dtype=1&fname=%E3%80%90%E9%BB%91%E9%A9%AC%E6%95%99%E7%A8%8B%E3%80%91%E6%8C%91%E6%88%98%E5%B9%B4%E8%96%AA20W%E4%B9%8BAndroid%E6%95%99%E7%A8%8B%E7%AC%94%E8%AE%B0%E2%80%94Day06%EF%BC%88%E9%A1%B5%E9%9D%A2%E8%B7%B3%E8%BD%AC%E5%92%8C%E6%95%B0%E6%8D%AE%E4%BC%A0%E9%80%92%EF%BC%89.pdf";
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(mContext, "加载失败!", Toast.LENGTH_SHORT).show();
				progress.setVisibility(View.GONE);
				break;
			case 1:
				Toast.makeText(mContext, "加载完成!", Toast.LENGTH_SHORT).show();
				progress.setVisibility(View.GONE);
				display(oaDir + "/temppdf.shit", true);
				unregisterReceiver(mReciever);
				break;
			case 2:
				progress.setText("加载中，当前进度" + msg.arg1 + "%");
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdf_urlview);
		mContext = this;
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		pdfView = (PDFView) findViewById(R.id.pdfView);
		progress = (TextView) findViewById(R.id.tv_progress);
	}

	private void initData() {
		mReciever = new DownloadCompletedReciever();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(DOWNLOAD_ACTION);
		registerReceiver(mReciever, intentFilter);
		// DownloadUtil.download(fileUrl, oaDir, mHandler);
		startDownloadService();
		progress.setVisibility(View.VISIBLE);
		progress.setText("正在连接资源...");
	}

	private void startDownloadService() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(mContext, DownloadService.class);
		intent.putExtra("urlStr", fileUrl);
		intent.putExtra("path", oaDir);
		startService(intent);
	}

	private void display(String assetFileName, boolean jumpToFirstPage) {
		if (jumpToFirstPage)
			pageNumber = 1;
		setTitle("PDF阅读器");
		File srcFile = new File(assetFileName);
		pdfView.fromFile(srcFile)
				.defaultPage(pageNumber)
				.enableDoubletap(true)
				.onPageChange(this)
				.load();
	}

	@Override
	public void onPageChanged(int page, int pageCount) {
		pageNumber = page;
		setTitle(format("%s %s / %s", "PDF阅读器", page, pageCount));
	}

	public class DownloadCompletedReciever extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int resultCode = intent.getIntExtra("result", 0);
			switch (resultCode) {
			case 0:
				mHandler.sendEmptyMessage(0);
				break;
			case 1:
				mHandler.sendEmptyMessage(1);
				break;
			case 2:
				Message msg = mHandler.obtainMessage();
				msg.what = 2;
				msg.arg1 = (int) intent.getFloatExtra("progress", 0f);
				mHandler.sendMessage(msg);
				break;
			default:
				break;
			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mReciever != null) {
			try {
				unregisterReceiver(mReciever);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
