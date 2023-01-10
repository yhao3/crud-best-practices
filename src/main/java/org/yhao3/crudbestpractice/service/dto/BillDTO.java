package org.yhao3.crudbestpractice.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class BillDTO {
    private Long id;

    private LocalDate billDate;

    private String billNo;

    private Double creditAmtSum;

    private Double debitAmtSum;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private List<BillDetailDTO> billDetails;



}
