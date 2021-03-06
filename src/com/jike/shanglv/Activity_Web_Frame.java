//使用WebView加载内容：只需要提供标题和加载的url即可
package com.jike.shanglv;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity_Web_Frame extends Activity {

	public static final String URL = "redire_url";
	public static final String TITLE = "activity_title";
	private WebView webView;
	private String url;
	private LinearLayout loading_ll;
	private ImageView frame_ani_iv;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_web_frame);
			((MyApplication) getApplication()).addActivity(this);

			((ImageButton) findViewById(R.id.back))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							finish();
						}
					});
			((ImageButton) findViewById(R.id.home))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							startActivity(new Intent(Activity_Web_Frame.this,
									MainActivity.class));
						}
					});

			webView = new WebView(this);
			loading_ll = (LinearLayout) findViewById(R.id.loading_ll);
			frame_ani_iv = (ImageView) findViewById(R.id.frame_ani_iv);
			webView = (WebView) findViewById(R.id.webView);
			WebSettings webSettings = webView.getSettings();
			webSettings.setJavaScriptEnabled(true);// 在WebView中使用JavaScript，若页面中用了JavaScript，必须为WebView使能JavaScript
			url = getIntent().getExtras().getString(URL);
			String title = getIntent().getExtras().getString(TITLE);
			((TextView) findViewById(R.id.title)).setText(title);

			webView.setWebViewClient(new WebViewClient() {// / 不重写的话，会跳到手机浏览器中
				@Override
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) { // Handle the
																	// error
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}

				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					loading_ll.setVisibility(View.GONE);
					webView.setVisibility(View.VISIBLE);
				}
			});
			webView.loadUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		try {
			loading_ll.setVisibility(View.VISIBLE);
			frame_ani_iv.setBackgroundResource(R.anim.frame_rotate_ani);
			AnimationDrawable anim = (AnimationDrawable) frame_ani_iv
					.getBackground();
			anim.setOneShot(false);
			anim.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onResume() {
		webView.loadUrl(url);
		super.onResume();
	}
}
