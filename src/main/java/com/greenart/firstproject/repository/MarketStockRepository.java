package com.greenart.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.MarketStockEntity;
import com.greenart.firstproject.vo.localadmin.MarketOptionStockVO;

@Repository
public interface MarketStockRepository extends JpaRepository<MarketStockEntity,Long>{
}
                        