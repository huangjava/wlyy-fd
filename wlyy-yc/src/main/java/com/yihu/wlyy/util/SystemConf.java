package com.yihu.wlyy.util;


import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemConf {

	// 别处登录
	public static final int LOGIN_OTHER = 999;
	// 登录超时
	public static final int LOGIN_TIMEOUT = 998;
	// 未登录
	public static final int NOT_LOGIN = 997;

	private static final String SERVER_IP = "server_ip";
	private static final String SERVER_PORT = "server_port";
	private static final String SERVER_DOMAIN = "server_domain";

	// 消息推送服务器地址
	private static final String MSG_PUSH_SERVER = "msg_push_server";
	// 服务器地址
	private static final String SERVER_URL = "server_url";
	// 图片资源服务器地址
	private static final String IMAGE_SERVER = "image_server";
	// 语音资源服务器地址
	private static final String VOICE_SERVER = "voice_server";
	// 聊天咨询服务器地址
	private static final String CHAT_SERVER = "chat_server";
	// 文件保存临时路径
	private static final String TEMP_PATH = "upload_temp_path";
	// 聊天文件保存路径
	private static final String CHAT_FILE_PATH = "chat_file_path";
	// 图片存放地址
	private static final String IMAGE_PATH = "image_path";
	// 语音存在地址
	private static final String VOICE_PATH = "voice_path";

	private static final String appId = "appId";
	private static final String appSecret = "appSecret";

	// 血糖餐前最小值
	public static final double HEALTH_STANDARD_ST_MIN_BEFORE = 4;
	// 血糖餐前最大值
	public static final double HEALTH_STANDARD_ST_MAX_BEFORE = 7;
	// 血糖餐后最小值
	public static final double HEALTH_STANDARD_ST_MIN_AFTER = 4;
	// 血糖餐后最大值
	public static final double HEALTH_STANDARD_ST_MAX_AFTER = 11.1;

	// 舒张压最小值
	public static final double HEALTH_STANDARD_SZY_MIN = 60;
	// 舒张压最大值
	public static final double HEALTH_STANDARD_SZY_MAX = 90;
	// 收缩压最小值
	public static final double HEALTH_STANDARD_SSY_MIN = 90;
	// 收缩压最大值
	public static final double HEALTH_STANDARD_SSY_MAX = 140;

	// 同一ip最大短信数
	public static final int MAX_SMS_IP = 10;
	// 同一手机号大最短信数
	public static final int MAX_SMS_MOBILE = 5;
	// 发送短信验证码间隔(分钟)
	public static final int SMS_INTERVAL = 2;

	private static Object lock = new Object();

	// 全局系统配置信息
	private static SystemConf systemConf;
	// 系统配置文件
	private Properties systemProperties;

	public static SystemConf getInstance() {
		if (systemConf == null) {
			synchronized (lock) {
				systemConf = new SystemConf();
			}
		}
		return systemConf;
	}

	/**
	 * 加载系统配置文件
	 * @return
	 */
	public Properties getSystemProperties() {
		if (systemProperties == null) {
			InputStream is = null;
			try {
				is = this.getClass().getResourceAsStream("/system.properties");
				systemProperties = new Properties();
				systemProperties.load(is);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return systemProperties;
	}

	/**
	 *  短信接口地址
 	 */
	public String getSmsUrl(){
		return getSystemProperties().getProperty("yihu_sms_url");
	}
	/**
	 *   短信企业编号
	 */
	public String getSmsCode(){
		return getSystemProperties().getProperty("yihu_sms_code");
	}

	/**
	 * 短信用户名
     */
	public String getSmsName(){
		return getSystemProperties().getProperty("yihu_sms_name");
	}

	/**
	 * 短信登录密码
     */
	public String getSmsPassword(){
		return getSystemProperties().getProperty("yihu_sms_password");
	};

	/**
	 *  挂号接口地址
	 */
	public String getGuahaoUrl(){
		return getSystemProperties().getProperty("yihu_guahao_url");
	}

	/**
	 *  挂号接口对接appid
	 */
	public String getGuahaoAppid(){
		return getSystemProperties().getProperty("yihu_guahao_appid");
	}

	/**
	 *  挂号接口对接app secret
	 */
	public String getGuahaoSecret(){
		return getSystemProperties().getProperty("yihu_guahao_secret");
	}


	/**
	 * 获取服务IP地址/域名
	 * @return
	 */
	public String getServerIp() {
		return getSystemProperties().getProperty(SERVER_IP);
	}

	/**
	 * 获取服务端口号
	 * @return
	 */
	public String getServerPort() {
		return getSystemProperties().getProperty(SERVER_PORT);
	}

	/**
	 * 获取服务资源名
	 * @return
	 */
	public String getServerDomain() {
		return getSystemProperties().getProperty(SERVER_DOMAIN);
	}

	/**
	 * 获取图片资源服务器地址
	 * @return
	 */
	public String getImageServer() {
		return getSystemProperties().getProperty(IMAGE_SERVER);
	}

	/**
	 * 获取语音资源服务器地址
	 * @return
	 */
	public String getVoiceServer() {
		return getSystemProperties().getProperty(VOICE_SERVER);
	}

	/**
	 * 获取聊天附件服务器地址
	 * @return
	 */
	public String getChatServer() {
		return getSystemProperties().getProperty(CHAT_SERVER);
	}

	/**
	 * 获取文件保存的临时路径
	 * @return
	 */
	public String getTempPath() {
		return getSystemProperties().getProperty(TEMP_PATH);
	}

	/**
	 * 获取图片存在地址
	 * @return
	 */
	public String getImagePath() {
		return getSystemProperties().getProperty(IMAGE_PATH);
	}

	/**
	 * 获取语音存放地址
	 * @return
	 */
	public String getVoicePath() {
		return getSystemProperties().getProperty(VOICE_PATH);
	}

	/**
	 * 聊天附件保存路径
	 * @return
	 */
	public String getChatPath() {
		return getSystemProperties().getProperty(CHAT_FILE_PATH);
	}

	/**
	 * 获取消息推送服务器
	 * @return
	 */
	public String getMsgPushServer() {
		return getSystemProperties().getProperty(MSG_PUSH_SERVER);
	}

	public String getServerUrlStr() {
		String temp = getSystemProperties().getProperty(SERVER_URL);
		if (StringUtils.isEmpty(temp)) {
			temp = "http://www.xmtyw.cn/wlyy/";
		}
		return temp;
	}
	
	public String getAppId(){
		String temp = getSystemProperties().getProperty(appId);
		if (StringUtils.isEmpty(temp)) {
			temp = "wxad04e9c4c5255acf";
		}
		return temp;
	}
	
	public String getAppSecret(){
		String temp = getSystemProperties().getProperty(appSecret);
		if (StringUtils.isEmpty(temp)) {
			temp = "ae77c48ccf1af5d07069f5153d1ac8d3";
		}
		return temp;
	}

	/**
	 * 获取服务全路径
	 * @return
	 */
	public String getServerUrl() {
		String port = getServerPort();
		port = StringUtils.isEmpty(port) ? "" : ":" + port;
		String domain = getServerDomain();
		domain = StringUtils.isEmpty(port) ? "" : "/" + domain;
		return "http://" + getServerIp() + port + domain + "/";
	}

	public String getValue(String name){
		return getSystemProperties().getProperty(name);
	}

}
