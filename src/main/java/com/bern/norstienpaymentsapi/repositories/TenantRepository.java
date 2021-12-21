/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.repositories;

/**
 *
 * @author unknown
 */
@Repository
public interface TenantRepository extends CrudRepository<Tenant, Long>{
    Tenant findById(long id);
}
