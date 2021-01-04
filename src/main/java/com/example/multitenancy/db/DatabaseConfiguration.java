package com.example.multitenancy.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.multitenancy.tenancy.TenantInformationProvider;

@Configuration
public class DatabaseConfiguration {

	@Bean
	public MultiTenantConnectionProvider getMultiTenantConnectionProvider(
			TenantInformationProvider tenantInformationProvider) {
		return new MultiTenantConnectionProvider(tenantInformationProvider.getAllTenantProperties());
	}

}
