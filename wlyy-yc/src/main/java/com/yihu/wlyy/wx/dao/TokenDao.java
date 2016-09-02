package com.yihu.wlyy.wx.dao;

import com.yihu.wlyy.wx.entity.Token;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TokenDao extends PagingAndSortingRepository<Token, Long> {
	
	@Modifying
	@Query("delete Token a where a.user = ?1")
	int deleteByUser(String user);
	
	@Modifying
	@Query("delete Token a where a.token = ?1")
	int deleteByToken(String token);
	
//	@Query("select a from Token a where a.user = ?1 and a.platform = ?2 and a.del = '1'")
//	Token findByUser(String user, int platform);
	
//	@Query("select a from Token a where a.user = ?1 and a.del = '1'")
//	Token findByUser(String user);
	
//	@Query("select count(1) from Token a where a.user = ?1 and a.del = '1'")
//	int countByUser(String user);
	
	@Query("select a from Token a where a.token = ?1 and a.del = '1'")
	Token findByToken(String token);
	
	@Query("select a from Token a where a.user = ?1 and a.platform = ?2 and a.del = '1'")
	Token findByPatient(String patient, int platform);
	
	@Modifying
	@Query("delete Token a where a.user = ?1 and a.platform = 3 and a.del = '1'")
	int deleteWxTokenByPatient(String patient);

}
