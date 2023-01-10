package org.yhao3.crudbestpractice.service.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * @author yhao3
 */
public class BillPartialUpdateDTO {
    private LocalDate billDate;

    private String billNo;

    private List<BillDetailPartialUpdateDTO> billDetails;

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public List<BillDetailPartialUpdateDTO> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetailPartialUpdateDTO> billDetails) {
        this.billDetails = billDetails;
    }

//    @Override
//    public String toString() {
//        return "BillPartialUpdateDTO{" +
//                "billDate=" + billDate +
//                ", billNo='" + billNo + '\'' +
//                ", billDetailPartialUpdateDTOList=" + billDetails +
//                '}';
//    }
}
