package com.starter.config;

import java.time.Duration;

import org.ehcache.config.CacheConfiguration;
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

@Configuration
@EnableCaching
public class CacheConfig {

	private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

	private int timeToLiveSeconds = 3600;
	private int maxEntries = 1000;

	public CacheConfig() {

		org.ehcache.expiry.ExpiryPolicy<Object, Object> expiryPolicy = ExpiryPolicyBuilder
				.timeToLiveExpiration(Duration.ofSeconds(this.timeToLiveSeconds));

		CacheConfiguration<Object, Object> ehcacheConfig = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(this.maxEntries))
				.withExpiry(expiryPolicy).build();

		jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(ehcacheConfig);

	}

	@Bean
	HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
		return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
	}

	@Bean
	JCacheManagerCustomizer cacheManagerCustomizer() {
		return cm -> {
			this.createCache(cm, com.starter.domain.User.class.getName());
			this.createCache(cm, com.starter.domain.Authority.class.getName());
			this.createCache(cm, com.starter.domain.User.class.getName() + ".authorities");
			this.createCache(cm, com.starter.domain.Product.class.getName());
		};
	}

	private void createCache(javax.cache.CacheManager cm, String cacheName) {
		javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
		if (cache != null) {
			cache.clear();
		} else {
			cm.createCache(cacheName, jcacheConfiguration);
		}
	}

}
