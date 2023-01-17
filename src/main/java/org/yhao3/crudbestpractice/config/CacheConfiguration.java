package org.yhao3.crudbestpractice.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import java.time.Duration;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jCacheConfiguration;

    /**
     * Empty Constructor.
     */
    public CacheConfiguration() {

        // Create an "Ehcache" CacheConfiguration.
        // You can use a builder as shown here, or alternatively use an XML configuration
        org.ehcache.config.CacheConfiguration<Object, Object> ehCacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(100))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(5)))
                .build();

        // Get a "JCache" configuration by wrapping the "Ehcache" configuration, and assign to `jcacheConfiguration` field.
        this.jCacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(ehCacheConfiguration);
    }


    /**
     * Create a {@link HibernatePropertiesCustomizer} implementation bean to customize the Hibernate properties.
     * @param cacheManager the {@link CacheManager} instance.
     * @return the {@link HibernatePropertiesCustomizer} implementation bean.
     */
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(CacheManager cacheManager) {
        return new HibernatePropertiesCustomizer() {
            @Override
            public void customize(Map<String, Object> hibernateProperties) {
                hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
            }
        };
    }


    /**
     * Create a {@link JCacheManagerCustomizer} implementation bean to customize the cache manager.
     * @return the {@link JCacheManagerCustomizer} implementation bean.
     */
    @Bean
    public JCacheManagerCustomizer jCacheManagerCustomizer() {

        return new JCacheManagerCustomizer() {
            @Override
            public void customize(CacheManager cacheManager) {
                // create caches.
                createCache(cacheManager, org.yhao3.crudbestpractice.domain.Bill.class.getName());
                createCache(cacheManager, org.yhao3.crudbestpractice.domain.Bill.class.getName() + ".billDetails");
                createCache(cacheManager, org.yhao3.crudbestpractice.domain.BillDetail.class.getName());
            }
        };
    }

    private void createCache(CacheManager cacheManager, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cacheManager.createCache(cacheName, jCacheConfiguration);
        }
    }
}
