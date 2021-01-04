package com.example.multitenancy.tenancy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.multitenancy.db.DatabaseProperties;

@Component
public class HardCodedTenantInformationProviderImpl implements TenantInformationProvider {
	
	/**
	 * in a real application this would come from an external file or config
	 */
	private List<TenantProperties> allTenantsProperties = getHardCodedTenantProperties();
	
	@Override
	public TenantProperties getTenantPropertyById(UUID tenantId) {
		return allTenantsProperties.stream().filter(tenant-> tenant.getTenantId().equals(tenantId)).findAny()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"));
	}
	@Override
	public TenantProperties getTenantPropertyByDomain(String domain) {
		return allTenantsProperties.stream().filter(tenant-> tenant.getDomain().equals(domain)).findAny()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"));
	}
	@Override
	public List<TenantProperties> getAllTenantProperties() {
		return this.allTenantsProperties;
	}

	private List<TenantProperties> getHardCodedTenantProperties() {
		List<TenantProperties> allTenants = new ArrayList<>();
		
		var acmeTenant = new TenantProperties();
		acmeTenant.setDomain("acme");
;		acmeTenant.setTenantId(UUID.fromString("2affd9bf-868c-4f03-973b-ee5ec6316b11"));
		acmeTenant.setDatabaseProperties(new DatabaseProperties("jdbc:sqlserver://acme:1433;databaseName=master", "sa", "catuser@123"));
		
		var lorealTenant = new TenantProperties();
		lorealTenant.setDomain("loreal");
		lorealTenant.setTenantId(UUID.fromString("505ffde4-641c-41d5-9bfc-46fa5743fd72"));
		lorealTenant.setDatabaseProperties(new DatabaseProperties("jdbc:sqlserver://loreal:1433;databaseName=master", "sa", "catuser@123"));
		
		allTenants.add(acmeTenant);
		allTenants.add(lorealTenant);
		return allTenants;
	}

}
