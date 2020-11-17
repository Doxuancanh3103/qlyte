package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.domain.DocumentDetailTemp;

import java.util.List;

public interface DocumentDetailTempRepoCustom {
    List<DocumentDetailTemp> getByDocumentId(Long documentId);
}
