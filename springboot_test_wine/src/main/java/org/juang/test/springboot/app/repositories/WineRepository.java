package org.juang.test.springboot.app.repositories;

import org.juang.test.springboot.app.models.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WineRepository extends JpaRepository<Wine,Long> {

    @Query("select c from Wine c where c.name=?1")
    Optional<Wine> findByName(String name);

}
