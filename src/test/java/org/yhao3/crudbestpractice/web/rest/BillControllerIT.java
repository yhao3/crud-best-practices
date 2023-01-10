package org.yhao3.crudbestpractice.web.rest;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.yhao3.crudbestpractice.domain.Bill;
import org.yhao3.crudbestpractice.domain.BillDetail;
import org.yhao3.crudbestpractice.repository.jpa.BillDetailRepository;
import org.yhao3.crudbestpractice.repository.jpa.BillRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class BillControllerIT {

    private static final String ENTITY_API_URL = "/api/bills";

    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final String DEFAULT_BILL_NO = "AA";

    private static final LocalDate DEFAULT_BILL_DATE = LocalDate.now();
    private static final String DEFAULT_MEMO = "AA";

    private static final Random RANDOM = new Random();
    private static final AtomicLong COUNT = new AtomicLong(RANDOM.nextInt() + (2L * Integer.MAX_VALUE));
    private static final int DEFAULT_BILL_DETAIL_SIZE = 6;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMockMvc;

    private Bill bill;

    public static Bill createBillEntity(EntityManager em) {
        Set<BillDetail> billDetails = new HashSet<>();
        for (int i = 1; i <= DEFAULT_BILL_DETAIL_SIZE; i++) {
            BillDetail billDetail = new BillDetail();
            if (i % 2 == 0) {
                billDetail.setDebitAmt(100D * 2 * i);
                billDetail.setCreditAmt(0D);
            } else {
                billDetail.setDebitAmt(0D);
                billDetail.setCreditAmt(100D * 2 * (i - 1));
            }
            billDetail.setMemo(DEFAULT_MEMO);
            billDetails.add(billDetail);
        }
        Bill bill = new Bill();
        bill.setBillNo(DEFAULT_BILL_NO);
        bill.setBillDate(DEFAULT_BILL_DATE);
        bill.setBillDetails(billDetails);
        return bill;
    }

    @BeforeEach
    public void initTest() {
        bill = createBillEntity(em);
    }

    @Test
    @Transactional
    void create_withBillNoIsRequired_failure() throws Exception {
        bill.setBillNo(null);
        restMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bill))).andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    void create_withCreditAmtIsRequired_failure() throws Exception {
        Set<BillDetail> billDetails = bill.getBillDetails();
        billDetails.forEach(
                billDetail -> billDetail.setCreditAmt(null)
        );
        bill.setBillDetails(billDetails);
        restMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bill))).andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    void create_withCreditAmtIsMustEqualsOrGreaterZero_failure() throws Exception {
        Set<BillDetail> billDetails = bill.getBillDetails();
        billDetails.forEach(
                billDetail -> billDetail.setCreditAmt(-1D)
        );
        bill.setBillDetails(billDetails);
        restMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bill))).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void create() throws Exception {

        int databaseSizeBeforeCreate = billRepository.findAll().size();

        restMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bill))).andExpect(status().isCreated());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = billList.get(databaseSizeBeforeCreate);
        assertEquals(DEFAULT_BILL_NO, testBill.getBillNo());
        assertEquals(DEFAULT_BILL_DATE, testBill.getBillDate());
        assertNotNull(testBill.getCreatedDate());
        assertNotNull(testBill.getLastModifiedDate());
        assertEquals(DEFAULT_BILL_DETAIL_SIZE, testBill.getBillDetails().size());
        testBill.getBillDetails().forEach(
                billDetail -> {
                    assertNotNull(billDetail.getId());
                    assertEquals(testBill.getId(), billDetail.getBill().getId());
                }
        );
    }

    @Test
    @Transactional
    void partialUpdate_butIdNotExists_throw400NotFound() throws Exception {

        int databaseSizeBeforeUpdate = billRepository.findAll().size();
        bill.setId(COUNT.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMockMvc.perform(patch(ENTITY_API_URL_ID, COUNT.incrementAndGet()).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bill))).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void getById() throws Exception {

        // Initialize database
        billRepository.saveAndFlush(bill);

        restMockMvc.perform(get(ENTITY_API_URL_ID, bill.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(bill.getId().intValue()));
    }
}