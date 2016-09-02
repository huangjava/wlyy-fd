/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yihu.wlyy.wx.dao;

import com.yihu.wlyy.wx.entity.AccessToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccessTokenDao extends PagingAndSortingRepository<AccessToken, Long> {
	
	@Query("select p from AccessToken p order by p.add_timestamp desc")
	Iterable<AccessToken> findAccessToken();
	
}
