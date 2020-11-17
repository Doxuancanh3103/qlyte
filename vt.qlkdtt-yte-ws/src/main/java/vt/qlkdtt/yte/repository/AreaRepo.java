package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vt.qlkdtt.yte.domain.Area;

public interface AreaRepo extends JpaRepository<Area, Long>, AreaRepoCustom {
}
