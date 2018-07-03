package com.zxventures.zedelivery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vividsolutions.jts.geom.Point;
import com.zxventures.zedelivery.models.PontoDeVenda;

public interface PdvRepository extends CrudRepository<PontoDeVenda, Long> { 
	
	PontoDeVenda findByDocument(String document);
	
	@Query("select pdv from PontoDeVenda pdv where within(:point, pdv.coverageArea) = true or touches(:point, pdv.coverageArea) = true order by ST_Distance(:point, pdv.address)")
	<T> List<T> searchPdvsThatCovergeThis(@Param("point") Point address, Class<T> type);
	
}
