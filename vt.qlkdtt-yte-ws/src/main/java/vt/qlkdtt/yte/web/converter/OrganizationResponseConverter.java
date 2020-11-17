package vt.qlkdtt.yte.web.converter;

import java.util.function.Function;

import vt.qlkdtt.yte.web.response.OrganizationResponse;
import vt.qlkdtt.yte.service.sdo.OrganizationSdo;

public class OrganizationResponseConverter implements Function<OrganizationSdo, OrganizationResponse>{


    @Override
    public OrganizationResponse apply(OrganizationSdo organizationSdo) {
        return OrganizationResponse.builder()
                .id(organizationSdo.getId())
                //
                .name(organizationSdo.getName())
                .logo(organizationSdo.getLogo())
                .description(organizationSdo.getDescription())
                .status(organizationSdo.getStatus())
                //
                .parentId(organizationSdo.getParentId())
                //
                .createdBy(organizationSdo.getCreatedBy())
                .createdTime(organizationSdo.getCreatedTime())
                .lastModifiedBy(organizationSdo.getLastModifiedBy())
                .lastModifiedTime(organizationSdo.getLastModifiedTime())
                .build();
    }
}
