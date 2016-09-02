package com.yihu.wlyy.util;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/*
 * MD5 算法
 */
public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL带上参数
	 * @param param
	 *            POST参数。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
					StringBuffer buffer = new StringBuffer();
					PrintWriter out = null;
					BufferedReader in = null;
					HttpURLConnection conn = null;
					try {
						URL realUrl = new URL(url);
						// 打开和URL之间的连接
						conn = (HttpURLConnection) realUrl.openConnection();
						conn.setRequestMethod("POST");
						conn.setDoOutput(true);
						conn.setDoInput(true);
						conn.setUseCaches(false);
						conn.setRequestProperty("Content-Type", "application/text");
						conn.setRequestProperty("accept", "*/*");
						conn.setRequestProperty("connection", "Keep-Alive");
						conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
						OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
						osw.write(param.toString());
						osw.flush();

						// 读取返回内容
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
						String temp;
						while ((temp = br.readLine()) != null) {
							buffer.append(temp);
							buffer.append("\n");
						}
					} catch (Exception e) {
						logger.error("push message error:", e);
					} finally {
						try {
							if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return buffer.toString();
	}

	/**
	 * 消息推送
	 * 
	 * @param receiver 消息接收人
	 * @param msgType 消息类型
	 * @param title 消息标题
	 * @param msg 消息内容
	 * @param data 消息数据
	 */
	public static boolean pushMessage(String receiver, String msgType, String title, String msg, String data) {
//		JSONObject param = new JSONObject();
//		param.put("to_uid", receiver);
//		param.put("content", msg);
//		param.put("type", msgType);
//		param.put("title", title);
//		param.put("data", data);

		PrintWriter out = null;
		BufferedReader in = null;
		HttpURLConnection conn = null;
		try {
			String url = SystemConf.getInstance().getMsgPushServer() + "?to_uid=" + receiver + "&content=" + msg + "&type=" + msgType + "&title=" + title + "&data=" + data;
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/json");
//			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//			osw.write(param.toString());
//			osw.flush();

			// 读取返回内容
			StringBuffer buffer = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
			System.out.println(buffer.toString());
			JSONObject json = new JSONObject(buffer.toString());
			if (json.getInt("errno") == 0) {
				// 成功
				return true;
			} else {
				// 失败
				return false;
			}
		} catch (Exception e) {
			logger.error("push message error:", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url 发送请求的 URL带上参数
	 * @param param POST参数。
	 * @param charset 编码格式
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, String charset) {
		StringBuffer buffer = new StringBuffer();
		PrintWriter out = null;
		BufferedReader in = null;
		HttpURLConnection conn = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/text");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), charset);
			osw.write(param.toString());
			osw.flush();

			// 读取返回内容
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			logger.error("push message error:", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		boolean result = HttpUtil.pushMessage("U20160322000001", "1", "您有一条医嘱提醒", "少吃辣，多运动，多吃水果！", null);
		System.out.println(result);
	}

}