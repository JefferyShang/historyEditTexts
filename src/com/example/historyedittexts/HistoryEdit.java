package com.example.historyedittexts;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 自定义EditText
 * 
 * @author Jeffery<br>
 *         创建日期：2016年10月9日
 * @version 1.0
 *
 */
public class HistoryEdit extends EditText {
	private Context mContext;
	private PopupWindow popupWindow;
	private LinearLayout linearLayout;
	private ScrollView scrollView;

	private Drawable mDrawable;
	/** 联系人名称 **/
	private ArrayList<String> mContactsName = new ArrayList<String>();

	/** 联系人号码 **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	public HistoryEdit(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public HistoryEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	public HistoryEdit(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		initView();
	}

	/*
	 * 初始化一个LinearLayout布局 用于显示在popupwindow上
	 */
	private void initView() {
		scrollView = (ScrollView) View.inflate(mContext, R.layout.history_scrollview, null);
		// linearLayout = new LinearLayout(mContext);
		linearLayout = (LinearLayout) scrollView.findViewById(R.id.his_li);
		// linearLayout.setLayoutParams(new
		// LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT));
		// linearLayout.setOrientation(LinearLayout.VERTICAL);
		mDrawable = mContext.getResources().getDrawable(R.drawable.clear_icon);

		this.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				updateCleanable(length(), hasFocus);
			}
		});
	}

	// 当内容不为空，而且获得焦点，才显示右侧删除按钮
	public void updateCleanable(int length, boolean hasFocus) {
		if (length() > 0 && hasFocus)
			setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
		else
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
	}

	// 内容发生变化时调用
	@Override
	protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		updateCleanable(length(), true);
		if (text.length() > 0) {

			if (linearLayout != null) {
				linearLayout.removeAllViews();
			}
			if (popupWindow != null) {
				popupWindow.dismiss();
			}
			String str = text + "";
			// Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
			for (int i = 0; i < mContactsNumber.size(); i++) { // 拉取集合中的历史数据
																// 有几个历史数据

				if (mContactsNumber.get(i).startsWith(str)) {// 就创建几个textview
					showUI(i);
				}
			}
			showWindow();
		}

	}

	private void showUI(int i) {

		RelativeLayout relativeLayout = new RelativeLayout(mContext);
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		// rl.addRule(RelativeLayout.ALIGN_LEFT);
		// rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		relativeLayout.setLayoutParams(rl);
		relativeLayout.setPadding(20, 20, 20, 20);
		// 号码
		final TextView textView = new TextView(mContext);
		textView.setText(mContactsNumber.get(i));
		RelativeLayout.LayoutParams tvNumber = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		// textView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		tvNumber.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		textView.setLayoutParams(tvNumber);// 设置宽高
		// textView.setPadding(10, 10, 10, 10);
		// 名字
		TextView textViewName = new TextView(mContext);
		textViewName.setText(mContactsName.get(i));
		RelativeLayout.LayoutParams tvName = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		// textViewName.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		tvName.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		textViewName.setLayoutParams(tvName);// 设置宽高
		// textViewName.setPadding(10, 10, 10, 10);
		relativeLayout.setOnClickListener(new OnClickListener() { // text点击事件
			// 点击后设置edittext的值
			@Override
			public void onClick(View v) {
				setText(textView.getText().toString());// 给EditText赋值
				setSelection(textView.getText().toString().length());// 设置EditText光标位置
				popupWindow.dismiss();
			}
		});
		relativeLayout.addView(textView); /// 添加到linerlayout中
		relativeLayout.addView(textViewName); /// 添加到linerlayout中

		linearLayout.addView(relativeLayout);
	}

	/*
	 * 设置历史数据的方法
	 */
	public void setData(ArrayList<Directory> mContacts) {
		// this.strings = mContacts.get(index);
		if (mContacts == null || mContacts.size() == 0) { // 如果历史数据集合为null，或者不包含历史数据，则移除linerlayout中的所有布局，并且隐藏popupwindow
			if (linearLayout != null) {
				linearLayout.removeAllViews();
			}
			if (popupWindow != null) {
				popupWindow.dismiss();
			}
			return;
		}
		for (int i = 0; i < mContacts.size(); i++) { // 拉取集合中的历史数据 有几个历史数据
														// 就创建几个textview
			mContactsNumber.add(mContacts.get(i).getContactNumber());
			mContactsName.add(mContacts.get(i).getContactName());
			showUI(i);
		}
		// showHistory();
	}

	private void showHistory() {
		// 添加历史记录
		TextView history = new TextView(mContext); // 添加消除历史记录的textview
		history.setText("消除历史记录");
		history.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		history.setPadding(10, 10, 10, 10);
		history.setGravity(Gravity.CENTER_HORIZONTAL);
		history.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 设置点击事件
				setData(null);
			}
		});
		linearLayout.addView(history);
	}

	public void showWindow() {
		if (popupWindow != null && popupWindow.isShowing()) {
			return; // 如果popupwindow 在显示状态 则不作处理
		} else if (popupWindow != null && !popupWindow.isShowing()) {
			popupWindow.showAsDropDown(this); // 如果popupwindow 是隐藏状态 则显示出来
			return;
		}
		// 否则创建popupwindow 并且显示
		popupWindow = new PopupWindow(scrollView, getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.quanaplha));
		popupWindow.update();
		popupWindow.setFocusable(false);
		popupWindow.showAsDropDown(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// 每次手指按下的时候 调用showWindow();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			showWindow();
		}
		
		//点“X”时清空数据
		final int DRAWABLE_RIGHT = 2;
		// 可以获得上下左右四个drawable，右侧排第二。图标没有设置则为空。
		Drawable rightIcon = getCompoundDrawables()[DRAWABLE_RIGHT];
		if (rightIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
			// 检查点击的位置是否是右侧的删除图标
			// 注意，使用getRwwX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
			int leftEdgeOfRightDrawable = getRight() - getPaddingRight() - rightIcon.getBounds().width();
			if (event.getRawX() >= leftEdgeOfRightDrawable) {
				setText("");
			}
		}
		return super.onTouchEvent(event);
	}
}
