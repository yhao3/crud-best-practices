package org.yhao3.crudbestpractice.repository.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.lang.NonNull;
import org.yhao3.crudbestpractice.document.Bill;
import org.yhao3.crudbestpractice.repository.jpa.BillRepository;

/**
 * @author yhao3
 */
public interface BillSearchRepository extends ElasticsearchRepository<Bill, Long>, BillSearchRepositoryInternal {}

interface BillSearchRepositoryInternal {

    /**
     * Elasticsearch search page for {@link CriteriaQuery}.
     * @param criteria the {@link Criteria} instance.
     * @param pageable the pagination information.
     * @return Page definition for repositories that need to return a paged SearchHits.
     */
    SearchPage<Bill> search(Criteria criteria, Pageable pageable);

    /**
     * Elasticsearch search page for {@link StringQuery}.
     * @param dsl the StringQuery source.
     * @param pageable the pagination information.
     * @return Page definition for repositories that need to return a paged SearchHits.
     */
    SearchPage<Bill> search(String dsl, Pageable pageable);

    /**
     * Elasticsearch search page for {@link NativeQuery}.
     * @param nativeQuery the {@link NativeQuery} instance.
     * @return Page definition for repositories that need to return a paged SearchHits.
     */
    SearchPage<Bill> search(NativeQuery nativeQuery);

    /**
     * Elasticsearch search page.
     * @param query the {@link Query} implementation instance.
     * @return Page definition for repositories that need to return a paged SearchHits.
     */
    SearchPage<Bill> search(Query query);

    /**
     * Saves an entity to the index specified in the entity's Document annotation
     * @param entity the entity to save, must not be null
     */
    void index(Bill entity);
}

class BillSearchRepositoryInternalImpl implements BillSearchRepositoryInternal {

    private final static Logger log = LoggerFactory.getLogger(BillSearchRepositoryInternalImpl.class);

    private final ElasticsearchOperations elasticsearchOperations;
    private final BillRepository repository;

    BillSearchRepositoryInternalImpl(ElasticsearchOperations elasticsearchOperations, BillRepository repository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.repository = repository;
        NativeQueryBuilder builder = NativeQuery.builder();
    }

    @Override
    public SearchPage<Bill> search(Criteria criteria, Pageable pageable) {
        // Correct:
        Query query = new CriteriaQuery(criteria).setPageable(pageable);
        return search(query);

        /*
         Incorrect: java.lang.ClassCastException: class org.springframework.data.elasticsearch.core.query.CriteriaQuery cannot be cast to class org.springframework.data.elasticsearch.client.elc.NativeQuery (org.springframework.data.elasticsearch.core.query.CriteriaQuery and org.springframework.data.elasticsearch.client.elc.NativeQuery are in unnamed module of loader 'app')
         Query query = new CriteriaQuery(criteria);
         return search(query.setPageable(pageable));
        */
    }

    @Override
    public SearchPage<Bill> search(String dsl, Pageable pageable) {
        Query query = new StringQuery(dsl).setPageable(pageable);
        return search(query);
    }

    @Override
    public SearchPage<Bill> search(NativeQuery nativeQuery) {
        return search((Query) nativeQuery);
    }

//    @Override
//    public SearchPage<Bill> search(String keyword, Double amtMin, Double amtMax, LocalDate dateMin, LocalDate dateMax, Pageable pageable) {
//        String dsl = """
//            {
//                "bool": {
//                        "should":[
//                            {"match":{"billNo":%s}},
//                            {"range":{"sex":"男"}},
//                            {"range":{  "creditAmtSum":{"gte":%s,"lte":%s}  }}
//                        ]
//                    }
//                }
//            }
//            """;
//        Query query = new StringQuery(dsl);
//
//        Criteria criteria = new Criteria();
//        if (keyword != null) {
//            criteria.and("billNo").contains(keyword);
//        }
//        if (amtMin != null) {
//            criteria.and("creditAmtSum").greaterThanEqual(amtMin)
//                    .subCriteria(new Criteria().or("debitAmtSum").greaterThanEqual(amtMin));
//        }
//        if (amtMax != null) {
//            criteria.and("creditAmtSum").lessThanEqual(amtMax)
//                    .subCriteria(new Criteria().or("debitAmtSum").lessThanEqual(amtMax));
//        }
//        if (dateMin != null) {
//            criteria.and("billDate").greaterThanEqual(dateMin);
//        }
//        if (dateMax != null) {
//            criteria.and("billDate").lessThanEqual(dateMax);
//        }
//        Query query = new CriteriaQuery(criteria);
//        return search(query.setPageable(pageable));
//    }

    @Override
    public SearchPage<Bill> search(Query query) {
        System.out.println(query.toString());
        SearchHits<Bill> searchHits = elasticsearchOperations.search(query, Bill.class);
        return SearchHitSupport.searchPageFor(searchHits, query.getPageable());
    }


    @Override
    public void index(@NonNull Bill document) {
        repository.findById(document.getId()).orElseThrow();
        elasticsearchOperations.save(document);
    }
}