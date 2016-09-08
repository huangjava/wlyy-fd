/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.address.Street;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StreetDao extends PagingAndSortingRepository<Street, Long> {
	
	// 根據CODE查詢街道名稱
	@Query("select p from Street p where p.code = ?1")
	Street findByCode(String code);
	
	@Query("select a from Street a where a.town = ?1 order by id")
	Iterable<Street> findByTown(String town);
	
}
