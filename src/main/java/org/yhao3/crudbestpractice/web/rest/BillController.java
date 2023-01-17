package org.yhao3.crudbestpractice.web.rest;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yhao3.crudbestpractice.domain.Bill;
import org.yhao3.crudbestpractice.repository.jpa.BillRepository;
import org.yhao3.crudbestpractice.service.BillService;
import org.yhao3.crudbestpractice.web.rest.error.NotFoundErrorResponseException;
import org.yhao3.crudbestpractice.web.rest.util.PaginationUtil;
import org.yhao3.crudbestpractice.web.rest.vo.BillCreateVO;
import org.yhao3.crudbestpractice.web.rest.vo.BillPartialUpdateVO;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BillController {

    private static final String ENTITY_NAME = "bill";

    private final BillRepository billRepository;
    private final BillService billService;

    public BillController(BillRepository billRepository, BillService billService) {
        this.billRepository = billRepository;
        this.billService = billService;
    }

    @PostMapping(value = "/bills")
    public ResponseEntity<Bill> create(@Valid @RequestBody BillCreateVO billCreateVO) throws URISyntaxException {
        Bill bill = billService.create(billCreateVO);
        return ResponseEntity.created(new URI("/api/bills/" + bill.getId())).body(bill);
    }

    @PatchMapping(value = "/bills/{id}", consumes = "application/merge-patch+json")
    public Optional<Bill> partialUpdate(@PathVariable(value = "id") final Long id,
                                        @RequestBody BillPartialUpdateVO billPartialUpdateVO) {

        if (!billRepository.existsById(id)) {
            throw new NotFoundErrorResponseException(ENTITY_NAME);
        }
        return billService.partialUpdate(id, billPartialUpdateVO);
    }

    @GetMapping(value = "/bills/{id}")
    public ResponseEntity<Bill> getBill(@PathVariable Long id) {
        Bill existingBill = billRepository.findById(id).orElseThrow(() -> new NotFoundErrorResponseException(ENTITY_NAME));
        return ResponseEntity.ok().body(existingBill);
    }

    @GetMapping(value = "/_search/bills")
    public ResponseEntity<List<SearchHit<org.yhao3.crudbestpractice.document.Bill>>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double amtMin,
            @RequestParam(required = false) Double amtMax,
            @RequestParam(required = false) LocalDate dateMin,
            @RequestParam(required = false) LocalDate dateMax,
            Pageable pageable
    ) {
        SearchPage<org.yhao3.crudbestpractice.document.Bill> searchPage = billService.search(keyword, amtMin, amtMax, dateMin, dateMax, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), searchPage);
        return ResponseEntity.ok().headers(headers).body(searchPage.getSearchHits().getSearchHits());
    }

}
