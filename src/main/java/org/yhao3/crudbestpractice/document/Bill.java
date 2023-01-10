package org.yhao3.crudbestpractice.document;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDate;

/**
 * @author kyrieeeee2@gmail.com
 */
@Document(indexName="bill")
public class Bill {

    @Id
    private Long id;

    @Field(type=FieldType.Date, format= DateFormat.basic_date)
    private LocalDate billDate;

    @Field(type=FieldType.Auto)
    private String billNo;

    @Field(type=FieldType.Auto)
    private Double creditAmtSum;

    @Field(type=FieldType.Auto)
    private Double debitAmtSum;

    @Field(type=FieldType.Auto)
    private Long billDetailSize;

    @Field(type= FieldType.Date, pattern="dd.MM.uuuu")
    private Instant createdDate;

    @Field(type= FieldType.Date, pattern="dd.MM.uuuu")
    private Instant lastModifiedDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getCreditAmtSum() {
        return creditAmtSum;
    }

    public void setCreditAmtSum(Double creditAmtSum) {
        this.creditAmtSum = creditAmtSum;
    }

    public Double getDebitAmtSum() {
        return debitAmtSum;
    }

    public void setDebitAmtSum(Double debitAmtSum) {
        this.debitAmtSum = debitAmtSum;
    }

    public Long getBillDetailSize() {
        return billDetailSize;
    }

    public void setBillDetailSize(Long billDetailSize) {
        this.billDetailSize = billDetailSize;
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

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", billDate=" + billDate +
                ", billNo='" + billNo + '\'' +
                ", creditAmtSum=" + creditAmtSum +
                ", debitAmtSum=" + debitAmtSum +
                ", billDetailSize=" + billDetailSize +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
