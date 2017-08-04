package com.delevin.boluolcs.view;

import com.delevin.boluolcs.utils.QntUtils;
import com.pusupanshi.boluolicai.R;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class LayoutEditText extends LinearLayout implements OnClickListener {
	private Button btJia;
	private Button btJian;
	private EditText editText;
	private String shouyi;

	public LayoutEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LayoutEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View v = LayoutInflater.from(context).inflate(
				R.layout.view_layout_edit, this, true);
		init(v);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_jia:
			if (!TextUtils.isEmpty(editText.getText().toString())) {
				int shuzi2 = QntUtils.getInt(editText.getText().toString());
				if (shuzi2 <= 200000) {
					String StringShuzi2 = QntUtils.getString(shuzi2 + 100);

					editText.setText(StringShuzi2);
				} else {
					Toast.makeText(getContext(), "您好！超过200000",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				editText.setText("1");
			}
			break;
		case R.id.btn_jian:
			if (!TextUtils.isEmpty(editText.getText().toString())) {
				int shuzi1 = QntUtils.getInt(editText.getText().toString());
				if (shuzi1 >= 100) {
					String stringShuzi1 = QntUtils.getString(shuzi1 - 100);
					editText.setText(stringShuzi1);
				} else {
					Toast.makeText(getContext(), "您好，少于100", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				editText.setText("1");
			}
			break;
		default:
			break;
		}
	}

	private void init(View v) {
		btJia = (Button) v.findViewById(R.id.btn_jia);
		btJian = (Button) v.findViewById(R.id.btn_jian);
		editText = (EditText) v.findViewById(R.id.editview);
		btJia.setOnClickListener(this);
		btJian.setOnClickListener(this);
	}

	public String getNumber() {
		return editText.getText().toString();
	}
}
