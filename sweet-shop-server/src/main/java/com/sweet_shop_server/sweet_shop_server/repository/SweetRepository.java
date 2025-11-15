package com.sweet_shop_server.sweet_shop_server.repository;

import com.sweet_shop_server.sweet_shop_server.entity.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SweetRepository extends JpaRepository<Sweet, Long> {

    boolean existsByName(String name);
}
