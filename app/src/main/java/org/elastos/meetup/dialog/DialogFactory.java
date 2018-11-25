package org.elastos.meetup.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.Calendar;


public class DialogFactory {
	/**
	 * 系统信息对话框
	 */
	public static final int KEY_MESSAGE = 1;
	/**
	 * 欢迎对话框
	 */
	public static final int KEY_WELCOME = 2;
	/**
	 * 查看详情对话框
	 */
	public static final int KEY_REGISTVIEW = 13;
	/**
	 * 旋转进度对话框
	 */
	public static final int KEY_CIRCLE_PROGRESS = 3;
	/**
	 * 日期对话框
	 * 变参1 OnDateSetListener接口
	 */
	public static final int KEY_DATE = 4;
	/**
	 * 销售员对话框
	 * 从1开始
	 * 变参1 OnResultCallback接口
	 */
	public static final int KEY_SALESPERSON = 5;

	/**
	 * 确定取消对话框
	 * 
	 * 确定 取消对话框
	 * 变参1 title
	 * 变参2 回调接口DialogInterface.OnClickListener
	 */
	public static final int KEY_OK_CANCEL = 6;
	
	/**
	 * 密码输入对话框
	 * 变参1 title
	 * 变参2 回调接口OnResultCallback
	 */
	public static final int KEY_PASSWD = 7;
	
	/**
	 * 系统信息对话框带
	 * 变参0 title
	 * 变参1  回调DialogInterface.OnClickListener
	 */
	public static final int KEY_MESSAGE_CALLBACK = 8;
	
	/**
	 * 自定义两个button对话框
	 * 
	 * 变参1 title
	 * 变参2 左按钮标题
	 * 变参3 左按钮回调接口DialogInterface.OnClickListener
	 * 变参4 右按钮标题
	 * 变参5 右按钮回调接口DialogInterface.OnClickListener
	 */
	public static final int KEY_CUSTOM = 9;
	
	/**
	 * 自定义两个button对话框
	 * 右按钮为取消按钮
	 * 
	 * 变参1 title
	 * 变参2 左按钮标题
	 * 变参3 左按钮回调接口DialogInterface.OnClickListener
	 */
	public static final int KEY_CUSTOM_2 = 10;
	
	/**
	 * 自定义三button对话框
	 * 右按钮为取消按钮
	 * 变参1 title
	 * 变参2 左按钮标题
	 * 变参3 左按钮回调接口DialogInterface.OnClickListener
	 * 变参4 中按钮标题
	 * 变参5 中按钮回调接口DialogInterface.OnClickListener
	 */
	public static final int KEY_CUSTOM_3 = 11;
	
	/**
	 * 变参1 title
	 * 变参2 message
	 * 变参3 左按钮回调接口DialogInterface.OnClickListener
	 * 变参4 左按钮回调接口DialogInterface.OnClickListener
	 */
	public static final int KEY_TITLE_MESSAGE = 12;
	
	/**
	 * Dialog选择的结果值
	 */
	private static Object resultObject;
	
	/**
	 * 
	 * @param context
	 * @param key
	 * @param objs 可传入接口回调，及其它类型的参数
	 * @return
	 */
	@SuppressLint({ "NewApi", "InlinedApi" })
	public static Dialog factory(final Context context, final int key, final Object... objs) {
		Dialog result = null;
		try {
			switch (key) {
				// 系统信息
				case KEY_MESSAGE:
					result = new AlertDialog.Builder(context)
//						.setIconAttribute(android.R.attr.alertDialogIcon)
						.setPositiveButton("关闭", null)
						.create();
					result.setCancelable(true);
					break;

				// 日期
				case KEY_DATE:
					Calendar today = Calendar.getInstance();
					int dYear = 0;
					if (objs.length > 1) {
						dYear = Integer.valueOf(String.valueOf(objs[1]));
					}
					result = new DatePickerDialog(context, ((OnDateSetListener) objs[0]), today.get(Calendar.YEAR) + dYear, today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
					break;

				// 确定取消对话框
				case KEY_OK_CANCEL:
					result = new AlertDialog.Builder(context)
//						.setIconAttribute(android.R.attr.alertDialogIcon)
						.setTitle(String.valueOf(objs[0]))
						.setNegativeButton("确定", (DialogInterface.OnClickListener)objs[1])
						.setPositiveButton("取消", null)
						.setCancelable(false)
						.create();
					break;

				// 带回调的信息对话框
				case KEY_MESSAGE_CALLBACK:
					result = new AlertDialog.Builder(context)
//						.setIconAttribute(android.R.attr.alertDialogIcon)
						.setTitle(String.valueOf(objs[0]))
						.setPositiveButton("确定", (DialogInterface.OnClickListener) objs[1])
						.setCancelable(false)
						.create();
					break;
				// 双回调信息对话框
				case KEY_CUSTOM:
					result = new AlertDialog.Builder(context)
//						.setIconAttribute(android.R.attr.alertDialogIcon)
						.setTitle(String.valueOf(objs[0]))
						.setNegativeButton(String.valueOf(objs[1]), (DialogInterface.OnClickListener)objs[2])
						.setPositiveButton(String.valueOf(objs[3]), (DialogInterface.OnClickListener)objs[4])
						.setCancelable(false)
						.create();
					break;
				// 自定义双按钮，右按钮为取消
				case KEY_CUSTOM_2:
					result = new AlertDialog.Builder(context)
//						.setIconAttribute(android.R.attr.alertDialogIcon)
						.setTitle(String.valueOf(objs[0]))
						.setNegativeButton(String.valueOf(objs[1]), (DialogInterface.OnClickListener)objs[2])
						.setPositiveButton("取消", null)
						.setCancelable(false)
						.create();
					break;
				// 三回调信息对话框
				case KEY_CUSTOM_3:
					result = new AlertDialog.Builder(context)
//						.setIconAttribute(android.R.attr.alertDialogIcon)
						.setTitle(String.valueOf(objs[0]))
						.setNegativeButton(String.valueOf(objs[1]), (DialogInterface.OnClickListener)objs[2])
						.setNeutralButton(String.valueOf(objs[3]), (DialogInterface.OnClickListener)objs[4])
						.setPositiveButton("取消", null)
						.setCancelable(false)
						.create();
					break;
				case KEY_TITLE_MESSAGE:
					result = new AlertDialog.Builder(context)
//					.setIconAttribute(android.R.attr.alertDialogIcon)
					.setTitle(String.valueOf(objs[0]))
					.setMessage(String.valueOf(objs[1]))
					.setNegativeButton("确定", (DialogInterface.OnClickListener)objs[2])
					.setPositiveButton("取消", (DialogInterface.OnClickListener)objs[3])
					.setCancelable(false)
					.create(); 
			}
		} catch (Exception e) {

				System.out.println(e);

		}
		return result;
	}
}
