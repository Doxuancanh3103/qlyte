package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.repository.*;

import javax.persistence.EntityManager;

@Repository
public class DoConnectRepoImpl implements DoConnectRepo {
    @Autowired
    EntityManager em;
}
