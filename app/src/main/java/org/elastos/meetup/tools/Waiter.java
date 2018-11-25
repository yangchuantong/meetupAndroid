package org.elastos.meetup.tools;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.elastos.meetup.R;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Waiter {
	
	
	public static final int cacheSize = 10;
	public static final int mixCacheSize = 3;
	public static final int threadCount = 20;
	
	public static boolean is_welcome = true;
	public static boolean is_reLogin = false;
	
	public static boolean hasGetGeoCoding = false;

	public static final String PREFS_NAME = "JPUSH_EXAMPLE";
	public static final String PREFS_DAYS = "JPUSH_EXAMPLE_DAYS";
	public static final String PREFS_START_TIME = "PREFS_START_TIME";
	public static final String PREFS_END_TIME = "PREFS_END_TIME";
	public static final String KEY_APP_KEY = "JPUSH_APPKEY";
	
	/**
	 * 返回封装参数的map对象
	 * @return
	 */
	public static Map<String,Object> getEmptyIdentityHashMap(){
		SoftReference<Map> sr = new SoftReference<Map>(new IdentityHashMap<String,Object>());
		return sr.get();
	}
	
	/**
	 * 返回空list
	 * @return
	 */
	public static List getEmptyList(){
		List list = new SoftReference<List>(new ArrayList()).get();
		return list;
	}
	
	/**
	 * 得到一个空map，此对象为是重新生成的map对象
	 * @return
	 */
	public static Map getEmptyMap4NewObj(){
		Map map = new SoftReference<Map>(new HashMap()).get();
		return map;
	}
	
	/**
	 * 返回传递参数的bundle对象
	 * @return
	 */
	public static Bundle getEmptyBundle(){
		SoftReference<Bundle> sr = new SoftReference<Bundle>(new Bundle());
		return sr.get();
	}
	/**
	 * 设置应用的版本信息
	 * @param params
	 * @param application
	 * @return
	 */
	public static Map<String, Object> setVersionInfo(Map<String, Object> params,Context context,Application application){


		params.put("mobilefrom", "android");
		params.put("tid", "loytoken");
		params.put("walletversion", "164");
		return params;
	}

	/**
	 * 得到应用的包信息
	 * @param application
	 * @return
	 * @throws PackageManager.NameNotFoundException
	 */
	public static PackageInfo getAppPackageInfo(Application application) throws PackageManager.NameNotFoundException {
		PackageInfo packageInfo = null;
		packageInfo = application.getPackageManager().getPackageInfo(application.getResources().getString(R.string.package_name), 0);
		return packageInfo;
	}
	/**
	 * 得到自定义的progressDialog
	 *
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog initProgressDialog(Context context, String msg) {

		// 首先得到整个View
		View view = LayoutInflater.from(context).inflate(
				R.layout.progress_loading, null);
		// 获取整个布局
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.dialog_view);
		// 页面中显示文本
		TextView tipText = (TextView) view.findViewById(R.id.tipTextView);
		// 显示文本
		tipText.setText(msg);

		// 创建自定义样式的Dialog
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		// 设置返回键无效
		loadingDialog.setCancelable(false);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		return loadingDialog;
	}
	/**
	 * 弹出错误信息
	 * @param errorMessage
	 * @param context
	 */
	public static void alertErrorMessage(String errorMessage,Context context){
		if(errorMessage!=null&&errorMessage.length()>0){
			Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 检查是否为空
	 * @param editText
	 * @return
	 */
	public static boolean CheckEditText(EditText editText) {
		if (TextUtils.isEmpty(editText.getText())) {
			editText.setHint(R.string.message_notnull);
			editText.requestFocus();
			return false;
		}
		return true;
	}
	/**
	 * 判断网络是否可用
	 * @param context
	 * @return   true网络可用       false网络不可用
	 */
	public static boolean netWorkAvailable(Context context){
		ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}

		NetworkInfo networkinfo = manager.getActiveNetworkInfo();

		if (networkinfo == null || !networkinfo.isAvailable()||networkinfo.getState()!= NetworkInfo.State.CONNECTED||!networkinfo.isConnected()) {
			return false;
		}
		int nType = networkinfo.getType();

		int netType = 0;
		if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = 1;// wifi
		} else if (nType == ConnectivityManager.TYPE_MOBILE) {
			int nSubType = networkinfo.getSubtype();
			TelephonyManager mTelephony = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
					&& !mTelephony.isNetworkRoaming()) {
				netType = 2;// 3G
			} else {
				netType = 3;// 2G
			}
		}
		if(netType==0){
			return false;
		}
		return true;
	}
	/**
	 * 功能说明: 将dip转换为px
	 *
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density; // 像素点密度比
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 功能说明: 将px转换为dip
	 *
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density; // 像素点密度比
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * @param context
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * @param context
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}


	//乘以10的18次方
	public static BigDecimal typeConversionByMultiply(String str){
		BigDecimal multiplicand = new BigDecimal(str);//被乘数
		BigDecimal multiplier = new BigDecimal(Math.pow(10, 18));//乘数
		BigDecimal quotient = multiplicand.multiply(multiplier);
		return quotient;

	}
	//除以10的18次方
	public static String typeConversionByDivide(BigDecimal dividend){

		BigDecimal divisor = new BigDecimal(Math.pow(10, 9));//除数
		BigDecimal quotient = dividend.divide(divisor,1, BigDecimal.ROUND_HALF_EVEN);
		if (0==quotient.compareTo(new BigDecimal(new BigInteger("0")))){
			return "0";
		}
		return quotient.toPlainString();

	}
	public static boolean isEmpty(String s) {
		if (null == s)
			return true;
		if (s.length() == 0)
			return true;
		if (s.trim().length() == 0)
			return true;
		return false;
	}
	/**
	 * 只能以 “+” 或者 数字开头；后面的内容只能包含 “-” 和 数字。
	 * */
	private final static String MOBILE_NUMBER_CHARS = "^[+0-9][-0-9]{1,}$";
	public static boolean isValidMobileNumber(String s) {
		if(TextUtils.isEmpty(s)) return true;
		Pattern p = Pattern.compile(MOBILE_NUMBER_CHARS);
		Matcher m = p.matcher(s);
		return m.matches();
	}
	// 校验Tag Alias 只能是数字,英文字母和中文
	public static boolean isValidTagAndAlias(String s) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	// 取得AppKey
	public static String getAppKey(Context context) {
		Bundle metaData = null;
		String appKey = null;
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			if (null != ai)
				metaData = ai.metaData;
			if (null != metaData) {
				appKey = metaData.getString(KEY_APP_KEY);
				if ((null == appKey) || appKey.length() != 24) {
					appKey = null;
				}
			}
		} catch (PackageManager.NameNotFoundException e) {

		}
		return appKey;
	}

	// 取得版本号
	public static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			return "Unknown";
		}
	}

	public static void showToast(final String toast, final Context context)
	{
		new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}).start();
	}

	public static boolean isConnected(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}

	public static String getImei(Context context, String imei) {
		String ret = null;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			ret = telephonyManager.getDeviceId();
		} catch (Exception e) {

		}
		if (isReadableASCII(ret)){
			return ret;
		} else {
			return imei;
		}
	}

	private static boolean isReadableASCII(CharSequence string){
		if (TextUtils.isEmpty(string)) return false;
		try {
			Pattern p = Pattern.compile("[\\x20-\\x7E]+");
			return p.matcher(string).matches();
		} catch (Throwable e){
			return true;
		}
	}

	/**
	 * 时间戳转字符串 只有日期
	 * @param timestamp
	 * @return
	 */
	public static String timestamp2StringForDate(Long timestamp,String formattype){
		if(timestamp!=null){
			if(formattype.equals("")){
				formattype="yyyy-MM-dd";
			}
			SimpleDateFormat df = new SimpleDateFormat(formattype, Locale.getDefault());
			if(timestamp<0){
				timestamp=~timestamp;
			}
			Date date = new Date(timestamp);
			return df.format(date);
		}
		return "";
	}

	/**
	 * 时间戳转字符串 带具体时间
	 * @param timestamp
	 * @return
	 */
	public static String timestamp2StringForTime(Long timestamp){
		if(timestamp!=null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(timestamp);
			return format.format(date);
		}
		return "";
	}
	/**
	 * 时间戳转字符串只要时间不要年
	 * @param timestamp
	 * @return
	 */
	public static String timestamp2StringForHour(Long timestamp){
		if(timestamp!=null){
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Date date = new Date(timestamp);
			return format.format(date);
		}
		return "";
	}



	/**
	 * 字符串转时间戳
	 * @param strdate
	 * @return
	 */
	public static Long StringForDate2timestamp(String strdate){
		if(strdate!=null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

			Date date;
			try {
				date = format.parse(strdate);
				return date.getTime()/1000;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return 0l;
	}
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String nowdate(){

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowdate=df.format(new Date());
		return nowdate;

	}
	/**
	 * 获取指定时间距离现在的时间差
	 * @return
	 */
	public static long getDatePoor(String startDate) {
        long nowtime=System.currentTimeMillis()/1000;
		long startTime=StringForDate2timestamp(startDate);
		long nd = 24 * 60 * 60;
		long nh = 60 * 60;
		long nm =  60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = nowtime-startTime;
		// 计算差多少天
		long day = diff / nd;

		// long sec = diff % nd % nh % nm / ns;
		return day;
	}
	/**
	 * 检查是否存在SDCard
	 * @return
	 */
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
}
