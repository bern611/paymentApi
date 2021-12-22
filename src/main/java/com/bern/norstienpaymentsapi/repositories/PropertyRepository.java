package com.bern.norstienpaymentsapi.repositories;

import com.bern.norstienpaymentsapi.entity.Property;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author unknown
 */
@Repository
@Transactional
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findAll();

    Property findById(long id);

    Property findByUuid(UUID uuid);
    
    Long deleteByUuid(UUID uuid);
    
    Long deleteById(long id);


}
