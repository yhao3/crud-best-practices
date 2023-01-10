package org.yhao3.crudbestpractice.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * The Bill DTO for creating.
 * @author yhao3
 */
public class BillCreateDTO {

    @NotNull
    private LocalDate billDate;

    @NotNull
    private String billNo;

    @Valid
    private List<BillDetailCreateDTO> billDetails;

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

    public List<BillDetailCreateDTO> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetailCreateDTO> billDetails) {
        this.billDetails = billDetails;
    }

    @Override
    public String toString() {
        return "BillCreateDTO{" +
                "billDate=" + billDate +
                ", billNo='" + billNo + '\'' +
                ", billDetailCreateDTOList=" + billDetails +
                '}';
    }
}
