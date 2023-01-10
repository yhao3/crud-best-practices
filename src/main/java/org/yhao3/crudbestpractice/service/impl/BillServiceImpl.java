package org.yhao3.crudbestpractice.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yhao3.crudbestpractice.domain.Bill;
import org.yhao3.crudbestpractice.domain.BillDetail;
import org.yhao3.crudbestpractice.repository.jpa.BillDetailRepository;
import org.yhao3.crudbestpractice.repository.jpa.BillRepository;
import org.yhao3.crudbestpractice.repository.search.BillSearchRepository;
import org.yhao3.crudbestpractice.service.BillService;
import org.yhao3.crudbestpractice.service.dto.*;
import org.yhao3.crudbestpractice.service.mapper.BillMapper;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;

    private final BillDetailRepository billDetailRepository;

    private final BillSearchRepository billSearchRepository;

    public BillServiceImpl(BillRepository billRepository, BillDetailRepository billDetailRepository, BillSearchRepository billSearchRepository) {
        this.billRepository = billRepository;
        this.billDetailRepository = billDetailRepository;
        this.billSearchRepository = billSearchRepository;
    }

    @Override
    public Bill create(BillCreateDTO billCreateDTO) {
        Bill bill = new Bill();
        bill.setBillNo(billCreateDTO.getBillNo());
        bill.setBillDate(billCreateDTO.getBillDate());
        bill = billRepository.save(bill);
        if (billCreateDTO.getBillDetails() != null) {
            List<BillDetail> billDetails = new ArrayList<>();
            for (BillDetailCreateDTO billDetailCreateDTO : billCreateDTO.getBillDetails()) {
                BillDetail billDetail = new BillDetail();
                billDetail.setCreditAmt(billDetailCreateDTO.getCreditAmt());
                billDetail.setDebitAmt(billDetailCreateDTO.getDebitAmt());
                billDetail.setMemo(billDetailCreateDTO.getMemo());
                billDetail.setBill(bill);
                billDetails.add(billDetail);
            }
            billDetails = billDetailRepository.saveAll(billDetails);
            bill.setBillDetails(new HashSet<>(billDetails));
        }
        Double creditAmtSum = 0D;
        Double debitAmtSum = 0D;
        Long billDetailSize = 0L;
        for (BillDetail billDetail : bill.getBillDetails()) {
            creditAmtSum += billDetail.getCreditAmt();
            debitAmtSum += billDetail.getDebitAmt();
            billDetailSize++;
        }

        org.yhao3.crudbestpractice.document.Bill billDocument = BillMapper.toDocument(bill);
        billSearchRepository.index(billDocument);
        return bill;
    }

    @Override
    public Optional<Bill> partialUpdate(Long id, BillPartialUpdateDTO billPartialUpdateDTO) throws NoSuchElementException {
        return billRepository
                .findById(id)
                .map(
                        existingBill -> {
                            if (billPartialUpdateDTO.getBillNo() != null) {
                                existingBill.setBillNo(billPartialUpdateDTO.getBillNo());
                            }
                            if (billPartialUpdateDTO.getBillDate() != null) {
                                existingBill.setBillDate(billPartialUpdateDTO.getBillDate());
                            }
                            if (billPartialUpdateDTO.getBillDetails() != null) {
                                // get existing billDetails.
                                Set<BillDetail> existingBillDetails = existingBill.getBillDetails();
                                // create a new Set for new billDetails.
                                Set<BillDetail> newBillDetails = new HashSet<>();
                                for (BillDetailPartialUpdateDTO billDetailPartialUpdateDTO : billPartialUpdateDTO.getBillDetails()) {
                                    if (billDetailPartialUpdateDTO.getId() == null) {
                                        // 1. "create" new BillDetail
                                        BillDetail billDetail = new BillDetail();
                                        billDetail.setCreditAmt(billDetailPartialUpdateDTO.getCreditAmt());
                                        billDetail.setDebitAmt(billDetailPartialUpdateDTO.getDebitAmt());
                                        billDetail.setMemo(billDetailPartialUpdateDTO.getMemo());
                                        billDetail.setBill(existingBill);
                                        billDetail = billDetailRepository.save(billDetail);
                                        newBillDetails.add(billDetail);
                                    } else {
                                        // 2. "update" existing BillDetail (partial update or update)
                                        BillDetail existingBillDetail = billDetailRepository.findById(billDetailPartialUpdateDTO.getId()).orElseThrow();
                                        existingBillDetails.remove(existingBillDetail);
                                        if (billDetailPartialUpdateDTO.getCreditAmt() != null) {
                                            existingBillDetail.setCreditAmt(billDetailPartialUpdateDTO.getCreditAmt());
                                        }
                                        if (billDetailPartialUpdateDTO.getDebitAmt() != null) {
                                            existingBillDetail.setDebitAmt(billDetailPartialUpdateDTO.getDebitAmt());
                                        }
                                        if (billDetailPartialUpdateDTO.getMemo() != null) {
                                            existingBillDetail.setMemo(billDetailPartialUpdateDTO.getMemo());
                                        }
                                        existingBillDetail = billDetailRepository.save(existingBillDetail);
                                        newBillDetails.add(existingBillDetail);
                                    }
                                }
                                // 3. "remove" unnecessary existing billDetails
                                billDetailRepository.deleteAll(existingBillDetails);

                                existingBill.setBillDetails(newBillDetails);
                            }
                            return existingBill;
                        }
                )
                .map(billRepository::save)
                .map(savedBill -> {
                    org.yhao3.crudbestpractice.document.Bill billDocument = BillMapper.toDocument(savedBill);
                    billSearchRepository.save(billDocument);

                    return savedBill;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public SearchPage<org.yhao3.crudbestpractice.document.Bill> search(String keyword, Double amtMin, Double amtMax, LocalDate dateMin, LocalDate dateMax, Pageable pageable) {
        Criteria criteria = new Criteria();
        if (keyword != null) {
            criteria.and(new Criteria("billNo").contains(keyword));
        }
        if (amtMin != null) {
            criteria.and(new Criteria("creditAmtSum").greaterThanEqual(amtMin)
                    .subCriteria(new Criteria().or("debitAmtSum").greaterThanEqual(amtMin)));
        }
        if (amtMax != null) {
            criteria.and(new Criteria("creditAmtSum").lessThanEqual(amtMax)
                    .subCriteria(new Criteria().or("debitAmtSum").lessThanEqual(amtMax)));
        }
        if (dateMin != null) {
            criteria.and(new Criteria("billDate").greaterThanEqual(dateMin));
        }
        if (dateMax != null) {
            criteria.and(new Criteria("billDate").lessThanEqual(dateMax));
        }
        return billSearchRepository.search(criteria, pageable);
    }
}
