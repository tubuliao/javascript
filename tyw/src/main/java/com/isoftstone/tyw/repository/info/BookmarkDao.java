package com.isoftstone.tyw.repository.info;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Bookmark;
import com.isoftstone.tyw.entity.info.Classification;

public interface BookmarkDao extends
		PagingAndSortingRepository<Bookmark, String>{

	@Query(nativeQuery=true, value="SELECT b.* FROM info_bookmark b WHERE b.user_id = :userId LIMIT :pageIndex, :items_per_page ")
	public List<Bookmark> findBookmarkSet(@Param("userId")String userId, @Param("pageIndex")Integer pageIndex, @Param("items_per_page")Integer items_per_page) ;
	
	@Query(nativeQuery=true, value="SELECT b.* FROM info_bookmark b WHERE b.user_id = :userId AND b.classification_id = :cId LIMIT :pageIndex, :items_per_page ")
	public List<Bookmark> findBookmarkList(@Param("userId")String userId, @Param("pageIndex")Integer pageIndex, @Param("items_per_page")Integer items_per_Page, @Param("cId")String cId) ;
	
	@Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_bookmark WHERE user_id = :userId")
	public BigInteger findAllTotal(@Param("userId")String userId) ;
	
	@Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_bookmark WHERE user_id = :userId AND classification_id = :cId")
	public BigInteger findAllTotalByCid(@Param("userId")String userId, @Param("cId")String cId) ;
	
	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM info_bookmark WHERE id = :bId") 
	public void deleteBookmark(@Param("bId")String bId) ;
	
	List<Bookmark> findByClassificationAndUserId(Classification classification,String uid);
	
	Page<Bookmark> findByUserId(String uid,Pageable pageable);
	
	Page<Bookmark> findByClassification(Classification classification,Pageable pageable);
	@Modifying
	@Query(nativeQuery=true, value="UPDATE info_bookmark b SET b.classification_id = :cId WHERE b.id = :bId")
	public void updateClassificationId(@Param("bId")String bId, @Param("cId")String cId) ;
	
	@Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_bookmark WHERE classification_id = :cId")
	public BigInteger findAllCountByCid(@Param("cId")String cId) ;
	
	@Query(nativeQuery=true, value="SELECT b.* FROM info_bookmark b WHERE b.id = :id")
	public Bookmark findOneById(@Param("id")String id) ;
	
	@Query(nativeQuery=true, value="SELECT * FROM info_bookmark b WHERE b.id = :id AND b.url = :url")
	public Bookmark findOneByUserIdUrl(@Param("id")String id, @Param("url")String url) ;
	
	@Query(value="select bk from Bookmark bk where bk.userId = :uid and bk.url=:url")
     public Bookmark searchbkByUserIdAndUrl(@Param("uid")String uid, @Param("url")String url);
	
	@Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_bookmark WHERE classification_id = :cId AND user_id = :uId")
	public BigInteger findAllCountByCidAndUid(@Param("cId")String cId, @Param("uId")String uId) ;
	
	@Query(value="from Bookmark b where b.url = :url and b.userId = :userId and b.title = :title")
	public Bookmark findOneByUrl(@Param("url")String url, @Param("userId")String userId, @Param("title")String title);
 
	@Query(nativeQuery=true, value="select count(*) as count from info_bookmark mark where mark.knowledge_id=?1")
    public BigInteger searchCollectCount(String id) ;
	
	
	@Modifying
	@Query(nativeQuery=true, value="UPDATE info_bookmark b SET b.classification_id = null WHERE b.id = :id")
	public void updateClassificationIdNull(@Param("id")String id);
}
