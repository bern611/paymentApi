package com.bern.norstienpaymentsapi.repositories;

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
public interface PropertyRepository extends CrudRepository<Property, Long>{
    
    List<Property> findAll();
    Property findById(long id);
}
