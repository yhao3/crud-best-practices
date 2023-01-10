package org.yhao3.crudbestpractice.service.mapper;

import org.yhao3.crudbestpractice.document.Bill;
import org.yhao3.crudbestpractice.domain.BillDetail;

public class BillMapper {
    public static Bill toDocument(org.yhao3.crudbestpractice.domain.Bill bill) {
        Double creditAmtSum = 0D;
        Double debitAmtSum = 0D;
        Long billDetailSize = 0L;
        for (BillDetail billDetail : bill.getBillDetails()) {
            creditAmtSum += billDetail.getCreditAmt();
            debitAmtSum += billDetail.getDebitAmt();
            billDetailSize++;
        }
        Bill billDocument = new Bill();
        billDocument.setId(bill.getId());
        billDocument.setBillNo(bill.getBillNo());
        billDocument.setBillDate(bill.getBillDate());
        billDocument.setCreditAmtSum(creditAmtSum);
        billDocument.setDebitAmtSum(debitAmtSum);
        billDocument.setBillDetailSize(billDetailSize);
        billDocument.setCreatedDate(bill.getCreatedDate());
        billDocument.setLastModifiedDate(bill.getLastModifiedDate());
        return billDocument;
    }
}
