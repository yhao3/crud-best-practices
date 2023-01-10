package org.yhao3.crudbestpractice.service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

public class BillDetailDTO {

    private Long id;

    private Double debitAmt;

    private Double creditAmt;

    private String memo;

    private Instant createdDate;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDebitAmt() {
        return debitAmt;
    }

    public void setDebitAmt(Double debitAmt) {
        this.debitAmt = debitAmt;
    }

    public Double getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(Double creditAmt) {
        this.creditAmt = creditAmt;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

//    @Override
//    public String toString() {
//        return "BillDetailDTO{" +
//                "id=" + id +
//                ", debitAmt=" + debitAmt +
//                ", creditAmt=" + creditAmt +
//                ", memo='" + memo + '\'' +
//                ", createdDate=" + createdDate +
//                ", lastModifiedDate=" + lastModifiedDate +
//                '}';
//    }
}
