package com.dzyd.pdfreader;

import java.util.List;

import com.dzyd.widge.SmartEdittext;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Email;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class EditErrorActivity extends Activity implements OnClickListener ,ValidationListener{
	
	@Email
	private SmartEdittext mEdittext;
	private Button mButton;
	private boolean error;
	private Validator validator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editerror_test);
		mEdittext = (SmartEdittext) findViewById(R.id.et_smart);
		mButton = (Button) findViewById(R.id.bt_smart);
		mButton.setOnClickListener(this);
		error = true;
		Validator validator = new Validator(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_smart:
			/*if (error) {
				mEdittext.setError("有错误发生了!");
				error = false;
			} else {
				mEdittext.setError(null);
				error = true;
			}
			*/
			validator.validate();
			break;

		default:
			break;
		}
	}

	@Override
	public void onValidationFailed(List<ValidationError> arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "失败!", 0).show();
	}

	@Override
	public void onValidationSucceeded() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "成功!", 0).show();
	}

}
