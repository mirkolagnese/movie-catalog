package com.lagnesem.moviecatalog.infra;

import com.lagnesem.moviecatalog.infra.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

}
