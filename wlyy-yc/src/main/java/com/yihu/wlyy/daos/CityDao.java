/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.address.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CityDao extends PagingAndSortingRepository<City, Long> {
	// 根據CODE查詢城市名稱
	@Query("select p from City p where p.code = ?1")
	City findByCode(String code);
	
	@Query("select a from City a where a.province = ?1 order by id")
	Iterable<City> findByProvince(String province);
}
