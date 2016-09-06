/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.device.DeviceCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeviceCategoryDao extends PagingAndSortingRepository<DeviceCategory, Long> {
	@Query("select a from DeviceCategory a where a.del = '1'")
	List<DeviceCategory> findAll();

	@Query("select a from DeviceCategory a where a.code = ?1 and  a.del = '1'")
	DeviceCategory findByCode(String code);
}
