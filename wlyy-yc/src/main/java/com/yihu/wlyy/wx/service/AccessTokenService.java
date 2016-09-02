/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yihu.wlyy.wx.service;

import com.yihu.wlyy.wx.dao.AccessTokenDao;
import com.yihu.wlyy.wx.dao.JsapiTicketDao;
import com.yihu.wlyy.wx.entity.AccessToken;
import com.yihu.wlyy.wx.entity.JsapiTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

/**
 * 患者基本信息类.
 * 
 * @author George
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class AccessTokenService  {

	private AccessTokenDao accessTokenDao;
	private JsapiTicketDao jsapiTicketDao;
	
	@Autowired
	public void setJsapiTicketDao(JsapiTicketDao jsapiTicketDao) {
		this.jsapiTicketDao = jsapiTicketDao;
	}

	private Clock clock = Clock.DEFAULT;

	@Autowired
	public void setAccessTokenDao(AccessTokenDao accessTokenDao) {
		this.accessTokenDao = accessTokenDao;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
	
	public Iterable<AccessToken> findAccessToken() {
		return accessTokenDao.findAccessToken();
	}
	
	public Iterable<JsapiTicket> findJsapiTicket() {
		return jsapiTicketDao.findJsapiTicket();
	}
	
	/**
	 * 添加access_token
	 * @param accessToken
	 */
	public void addAccessToken(AccessToken accessToken) {
		accessToken.setAdd_timestamp(System.currentTimeMillis());
		accessToken.setCzrq(clock.getCurrentDate());
		accessTokenDao.save(accessToken);
	}
	
	public void delAccessToken(AccessToken accessToken) {
		accessTokenDao.delete(accessToken);
	}
	
	/**
	 * 添加JsapiTicket
	 * @param JsapiTicket
	 */
	public void addJsapiTicket(JsapiTicket jsapiTicket) {
		jsapiTicket.setAdd_timestamp(System.currentTimeMillis());
		jsapiTicket.setCzrq(clock.getCurrentDate());
		jsapiTicketDao.save(jsapiTicket);
	}
	
	public void delJsapiTicket(JsapiTicket jsapiTicket) {
		jsapiTicketDao.delete(jsapiTicket);
	}

}
