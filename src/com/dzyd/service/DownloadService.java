package com.dzyd.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.dzyd.pdfreader.RemotePdfReaderActivity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class DownloadService extends IntentService {

	private Intent intent;
	private Bundle bundle;
	private static final int maxPro = 100;

	public DownloadService() {
		super("download");
		Log.d("Lihao", "Service已经启动!");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String urlStr = intent.getStringExtra("urlStr");
		String path = intent.getStringExtra("path");
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		} else if (dirFile.isFile()) {
			dirFile.delete();
			dirFile.mkdir();
		}
		File file = new File(dirFile.getAbsolutePath() + "/temppdf.shit");
		if (file.exists()) {
			file.delete();
		}
		Log.d("Lihao", "开始下载!");
		InputStream is = null;
		OutputStream os = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			int contentLength = conn.getContentLength();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			int curPro = 0;
			if (contentLength >= maxPro) {
				curPro = contentLength / maxPro;
			}
			Log.d("Lihao", "资源长度:" + contentLength);
			is = conn.getInputStream();
			byte[] bs = new byte[1024 * 4];
			int len;
			os = new FileOutputStream(file);
			int i = 0;
			int arg = 0;
			int totalPro = 0;
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
				totalPro += len;
				i += len;
				if (i > curPro) {
					Log.d("Lihao", "当前下载长度:" + totalPro + "/" + contentLength);
					float percent = (float) totalPro / (float) contentLength * 100;
					Log.d("Lihao", "当前下载进度:" + percent);
					intent = new Intent(RemotePdfReaderActivity.DOWNLOAD_ACTION);
					bundle = new Bundle();
					bundle.putInt("result", 2);
					bundle.putFloat("progress", percent);
					intent.putExtras(bundle);
					sendBroadcast(intent);
					i = 0;
				}
			}
			intent = new Intent(RemotePdfReaderActivity.DOWNLOAD_ACTION);
			bundle = new Bundle();
			bundle.putInt("result", 1);
			intent.putExtras(bundle);
			sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Lihao", e.getMessage());
			intent = new Intent(RemotePdfReaderActivity.DOWNLOAD_ACTION);
			bundle = new Bundle();
			bundle.putInt("result", 0);
			intent.putExtras(bundle);
			sendBroadcast(intent);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("Lihao", "下载完成!");
	}

}
