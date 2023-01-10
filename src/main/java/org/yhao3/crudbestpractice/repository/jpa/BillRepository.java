package org.yhao3.crudbestpractice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yhao3.crudbestpractice.domain.Bill;

@Repository(value = "billRepository")
public interface BillRepository extends JpaRepository<Bill, Long> {
}