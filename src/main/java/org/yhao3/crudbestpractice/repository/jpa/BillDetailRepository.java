package org.yhao3.crudbestpractice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yhao3.crudbestpractice.domain.BillDetail;

@Repository(value = "billDetailRepository")
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
}