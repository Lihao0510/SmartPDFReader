package com.dzyd.pdfreader;

import static java.lang.String.format;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import android.app.Activity;
import android.os.Bundle;

public class PDFReaderActivity extends Activity implements OnPageChangeListener{
	
	public static final String SAMPLE_FILE = "sample.pdf";
    public static final String ABOUT_FILE = "about.pdf";
    private PDFView pdfView;
    
    String pdfName = SAMPLE_FILE;

    Integer pageNumber = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdf_webview);
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		pdfView = (PDFView) findViewById(R.id.pdfView);
	}

	private void initData() {
		// TODO Auto-generated method stub
		display(pdfName, true);
	}
	
	private void display(String assetFileName, boolean jumpToFirstPage) {
        if (jumpToFirstPage) pageNumber = 1;
        setTitle(pdfName = assetFileName);

        pdfView.fromAsset(assetFileName)
                .defaultPage(pageNumber)
                .enableDoubletap(true)
                .onPageChange(this)
                .load();
    }
     
	@Override
	public void onPageChanged(int page, int pageCount) {
		// TODO Auto-generated method stub
		pageNumber = page;
        setTitle(format("%s %s / %s", pdfName, page, pageCount));
	}
 
}
