package kopo.poly.repository.custom;

import kopo.poly.repository.entity.PreferEntity;

import java.util.List;

public interface PreferRepositoryCustom {

    List<PreferEntity> findByRegId(String regId) throws Exception;
}
