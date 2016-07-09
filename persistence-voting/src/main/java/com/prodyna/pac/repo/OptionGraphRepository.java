package com.prodyna.pac.repo;

import com.prodyna.pac.domain.Option;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by bjoern on 17.03.16.
 */
public interface OptionGraphRepository extends GraphRepository<Option> {
}
