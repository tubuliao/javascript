
package com.isoftstone.tyw.repository.edu;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.edu.EducationPackage;

public interface EduPackageDao extends PagingAndSortingRepository<EducationPackage, String>, JpaSpecificationExecutor<EducationPackage>{
    List<EducationPackage> findByTitle(String title);
}
