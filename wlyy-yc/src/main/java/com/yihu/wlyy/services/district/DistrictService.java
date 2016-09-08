package com.yihu.wlyy.services.district;

import com.yihu.wlyy.daos.CityDao;
import com.yihu.wlyy.daos.StreetDao;
import com.yihu.wlyy.daos.TownDao;
import com.yihu.wlyy.models.address.City;
import com.yihu.wlyy.models.address.Street;
import com.yihu.wlyy.models.address.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 省市区三级业务处理类
 * @author George
 *
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class DistrictService {
	
	@Autowired
	private CityDao cityDao;
	@Autowired
	private TownDao townDao;
	@Autowired
	private StreetDao streetDao;

	/**
	 * 查询省市区三级目录
	 * @param type 1一级目录，2二级目录，3三级目录
	 * @param code 目录标识
	 * @return
	 */
	public List<?> findByType(int type, String code) {
		switch (type) {
		case 1:
			return findCity();
		case 2:
			return findTown(code);
		case 3:
			return findStreet(code);
		}
		return null;
	}

	/**
	 * 查询宜昌城市信息
	 * @return
	 */
	public List<City> findCity() {
		List<City> list = new ArrayList<>();
		Iterable<City> iterable = cityDao.findAll(new Sort(Direction.ASC, "id"));
		if (iterable != null) {
			Iterator<City> it = iterable.iterator();
			while (it != null && it.hasNext()) {
				list.add(it.next());
			}
		}
		return list;
	}

	/**
	 * 查询城市下的区县信息
	 * @param city 城市编码
	 * @return 
	 */
	public List<Town> findTown(String city) {
		List<Town> list = new ArrayList<>();
		Iterable<Town> iterable = townDao.findByCity(city);
		if (iterable != null) {
			Iterator<Town> it = iterable.iterator();
			while (it != null && it.hasNext()) {
				list.add(it.next());
			}
		}
		return list;
	}
	
	/**
	 * 查询城市下的街道信息
	 * @param town 区县编码
	 * @return 
	 */
	public List<Street> findStreet(String town) {
		List<Street> list = new ArrayList<>();
		Iterable<Street> iterable = streetDao.findByTown(town);
		if (iterable != null) {
			Iterator<Street> it = iterable.iterator();
			while (it != null && it.hasNext()) {
				list.add(it.next());
			}
		}
		return list;
	}
}
