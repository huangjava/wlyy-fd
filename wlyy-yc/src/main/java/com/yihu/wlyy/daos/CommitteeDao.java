/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.address.Committee;
import com.yihu.wlyy.models.address.Street;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommitteeDao extends PagingAndSortingRepository<Committee, Long> {
	
	// 根據CODE查詢居委会名稱
	@Query("select p from Committee p where p.code = ?1")
	Street findByCode(String code);
	
	@Query("select a from Committee a where a.street = ?1 order by id")
	Iterable<Committee> findByStreet(String street);
	
}
