package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.GlobalList;

@Repository
public interface GlobalListRepo extends JpaRepository<GlobalList, Long>, GlobalListRepoCustom{
}
