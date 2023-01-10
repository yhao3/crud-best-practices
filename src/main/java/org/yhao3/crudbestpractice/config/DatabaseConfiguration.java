package org.yhao3.crudbestpractice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The database configuration.
 * @author yhao3
 */
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "taipeiDateTimeProvider")
@EnableTransactionManagement
@EnableJpaRepositories({ "org.yhao3.crudbestpractice.repository.jpa" })
@EnableElasticsearchRepositories("org.yhao3.crudbestpractice.repository.search")
public class DatabaseConfiguration {
}
