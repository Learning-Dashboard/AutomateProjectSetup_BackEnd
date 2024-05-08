package com.upc.gessi.automation.domain.respositories;

import com.upc.gessi.automation.domain.models.Metric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends CrudRepository<Metric,Integer> {
}
