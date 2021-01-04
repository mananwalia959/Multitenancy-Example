package com.example.multitenancy.tenancy;

import java.util.List;
import java.util.UUID;

public interface TenantInformationProvider {

	TenantProperties getTenantPropertyById(UUID tenantId);

	TenantProperties getTenantPropertyByDomain(String domain);

	List<TenantProperties> getAllTenantProperties();

}