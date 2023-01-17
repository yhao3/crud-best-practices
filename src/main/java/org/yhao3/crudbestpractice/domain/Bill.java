package org.yhao3.crudbestpractice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * The bill entity.
 * @author haoyang
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bill")
public class Bill implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_seq")
    @SequenceGenerator(name = "bill_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private LocalDate billDate;

    @Column
    private String billNo;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bill")
    @JsonIgnoreProperties(value = "bill", allowSetters = true)
    private Set<BillDetail> billDetails = new java.util.LinkedHashSet<>();

    public Bill() {
    }

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

    public Set<BillDetail> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(Set<BillDetail> billDetails) {
        this.billDetails = billDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bill bill = (Bill) o;
        return Objects.equals(id, bill.id) && Objects.equals(billDate, bill.billDate) && Objects.equals(billNo, bill.billNo) && Objects.equals(createdDate, bill.createdDate) && Objects.equals(lastModifiedDate, bill.lastModifiedDate) && Objects.equals(billDetails, bill.billDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, billDate, billNo, createdDate, lastModifiedDate, billDetails);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", billDate=" + billDate +
                ", billNo='" + billNo + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", billDetails=" + billDetails +
                '}';
    }
}