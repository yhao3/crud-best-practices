package org.yhao3.crudbestpractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yhao3.crudbestpractice.domain.Bill;
import org.yhao3.crudbestpractice.domain.BillDetail;
import org.yhao3.crudbestpractice.repository.jpa.BillRepository;
import org.yhao3.crudbestpractice.repository.search.BillSearchRepository;
import org.yhao3.crudbestpractice.service.mapper.BillMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yhao3
 */
@SpringBootApplication
public class CrudBestPracticeApplication implements CommandLineRunner {

	@Autowired
	private BillSearchRepository billSearchRepository;
	@Autowired
	private BillRepository billRepository;

	public static void main(String[] args) {
		SpringApplication.run(CrudBestPracticeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		billSearchRepository.deleteAll();
		for (int i = 0; i < 50; i++) {
			LocalDate billDate = LocalDate.of(2023, i % 12 + 1, i % 31 + 1);
			Set<BillDetail> billDetailSet = new HashSet<>();
			Bill bill = new Bill();
			String yyyyMmDd = billDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			String billNoPrefix = i < 25 ? "AA" : "BB";
			bill.setBillNo(billNoPrefix + yyyyMmDd);
			bill.setBillDate(billDate);
			for (int j = 0; j < 4; j++) {
				BillDetail billDetail = new BillDetail();
				if (j % 2 == 0) {
					billDetail.setDebitAmt(100D * i);
					billDetail.setCreditAmt(0D);
					billDetail.setMemo("This is debit");
				} else {
					billDetail.setDebitAmt(0D);
					billDetail.setCreditAmt(100D * i);
					billDetail.setMemo("This is credit");
				}
				billDetail.setBill(bill);
				billDetailSet.add(billDetail);
			}
			bill.setBillDetails(billDetailSet);
			billRepository.save(bill);
			org.yhao3.crudbestpractice.document.Bill document = BillMapper.toDocument(bill);
			billSearchRepository.save(document);
		}
	}
}
