package com.dzyd.widge;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;

public class SmartEdittext extends EditText {

	public SmartEdittext(Context context) {
		super(context);
	}

	public SmartEdittext(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setError(CharSequence error) {
		if (error == null) {
			setBackgroundDrawable(null);
		} else {
			setBackgroundResource(R.color.holo_blue_bright);
		}
	}

}
