package com.dzyd.pdfreader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.os.Message;

public class DownloadUtil {

	public static final int maxPro = 100;

	public static void download(final String urlStr, final String path, final Handler handler) {
		new Thread(new Runnable() {

			@Override
			public void run() {
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
					is = conn.getInputStream();
					byte[] bs = new byte[1024 * 4];
					int len;
					os = new FileOutputStream(file);
					int i = 0;
					int arg = 0;
					while ((len = is.read(bs)) != -1) {
						os.write(bs, 0, len);
						i += len;
						if (i > curPro) {
							Message msg = Message.obtain();
							msg.arg1 = ++arg;
							msg.what = 2;
							handler.sendMessage(msg);
							i = 0;
						}
					}
					handler.sendEmptyMessage(1);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(0);
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
		}).start();
	}

}
