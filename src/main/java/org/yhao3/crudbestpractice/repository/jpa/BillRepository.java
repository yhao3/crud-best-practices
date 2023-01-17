package org.yhao3.crudbestpractice.repository.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.yhao3.crudbestpractice.domain.Bill;

import java.util.Optional;

@Repository(value = "billRepository")
public interface BillRepository extends JpaRepository<Bill, Long> {

    /**
     * Find bill by id.
     * @param id must not be {@literal null}.
     * @return the entity.
     */
    @EntityGraph(attributePaths = { "billDetails" })
    Optional<Bill> findById(@NonNull Long id);
}