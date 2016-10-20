package com.dzyd.pdfreader;

import static java.lang.String.format;

import java.io.File;

import com.dzyd.service.DownloadService;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

public class AFinalReaderActivity extends Activity implements OnPageChangeListener {

	private TextView progress;
	private PDFView pdfView;
	private Context mContext;
	private int pageNumber;
	public static final String DOWNLOAD_ACTION = "com.lihao.downloadcase";
	private static final String oaDir = Environment.getExternalStorageDirectory().getPath() + "/isocache";
	private static final String fileUrl = "https://tj-yun-ftn.weiyun.com/ftn_handler/ef1feb654ff6bb3b85aee26bc9afd08280727079193406a58dc3d7223c563fb6abdb12766ad2fc641ba4bf4dc51039c7b30f0ddefcf4f4edf502616ca67d958a?compressed=0&dtype=1&fname=%E3%80%90%E9%BB%91%E9%A9%AC%E6%95%99%E7%A8%8B%E3%80%91%E6%8C%91%E6%88%98%E5%B9%B4%E8%96%AA20W%E4%B9%8BAndroid%E6%95%99%E7%A8%8B%E7%AC%94%E8%AE%B0%E2%80%94Day06%EF%BC%88%E9%A1%B5%E9%9D%A2%E8%B7%B3%E8%BD%AC%E5%92%8C%E6%95%B0%E6%8D%AE%E4%BC%A0%E9%80%92%EF%BC%89.pdf";

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
		File file = new File(oaDir + "/faq.pdf");
		try {
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		startDownloadService();
		progress.setVisibility(View.VISIBLE);
		progress.setText("正在连接资源...");
	}

	private void startDownloadService() {
		FinalHttp fh = new FinalHttp();
		HttpHandler<File> handler = fh.download(fileUrl, oaDir + "/faq.pdf", true, new AjaxCallBack<File>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				Toast.makeText(mContext, "下载失败!", Toast.LENGTH_SHORT).show();
				Log.d("Lihao", t.getMessage());
				Log.d("Lihao", strMsg);
				Log.d("Lihao", "errorNo:" + errorNo);
			}

			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
				int progressNum = (int) (current * 100 / count);
				progress.setText("当前进度:" + progressNum + "%");
			}

			@Override
			public void onSuccess(File file) {
				// TODO Auto-generated method stub
				super.onSuccess(file);
				progress.setVisibility(View.GONE);
				Toast.makeText(mContext, "下载完成!", Toast.LENGTH_SHORT).show();
				display(file.getAbsolutePath(), true);
			}

		});
	}

	private void display(String assetFileName, boolean jumpToFirstPage) {
		if (jumpToFirstPage)
			pageNumber = 1;
		setTitle("PDF阅读器");
		File srcFile = new File(assetFileName);
		pdfView.fromFile(srcFile).defaultPage(pageNumber).enableDoubletap(true).onPageChange(this).load();
	}

	@Override
	public void onPageChanged(int page, int pageCount) {
		// TODO Auto-generated method stub
		pageNumber = page;
		setTitle(format("%s %s / %s", "PDF阅读器", page, pageCount));
	}

}
