package com.bankapp.exchange.repository;

import com.bankapp.exchange.entity.Rate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    List<Rate> findAllByBaseFalse();

}