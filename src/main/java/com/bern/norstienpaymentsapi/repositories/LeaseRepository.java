/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.repositories;

import com.bern.norstienpaymentsapi.entity.Lease;
import com.bern.norstienpaymentsapi.entity.Tenant;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author unknown
 */
@Transactional
public interface LeaseRepository extends JpaRepository<Lease, Long> {

    List<Lease> findAll();

    Lease findById(long id);

    Lease findByUuid(UUID uuid);

    List<Lease> findLeasesByTenant(Tenant tenant);

    public void deleteByUuid(UUID uuid);
}
