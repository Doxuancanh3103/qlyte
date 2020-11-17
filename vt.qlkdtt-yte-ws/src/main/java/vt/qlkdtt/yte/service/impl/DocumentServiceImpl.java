package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Document;
import vt.qlkdtt.yte.service.DocumentService;
import vt.qlkdtt.yte.service.sdi.DocumentInsertSdi;
import vt.qlkdtt.yte.service.sdi.DocumentUpdateSdi;
import vt.qlkdtt.yte.service.sdo.DocumentInsertSdo;
import vt.qlkdtt.yte.repository.DocumentRepo;
import vt.qlkdtt.yte.repository.DocumentRepoCustom;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdo.DocumentSearchSdo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    DocumentRepoCustom documentRepoCustom;

    @Autowired
    DocumentRepo documentRepo;

    @Override
    public Page<DocumentSearchSdo> findAll(Pageable pageable) throws ParseException {
        return documentRepoCustom.findAll(pageable);
    }

    @Override
    public DocumentSearchSdo findById(Long documentId) throws ParseException {
        if (DataUtil.isNullOrZero(documentId)) {
            throw new AppException("API-DC001", "documentId is required");
        }
        return documentRepoCustom.findById(documentId);
    }

    @Override
    public List<DocumentSearchSdo> searchDocument(String documentCodeName) throws ParseException {
        return documentRepoCustom.searchDocument(documentCodeName);
    }

    @Override
    public Map<String, Object> changeStatus(Long documentId, String status) {
        return documentRepoCustom.changeStatus(documentId, status);
    }

    //<editor-fold desc="Valid input insert">
    private void validInputInsert(DocumentInsertSdi sdi) {
        //code
        if (DataUtil.isStringNullOrEmpty(sdi.getCode())) {
            throw new AppException("API-DC002", "code is required");
        }

        //name
        if (DataUtil.isStringNullOrEmpty(sdi.getName())) {
            throw new AppException("API-DC003", "name is required");
        }

        //signDate
        if (!DataUtil.isStringNullOrEmpty(sdi.getSignDate()) && !DateUtil.isDate(sdi.getSignDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-DC004", "signDate format must be yyyy-mm-dd");
        }

        //effectDate
        if (!DataUtil.isStringNullOrEmpty(sdi.getEffectDate()) && !DateUtil.isDate(sdi.getEffectDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-DC005", "effectDate format must be yyyy-mm-dd");
        }

        //expireDate
        if (!DataUtil.isStringNullOrEmpty(sdi.getExpireDate()) && !DateUtil.isDate(sdi.getExpireDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-DC006", "expireDate format must be yyyy-mm-dd");
        }
    }
    //</editor-fold>

    @Override
    public DocumentInsertSdo insert(DocumentInsertSdi sdi) {
        validInputInsert(sdi);
        Document document = sdi.toDocument();

        documentRepo.save(document);

        return document.toDocumentInsertSdo();
    }

    //<editor-fold desc="Valid input update">
    private void validInputUpdate(DocumentUpdateSdi sdi) {
        //documentId
        if (DataUtil.isNullOrZero(sdi.getDocumentId())) {
            throw new AppException("API-DC001", "documentId is required");
        }
        boolean isExistDocument = documentRepoCustom.isExist(sdi.getDocumentId());
        if (!isExistDocument) {
            List<String> lstError = new ArrayList<>();
            lstError.add(DataUtil.safeToString(sdi.getDocumentId()));

            throw new AppException("API-DC007", "documentId " + sdi.getDocumentId() + " not exist" , lstError);
        }

        //code
        if (DataUtil.isStringNullOrEmpty(sdi.getCode())) {
            throw new AppException("API-DC002", "code is required");
        }

        //name
        if (DataUtil.isStringNullOrEmpty(sdi.getName())) {
            throw new AppException("API-DC003", "name is required");
        }

        //signDate
        if (!DataUtil.isStringNullOrEmpty(sdi.getSignDate()) && !DateUtil.isDate(sdi.getSignDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-DC004", "signDate format must be yyyy-mm-dd");
        }

        //effectDate
        if (!DataUtil.isStringNullOrEmpty(sdi.getEffectDate()) && !DateUtil.isDate(sdi.getEffectDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-DC005", "effectDate format must be yyyy-mm-dd");
        }

        //expireDate
        if (!DataUtil.isStringNullOrEmpty(sdi.getExpireDate()) && !DateUtil.isDate(sdi.getExpireDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-DC006", "expireDate format must be yyyy-mm-dd");
        }
    }
    //</editor-fold>

    @Override
    public DocumentInsertSdo update(DocumentUpdateSdi sdi) {
        validInputUpdate(sdi);
        Document document = null;

        Optional<Document> optionalDoc = documentRepo.findById(sdi.getDocumentId());

        if (optionalDoc.isPresent()) {
            document = optionalDoc.get();
            document = sdi.updateDocument(document);

            documentRepo.save(document);

            return document.toDocumentInsertSdo();
        }

        return null;
    }

    @Override
    public void changeStatusByProductId(String status, long productId) {
        documentRepoCustom.changeStatusByProductId(status, productId);
    }

    @Override
    public boolean isExist(Long documentId) {
        return documentRepoCustom.isExist(documentId);
    }

    @Override
    public boolean isExist(Long documentId, String documentCode) {
        return documentRepoCustom.isExist(documentId, documentCode);
    }

}
