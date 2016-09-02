package com.yihu.wlyy.web;

import com.yihu.wlyy.util.SystemConf;
import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.util.HttpUtil;
import com.yihu.wlyy.util.ImageCompress;
import com.yihu.wlyy.wx.entity.AccessToken;
import com.yihu.wlyy.wx.entity.JsapiTicket;
import com.yihu.wlyy.wx.service.AccessTokenService;
import com.yihu.wlyy.wx.service.TokenService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WeixinBaseController extends BaseController {

	@Autowired
	private AccessTokenService accessTokenService;
	@Autowired
	private TokenService tokenService;

	/**
	 * 通过code获取判断openid
	 * 
	 * @param code
	 * @return
	 */
	public String getOpenidByCode(String code) {
		try {
			String token_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
			String params = "appid=" + SystemConf.getInstance().getAppId() + "&secret=" + SystemConf.getInstance().getAppSecret() + "&code=" + code + "&grant_type=authorization_code";
			String result = HttpUtil.sendGet(token_url, params);
			JSONObject json = new JSONObject(result);
			if (json.has("openid")) {
				return json.get("openid").toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			error(e);
		}
		return null;
	}

	/**
	 * 通过code获取判断openid
	 * 
	 * @param code
	 * @return
	 */
	public String getOpenid(String code) {
		try {
			String token_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
			String params = "appid=" + SystemConf.getInstance().getAppId() + "&secret=" + SystemConf.getInstance().getAppSecret() + "&code=" + code + "&grant_type=authorization_code";
			String result = HttpUtil.sendGet(token_url, params);
			JSONObject json = new JSONObject(result);
			Map<Object, Object> map = new HashMap<Object, Object>();
			if (json.has("openid")) {
				String openid = json.get("openid").toString();
//				Patient patient = patientService.findByOpenid(openid);
//				if (patient != null) {
//					// 返回用户信息
//					map.put("id", patient.getId());
//					map.put("uid", patient.getCode());
//					map.put("name", patient.getName());
//					map.put("openid", patient.getOpenid());
//					map.put("photo", patient.getPhoto());
//					// 查询token
//					Token token = SystemData.wxPatientTokens.get(patient.getCode());
//					if (token == null) {
//						// 从数据库加载
//						token = tokenService.findWxToken(patient.getCode());
//					}
//					if (token == null) {
//						// 生成新的token
//						token = tokenService.newTxToken(patient.getCode(), openid);
//					}
//					map.put("token", token.getToken());
					return write(200, "获取成功", "data", map);
//				} else {
//					return error(100, openid);
//				}
			} else {
				return error(Integer.parseInt(json.get("errcode").toString()), "操作失败:" + json.get("errmsg").toString());
			}
		} catch (Exception e) {
			error(e);
			return invalidUserException(e, -1, "操作失败！");
		}
	}

	/**
	 * 拼接地址？后的参数
	 * 
	 * @param data
	 * @param type
	 * @return
	 */
	public String getParamUrl(String data, int type) {
		try {
			JSONObject jsonData = new JSONObject(data);
			String id = jsonData.get("id").toString();
			String uid = jsonData.get("uid").toString();
			String name = jsonData.get("name").toString();
			String openid = jsonData.get("openid").toString();
			String photo = jsonData.get("photo").toString();
			String token = jsonData.get("token").toString();
			String paramUrl = "?type=" + type + "&id=" + id + "&uid=" + uid + "&name=" + name + "&openid=" + openid + "&photo=" + photo + "&token=" + token;
			return paramUrl;
		} catch (Exception e) {
			error(e);
			return "";
		}
	}

	/**
	 * 获取微信的access_token
	 * 
	 * @return
	 */
	public String getAccessToken() {
		try {
			Iterable<AccessToken> accessTokens = accessTokenService.findAccessToken();
			if (accessTokens != null) {
				for (AccessToken accessToken : accessTokens) {
					if ((System.currentTimeMillis() - accessToken.getAdd_timestamp()) < (accessToken.getExpires_in() * 1000)) {
						return accessToken.getAccess_token();
					} else {
						accessTokenService.delAccessToken(accessToken);
						break;
					}
				}
			}
			String token_url = "https://api.weixin.qq.com/cgi-bin/token";
			String params = "grant_type=client_credential&appid=" + SystemConf.getInstance().getAppId() + "&secret=" + SystemConf.getInstance().getAppSecret();
			String result = HttpUtil.sendGet(token_url, params);
			JSONObject json = new JSONObject(result);
			if (json.has("access_token")) {
				String token = json.get("access_token").toString();
				String expires_in = json.get("expires_in").toString();
				AccessToken newaccessToken = new AccessToken();
				newaccessToken.setAccess_token(token);
				newaccessToken.setExpires_in(Long.parseLong(expires_in));
				accessTokenService.addAccessToken(newaccessToken);
				return token;
			} else {
				return null;
			}
		} catch (Exception e) {
			error(e);
			return null;
		}
	}

	/**
	 * 获取微信的jsapi_ticket
	 * 
	 * @return
	 */
	public String getJsapi_ticketByToken() {
		try {
			Iterable<JsapiTicket> jsapiTickets = accessTokenService.findJsapiTicket();
			if (jsapiTickets != null) {
				for (JsapiTicket jsapiTicket : jsapiTickets) {
					if ((System.currentTimeMillis() - jsapiTicket.getAdd_timestamp()) < (jsapiTicket.getExpires_in() * 1000)) {
						return jsapiTicket.getJsapi_ticket();
					} else {
						accessTokenService.delJsapiTicket(jsapiTicket);
						break;
					}
				}
			}
			String token = getAccessToken();
			if (token != null) {
				String token_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
				String params = "access_token=" + token + "&type=jsapi";
				String result = HttpUtil.sendGet(token_url, params);
				JSONObject json = new JSONObject(result);
				if (json.has("ticket")) {
					String ticket = json.get("ticket").toString();
					String expires_in = json.get("expires_in").toString();
					JsapiTicket newJsapiTicket = new JsapiTicket();
					newJsapiTicket.setJsapi_ticket(ticket);
					newJsapiTicket.setExpires_in(Long.parseLong(expires_in));
					accessTokenService.addJsapiTicket(newJsapiTicket);
					return ticket;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			error(e);
			return null;
		}
	}

	/**
	 * @description： SHA、SHA1加密 @parameter： str：待加密字符串 @return： 加密串
	 **/
	public String SHA1(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1"); // 如果是SHA加密只需要将"SHA-1"改成"SHA"即可
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexStr = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexStr.append(0);
				}
				hexStr.append(shaHex);
			}
			return hexStr.toString();

		} catch (Exception e) {
			error(e);
		}
		return null;
	}

	/**
	 * 获取微信服务器图片
	 * 
	 * @return
	 */
	public String fetchWxImages() {
		String photos = "";
		try {
			String images = request.getParameter("mediaIds");
			if (StringUtils.isEmpty(images)) {
				return photos;
			}
			String[] mediaIds = images.split(",");
			for (String mediaId : mediaIds) {
				if (StringUtils.isEmpty(mediaId)) {
					continue;
				}
				String temp = saveImageToDisk(mediaId);
				if (!StringUtils.isEmpty(temp)) {
					if (photos.length() == 0) {
						photos = temp;
					} else {
						photos += "," + temp;
					}
				}
			}
		} catch (Exception e) {
			error(e);
		}
		return photos;
	}

	/**
	 * 获取下载图片信息（jpg）
	 * 
	 * @param mediaId 文件的id
	 * @throws Exception
	 */
	public String saveImageToDisk(String mediaId) throws Exception {
		// 文件保存的临时路径
		String tempPath = SystemConf.getInstance().getTempPath() + File.separator;
		// 拼接年月日路径
		String datePath = DateUtil.getStringDate("yyyy") + File.separator + DateUtil.getStringDate("MM") + File.separator + DateUtil.getStringDate("dd") + File.separator;
		// 重命名文件
		String newFileName = DateUtil.dateToStr(new Date(), DateUtil.YYYYMMDDHHMMSS) + "_" + new Random().nextInt(1000) + ".png";
		// 保存路径
		File uploadFile = new File(tempPath + datePath + newFileName);

		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			if (!uploadFile.getParentFile().exists()) {
				uploadFile.getParentFile().mkdirs();
			}
			inputStream = getInputStream(mediaId);
			byte[] data = new byte[1024];
			int len = 0;
			fileOutputStream = new FileOutputStream(uploadFile);
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
			// 生成缩略图
			ImageCompress.compress(uploadFile.getAbsolutePath(), uploadFile.getAbsolutePath() + "_small", 300, 300);
			// 返回保存路径
			return datePath + newFileName;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取微信服务器语音
	 * 
	 * @return
	 */
	public String fetchWxVoices() {
		String voiceIds = "";
		try {
			String voices = request.getParameter("voices");
			if (StringUtils.isEmpty(voices)) {
				return voices;
			}
			String[] mediaIds = voices.split(",");
			for (String mediaId : mediaIds) {
				if (StringUtils.isEmpty(mediaId)) {
					continue;
				}
				String temp = saveVoiceToDisk(mediaId);
				if (!StringUtils.isEmpty(temp)) {
					if (voiceIds.length() == 0) {
						voiceIds = temp;
					} else {
						voiceIds += "," + temp;
					}
				}
			}
		} catch (Exception e) {
			error(e);
		}
		return voiceIds;
	}

	/**
	 * 获取下载语音信息（jpg）
	 * 
	 * @param mediaId 文件的id
	 * @throws Exception
	 */
	public String saveVoiceToDisk(String mediaId) throws Exception {
		// 文件保存的临时路径
		String tempPath = SystemConf.getInstance().getTempPath() + File.separator;
		// 拼接年月日路径
		String datePath = DateUtil.getStringDate("yyyy") + File.separator + DateUtil.getStringDate("MM") + File.separator + DateUtil.getStringDate("dd") + File.separator;
		// 重命名文件
		String newFileName = DateUtil.dateToStr(new Date(), DateUtil.YYYYMMDDHHMMSS) + "_" + new Random().nextInt(1000) + ".amr";
		// 保存路径
		File uploadFile = new File(tempPath + datePath + newFileName);

		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			if (!uploadFile.getParentFile().exists()) {
				uploadFile.getParentFile().mkdirs();
			}
			inputStream = getInputStream(mediaId);
			byte[] data = new byte[1024];
			int len = 0;
			fileOutputStream = new FileOutputStream(uploadFile);
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
			// 返回保存路径
			return datePath + newFileName;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 下载多媒体文件（请注意，视频文件不支持下载，调用该接口需http协议）
	 *
	 * @return
	 */
	private InputStream getInputStream(String mediaId) {
		String accessToken = getAccessToken();
		InputStream is = null;
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + mediaId;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			// 获取文件转化为byte流
			is = http.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}

}
