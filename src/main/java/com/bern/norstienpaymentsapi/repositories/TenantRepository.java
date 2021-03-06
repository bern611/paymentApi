/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.repositories;

import com.bern.norstienpaymentsapi.entity.Property;
import com.bern.norstienpaymentsapi.entity.Tenant;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author unknown
 */
@Repository
@Transactional
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Tenant findById(long id);

    List<Tenant> findAll();
    
    List<Tenant> findTenantsByProperty(Property property);

    Tenant findByUuid(UUID uuid);

    public void deleteByUuid(UUID uuid);
}
