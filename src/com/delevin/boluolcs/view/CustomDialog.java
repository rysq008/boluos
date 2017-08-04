package com.delevin.boluolcs.view;

import com.pusupanshi.boluolicai.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

public class CustomDialog extends Dialog {

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomDialog(Context context) {
		super(context);
	}

	public static class Builder {

		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;

		private DialogInterface.OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {

			this.contentView = v;
			return this;

		}

		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {

			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;

		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {

			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;

		}

		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}
		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public CustomDialog create() {
//			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final CustomDialog dialog = new CustomDialog(context,R.style.progress_dialog);
			dialog.setCanceledOnTouchOutside(false);
//			View layout = inflater.inflate(R.layout.custom_dialog_layout, null);
//			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//			Button BangKa = (Button) layout.findViewById(R.id.btBangk);
//			ImageView imageView = (ImageView) layout.findViewById(R.id.close);
//			BangKa.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v) {
//					positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
//					negativeButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
//				}
//			});
//			imageView.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v) {
//					negativeButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
//				}
//			});
//			dialog.setContentView(layout);
			return dialog;
		}
	}

}
