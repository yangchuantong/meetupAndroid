package org.elastos.meetuplib.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.tools.Waiter;

import java.util.HashMap;
import java.util.Map;


public class NativeDataService {
	
	private static NativeDataService nativeDataService;
	
	private NativeDataService(){}
	
	public static NativeDataService getInstance(){
		if(nativeDataService==null){
			nativeDataService = new NativeDataService();
		}
		return nativeDataService;
	}


	/**
	 * 加载本地退版本
	 * @param context
	 * @return
	 */
	public int loadNativeVersion(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_APP_SYSTEM, Activity.MODE_PRIVATE);
		if(preferences!=null){
			return preferences.getInt("versionCode", 0);
		}
		return 0;
	}
	/**
	 * 保存是否提醒
	 * @param context
	 * @param versionCode
	 * @return
	 */
	public boolean saveNativeVersion(Context context,Integer versionCode){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_APP_SYSTEM, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			if(editor!=null){
				editor.putInt("versionCode", versionCode);
				return editor.commit();
			}
		}
		return false;
	}
	/**
	 * 保存钱包相关
	 * @param context
	 * @param map
     * @return
     */
	public boolean saveWallet(Context context,Map<String,String> map){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_WALLET, Activity.MODE_PRIVATE);
		if(preferences!=null){

			Editor editor = preferences.edit();
			if(map.get("keyStore")!=null){
				editor.putString("keyStore",map.get("keyStore").toString());
			}
			if(map.get("privateKey")!=null){
				editor.putString("privateKey",map.get("privateKey").toString());
			}
			if(map.get("publicKey")!=null){
				editor.putString("publicKey",map.get("publicKey").toString());
			}
			if(map.get("did")!=null){
				editor.putString("did",map.get("did").toString());
			}
			if(map.get("address")!=null){
				editor.putString("address",map.get("address").toString());
			}
			if(map.get("ethAddress")!=null){
				editor.putString("ethAddress",map.get("ethAddress").toString());
			}
			if(map.get("mnemonic")!=null){
				editor.putString("mnemonic",map.get("mnemonic").toString());
			}
			if(map.get("privateKeyPwd")!=null){
				editor.putString("privateKeyPwd",map.get("privateKeyPwd").toString());
			}
			return editor.commit();
		}
		return false;
	}
	/**
	 * 删除钱包相关
	 * @param context
	 * @return
	 */
	public boolean deleteWallet(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_WALLET, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			editor.remove("address");
			editor.remove("walletname");
			return editor.commit();
		}
		return false;
	}
	/**
	 * 加载钱包
	 * @param context
	 * @return
	 */
	public Map<String,String> loadWallet(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_WALLET, Activity.MODE_PRIVATE);
		Map<String,String> map=new HashMap<String, String>();
		if(preferences!=null){
			map.put("keyStore", preferences.getString("keyStore",""));
			map.put("publicKey", preferences.getString("publicKey",""));
			map.put("address", preferences.getString("address",""));
			map.put("ethAddress", preferences.getString("ethAddress",""));
			map.put("mnemonic", preferences.getString("mnemonic",""));
			map.put("privateKey", preferences.getString("privateKey",""));
			map.put("privateKeyPwd", preferences.getString("privateKeyPwd",""));
			map.put("did", preferences.getString("did",""));

			return map;
		}
		return map;
	}
	public boolean saveLanguage(Context context,int language){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_USER_PRIVATE, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			editor.putInt("language",language);
			return editor.commit();
		}
		return false;
	}
	//获取语言设置
	public int loadLanguage(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_USER_PRIVATE, Activity.MODE_PRIVATE);
		if(preferences!=null){
			return preferences.getInt("language", 0);
		}
		return 0;
	}
	//保存货币单位
	public boolean saveCurrencyUnit(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_USER_PRIVATE, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			editor.putString("currencyUnit",SystemConfig.currencyUnit);
			return editor.commit();
		}
		return false;
	}
	//获取货币单位设置
	public String loadCurrencyUnit(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_USER_PRIVATE, Activity.MODE_PRIVATE);
		if(preferences!=null){
			SystemConfig.currencyUnit=preferences.getString("currencyUnit", "CNY");
			return SystemConfig.currencyUnit;
		}
		return "CNY";
	}

	/**
	 * 加载上次请求的时间
	 * @param context
	 * @return
	 */
	public Long loadNativeNewsTime(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_APP_SYSTEM, Activity.MODE_PRIVATE);
		if(preferences!=null){
			return preferences.getLong("news_send_time", Waiter.StringForDate2timestamp("2018-04-01 12:00"));
		}
		return  Waiter.StringForDate2timestamp("2018-04-01 12:00");
	}
	/**
	 * 保存消息获取的时间
	 * @param context
	 * @return
	 */
	public boolean saveNativeNewsTime(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_APP_SYSTEM, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			if(editor!=null){
				editor.putLong("news_send_time",  System.currentTimeMillis());
				return editor.commit();
			}
		}
		return false;
	}
	/**
	 * 加载未读消息条数
	 * @param context
	 * @return
	 */
	public int loadNativeNewsRead(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_APP_SYSTEM, Activity.MODE_PRIVATE);
		if(preferences!=null){
			return preferences.getInt("news_send_read", 0);
		}
		return  0;
	}
	/**
	 * 保存未读消息条数
	 * @param context
	 * @return
	 */
	public boolean saveNativeNewsRead(Context context,int count){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_APP_SYSTEM, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			if(editor!=null){
				editor.putInt("news_send_read", count);
				return editor.commit();
			}
		}
		return false;
	}
	//保存上一次发起交易的时间
	public boolean saveTransferTime(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_USER_PRIVATE, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			editor.putLong("transfertime",System.currentTimeMillis());
			return editor.commit();
		}
		return false;
	}
	//获取语言设置
	public boolean loadTransferTime(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_USER_PRIVATE, Activity.MODE_PRIVATE);
		if(preferences!=null){
			long lasttransfertime=preferences.getLong("transfertime", 0);
			if(System.currentTimeMillis()-lasttransfertime<30000){
				return false;
			}

		}
		return true;
	}
}
