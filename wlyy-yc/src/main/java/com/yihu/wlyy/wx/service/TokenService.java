package com.yihu.wlyy.wx.service;

import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.wx.dao.TokenDao;
import com.yihu.wlyy.wx.entity.Token;
import com.yihu.wlyy.wx.util.SystemData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class TokenService  {

	@Autowired
	public TokenDao tokenDao;

	/**
	 * 生成token
	 * @param user 用户标识
	 * @param imei 手机IMEI码
	 * @param platform 1患者，2医生，3患者微信公众号
	 * @return
	 * @throws Exception
	 */
	public Token newToken(String user, String imei, int platform) throws Exception {
		Date czrq = new Date();
		String tokenStr = platform + imei + System.currentTimeMillis();
		// MD5加密
		//tokenStr = MD5.GetMD5Code(tokenStr);
//		Token token = tokenDao.findByPatient(user, platform);
//		if (token == null) {
//			token = new Token();
//		}
		Token token = new Token();
		token.setDel("1");
		token.setPlatform(platform);
		token.setImei(imei);
		// 30天的有限期
		token.setTimeout(DateUtil.strToDate(DateUtil.getNextDay(DateUtil.getStringDate(DateUtil.YYYY_MM_DD), 30), DateUtil.YYYY_MM_DD));
		token.setToken(tokenStr);
		token.setUser(user);
		token.setCzrq(czrq);
		// 先删除防止重复
		tokenDao.deleteByUser(user);
		// 添加新的token
		token = tokenDao.save(token);
		if (token == null) {
			throw new Exception("Token生成失败");
		}
		// 更新token缓存
		if (platform == 1) {
			SystemData.patientTokens.put(user, token);
		} else if (platform == 2) {
			SystemData.doctorTokens.put(user, token);
		} else if (platform == 3) {
			SystemData.wxPatientTokens.put(user, token);
		}
		return token;
	}

	public Token newTxToken(String user, String openid) throws Exception {
		Date czrq = new Date();
		String tokenStr = 3 + openid + System.currentTimeMillis();
		// MD5加密
		//tokenStr = MD5.GetMD5Code(tokenStr);
		Token token = new Token();
		token.setDel("1");
		token.setPlatform(3);
		token.setImei(openid);
		// 360天的有限期
		token.setTimeout(DateUtil.strToDate(DateUtil.getNextDay(DateUtil.getStringDate(DateUtil.YYYY_MM_DD), 360), DateUtil.YYYY_MM_DD));
		token.setToken(tokenStr);
		token.setUser(user);
		token.setCzrq(czrq);
		// 先删除，防止重复
		tokenDao.deleteByUser(user);
		// 添加新的token
		token = tokenDao.save(token);
		if (token == null) {
			throw new Exception("Token生成失败");
		}
		// 更新token缓存
		SystemData.wxPatientTokens.put(user, token);
		return token;
	}

	/**
	 * 删除token
	 * @param uid 用户code
	 * @return
	 * @throws Exception
	 */
	public void delToken(int platform, String uid) throws Exception {
		// 删除老的token
		tokenDao.deleteByUser(uid);
		// 更新token缓存
		if (platform == 1) {
			SystemData.patientTokens.remove(uid);
			//删除用户的openId
			//Patient patient=patientDao.findByCode(uid);
			//patient.setOpenid("");
		} else if (platform == 2) {
			SystemData.doctorTokens.remove(uid);
		} else if (platform == 3) {
			SystemData.wxPatientTokens.remove(uid);
		}
	}

	/**
	 * 查询患者的微信token标识
	 * @param patient
	 */
	public Token findWxToken(String patient) {
		return tokenDao.findByPatient(patient, 3);
	}

}
