/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.device.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeviceDao extends PagingAndSortingRepository<Device, Long> {

	@Query("select a from Device a where a.categoryCode = ?1 and a.del = '1'")
	List<Device> findByCategoryCode(String categoryCode);
}
