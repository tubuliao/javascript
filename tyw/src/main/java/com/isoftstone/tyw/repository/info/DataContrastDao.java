package com.isoftstone.tyw.repository.info;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.info.DataContrast;

public interface DataContrastDao extends
		PagingAndSortingRepository<DataContrast, String>{


	@Query(nativeQuery=true, value="SELECT uuid() AS id, 'msg13' AS data_type, COUNT(*) AS counts FROM tywadb.analysis_segment UNION SELECT uuid() AS id, 'msg14' AS data_type, COUNT(*) AS counts FROM tywadb.analysis_form UNION SELECT uuid() AS id, 'msg15' AS data_type, COUNT(*) AS counts FROM tywadb.analysis_files UNION SELECT uuid() AS id, 'msg16' AS data_type, COUNT(*) AS counts FROM tywadb.analysis_image UNION SELECT uuid() AS id, 'msg17' AS data_type, COUNT(*) AS counts FROM tywadb.analysis_video UNION SELECT uuid() AS id, 'msg18' AS data_type, COUNT(*) AS counts FROM tywadb.analysis_ppt ")
	public List<DataContrast> findDataListFromTywadb();
	
	@Query(nativeQuery=true, value="SELECT uuid() AS id, 'msg19' AS data_type, COUNT(*) AS counts FROM tywdb.info_segment UNION SELECT uuid() AS id, 'msg20' AS data_type, COUNT(*) AS counts FROM tywdb.info_form UNION SELECT uuid() AS id, 'msg21' AS data_type, COUNT(*) AS counts FROM tywdb.info_files UNION SELECT uuid() AS id, 'msg22' AS data_type, COUNT(*) AS counts FROM tywdb.info_image UNION SELECT uuid() AS id, 'msg23' AS data_type, COUNT(*) AS counts FROM tywdb.info_video UNION SELECT uuid() AS id, 'msg24' AS data_type, COUNT(*) AS counts FROM tywdb.info_powerpoint ")
	public List<DataContrast> findDataListFromTywdb();
	
	@Query(nativeQuery=true, value="SELECT UUID() AS id, 'msg01' AS data_type, COUNT(s01.id) AS counts FROM tywdb.info_segment s01 LEFT JOIN tywadb.analysis_segment s02 ON s01.id = s02.id WHERE s02.id IS NULL UNION SELECT UUID() AS id, 'msg02' AS data_type, COUNT(s02.id) AS counts FROM tywdb.info_segment s01 RIGHT JOIN tywadb.analysis_segment s02 ON s01.id = s02.id WHERE s01.id IS NULL UNION SELECT UUID() AS id, 'msg03' AS data_type, COUNT(f01.id) AS counts FROM tywdb.info_form f01 LEFT JOIN tywadb.analysis_form f02 ON f01.id = f02.id WHERE f02.id IS NULL UNION SELECT UUID() AS id, 'msg04' AS data_type, COUNT(f02.id) AS counts FROM tywdb.info_form f01 RIGHT JOIN tywadb.analysis_form f02 ON f01.id = f02.id WHERE f01.id IS NULL UNION SELECT UUID() AS id, 'msg05' AS data_type, COUNT(fi01.id) AS counts FROM tywdb.info_files fi01 LEFT JOIN tywadb.analysis_files fi02 ON fi01.id = fi02.id WHERE fi02.id IS NULL UNION SELECT UUID() AS id, 'msg06' AS data_type, COUNT(fi02.id) AS counts FROM tywdb.info_files fi01 RIGHT JOIN tywadb.analysis_files fi02 ON fi01.id = fi02.id WHERE fi01.id IS NULL UNION SELECT UUID() AS id, 'msg07' AS data_type, COUNT(i01.id) AS counts FROM tywdb.info_image i01 LEFT JOIN tywadb.analysis_image i02 ON i01.id = i02.id WHERE i02.id IS NULL UNION SELECT UUID() AS id, 'msg08' AS data_type, COUNT(i02.id) AS counts FROM tywdb.info_image i01 RIGHT JOIN tywadb.analysis_image i02 ON i01.id = i02.id WHERE i01.id IS NULL UNION SELECT UUID() AS id, 'msg09' AS data_type, COUNT(v01.id) AS counts FROM tywdb.info_video v01 LEFT JOIN tywadb.analysis_video v02 ON v01.id = v02.id WHERE v02.id IS NULL UNION SELECT UUID() AS id, 'msg10' AS data_type, COUNT(v02.id) AS counts FROM tywdb.info_video v01 RIGHT JOIN tywadb.analysis_video v02 ON v01.id = v02.id WHERE v01.id IS NULL UNION SELECT UUID() AS id, 'msg11' AS data_type, COUNT(p01.id) AS counts FROM tywdb.info_powerpoint p01 LEFT JOIN tywadb.analysis_ppt p02 ON p01.id = p02.id WHERE p02.id IS NULL UNION SELECT UUID() AS id, 'msg12' AS data_type, COUNT(p02.id) AS counts FROM tywdb.info_powerpoint p01 RIGHT JOIN tywadb.analysis_ppt p02 ON p01.id = p02.id WHERE p01.id IS NULL")
	public List<DataContrast> findDataListByDifference();
	
}
