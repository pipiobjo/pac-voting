package com.prodyna.pac.repo;

import java.util.List;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.prodyna.pac.domain.Option;

/**
 * Created by bjoern on 17.03.16.
 */
public interface OptionGraphRepository extends GraphRepository<Option> {
	
	List<Option> findAll();
	
	Option findByOptionId(String optionId);
}
