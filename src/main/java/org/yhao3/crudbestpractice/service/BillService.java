package org.yhao3.crudbestpractice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;
import org.yhao3.crudbestpractice.domain.Bill;
import org.yhao3.crudbestpractice.service.dto.BillCreateDTO;
import org.yhao3.crudbestpractice.service.dto.BillPartialUpdateDTO;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author yhao3
 */
@Service
public interface BillService {

    Bill create(BillCreateDTO billCreateDTO);

    Optional<Bill> partialUpdate(Long id, BillPartialUpdateDTO billPartialUpdateDTO);

    SearchPage<org.yhao3.crudbestpractice.document.Bill> search(String query, Double amtMin, Double amtMax, LocalDate dateMin, LocalDate dateMax, Pageable pageable);
//    SearchPage<org.yhao3.crudbestpractice.document.Bill> search(String query, Double amtMin, Double amtMax, LocalDate dateMin, LocalDate dateMax, Pageable pageable);
}
