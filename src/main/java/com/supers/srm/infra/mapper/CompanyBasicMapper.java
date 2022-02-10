package com.supers.srm.infra.mapper;

import com.supers.srm.domain.entity.CompanyBasic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyBasicMapper {
    void insertCompanyBasic(@Param("companyName") String companyName, @Param("fileUrl") String fileUrl);

    List<CompanyBasic> selectCompanyBasic();

    void updateSplmCompanyBasic(@Param("licenceUrl") String licenceUrl, @Param("companyName") String companyName);

    void updateSplmSupplierBasic(@Param("licenceUrl") String licenceUrl, @Param("companyName") String companyName);
}
