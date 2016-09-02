package com.yihu.wlyy.wx.util;


import com.yihu.wlyy.wx.entity.Token;

import java.util.HashMap;
import java.util.Map;

public class SystemData {

	// 医生验证信息
	public static Map<String, Token> doctorTokens = new HashMap<String, Token>();
	// 患者验证信息
	public static Map<String, Token> patientTokens = new HashMap<String, Token>();
	// 患者公众号验证信息
	public static Map<String, Token> wxPatientTokens = new HashMap<String, Token>();

}
