package com.greenart.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.firstproject.entity.OptionInfoEntity;

@Repository
public interface OptionInfoRepository extends JpaRepository<OptionInfoEntity, Long>{
    
}