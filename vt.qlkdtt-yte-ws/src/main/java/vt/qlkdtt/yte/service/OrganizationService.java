package vt.qlkdtt.yte.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vt.qlkdtt.yte.service.sdi.OrganizationCreateSdi;
import vt.qlkdtt.yte.service.sdi.OrganizationUpdateSdi;
import vt.qlkdtt.yte.service.sdo.OrganizationSdo;

public interface OrganizationService {

    // ### Organization
    @Cacheable(value = "organizations")
    Optional<OrganizationSdo> getOrganizationById(long organizationId);

    @Cacheable(value = "organizations")
    Page<OrganizationSdo> getOrganizations(String keyword, String status, Pageable pageable);

    //
    @CacheEvict(value = "organizations", allEntries = true)
    OrganizationSdo createOrganization(OrganizationCreateSdi organizationCreateSdi);

    @CacheEvict(value = "organizations", allEntries = true)
    OrganizationSdo updateOrganization(OrganizationUpdateSdi organizationUpdateSdi);

    @CacheEvict(value = "organizations", allEntries = true)
    void deleteOrganization(long organizationId);

}
