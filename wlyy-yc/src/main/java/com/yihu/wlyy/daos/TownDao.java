/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.address.Town;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TownDao extends PagingAndSortingRepository<Town, Long> {
	// 根據CODE查詢區縣名稱
	@Query("select p from Town p where p.code = ?1")
	Town findByCode(String code);

	@Query("select a from Town a where a.city = ?1 order by id")
	Iterable<Town> findByCity(String province);

	@Query("select a from Town a where a.city = ?1 order by id")
	List<Town> findByCityCode(String province);
}
