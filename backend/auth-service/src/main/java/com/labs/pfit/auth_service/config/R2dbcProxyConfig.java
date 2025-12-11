package com.labs.pfit.auth_service.config;

import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.r2dbc.proxy.ProxyConnectionFactory;
import io.r2dbc.proxy.core.QueryExecutionInfo;
import io.r2dbc.proxy.listener.ProxyExecutionListener;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class R2dbcProxyConfig {
	@Bean
	@Primary
	public ConnectionFactory connectionFactory(@Value("${spring.r2dbc.url}") String r2dbcUrl,
	                                           @Value("${spring.r2dbc.username:}") String username,
	                                           @Value("${spring.r2dbc.password:}") String password) {

		ConnectionFactoryOptions base = ConnectionFactoryOptions.parse(r2dbcUrl);
		ConnectionFactoryOptions opts = base;
		
		if (username != null && !username.isEmpty()) {
			opts = base.mutate()
				       .option(USER, username)
				       .option(PASSWORD, password)
				       .build();
		}
		
		ConnectionFactory original = ConnectionFactories.get(opts);

		// Build a proxy with an SLF4J listener (logs SQL + binds)
		return ProxyConnectionFactory.builder(original)
			       .listener(new ProxyExecutionListener() {
				       @Override
				       public void beforeQuery(QueryExecutionInfo execInfo) {
						   execInfo.getQueries().forEach(query -> {
							   log.info("Query: {}", query.getQuery());
							   log.info("Bindings: {}", query.getBindingsList());
						   });
				       }
				       
				       @Override
				       public void afterQuery(QueryExecutionInfo execInfo) {
					       execInfo.getQueries().forEach(query -> {
						       log.debug("Total Execution time: {} ms", execInfo.getExecuteDuration().toMillis());
					       });
				       }
				       
				       @Override
				       public void eachQueryResult(QueryExecutionInfo execInfo) {
						   log.info("Result {}", execInfo.getCurrentMappedResult());
				       }

			       })
			       .build();
	}
}
