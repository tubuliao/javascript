package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Bookmark;
import com.isoftstone.tyw.entity.info.Classification;
import com.isoftstone.tyw.repository.info.BookmarkDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Component
@Transactional(readOnly = true)
public class BookmarkService {

	@Autowired
	private BookmarkDao bookmarkDao ;
	
	@Transactional(readOnly = false)
	public List<Bookmark> getBookmarkSet(String userId, Integer pageIndex, Integer items_per_page) throws ParseException {
		return bookmarkDao.findBookmarkSet(userId, pageIndex, items_per_page) ; 
	}
	
	@Transactional(readOnly = false) 
	public List<Bookmark> getBookmarkListByCid(String userId, Integer pageIndex, Integer items_per_page, String id) throws ParseException {
		return bookmarkDao.findBookmarkList(userId, pageIndex, items_per_page, id) ;
	}
	
	@Transactional(readOnly = false)
	public BigInteger getBookmarkTotal(String userId) {
		return bookmarkDao.findAllTotal(userId) ;
	}
	
	@Transactional(readOnly = false)
	public BigInteger getBookmarkTotalByCid(String userId, String cId) {
		return bookmarkDao.findAllTotalByCid(userId, cId) ;
	}
	
	@Transactional(readOnly = false)
	public void removeBookmark(String bId) {
		bookmarkDao.deleteBookmark(bId) ;
	}
	
	public Page<Bookmark> findByClassification(Classification classification,Pageable pageable) {
       return bookmarkDao.findByClassification(classification,pageable) ;
    }
    
    public Page<Bookmark> findByUserId(String uid,Pageable pageable) {
        return bookmarkDao.findByUserId(uid,pageable); 
     }
	@Transactional(readOnly = false) 
	public void choseClassification(String bId, String cId) {
		bookmarkDao.updateClassificationId(bId, cId) ;
	}
	
	@Transactional(readOnly = false)
	public BigInteger getBookmarkCountByCid(String cId) {
		return bookmarkDao.findAllCountByCid(cId) ;
	}
	
	@Transactional(readOnly = false)
	public Bookmark getOneById(String id) {
		return bookmarkDao.findOneById(id) ;
	}
	
	@Transactional(readOnly = false)
	public void saveOne(Bookmark b) {
		bookmarkDao.save(b) ;
	}
	@Transactional(readOnly = false)
    public Bookmark saveBookMark(Bookmark b) {
       return bookmarkDao.save(b) ;
    }
    public Bookmark findByUserIdUrl(String uid,String url) {
       return bookmarkDao.searchbkByUserIdAndUrl(uid,url) ;
    }
	@Transactional(readOnly = false)
	public Bookmark getOneByUserIdUrl(String id, String url) {
		return bookmarkDao.findOneByUserIdUrl(id, url) ;
	}
	 
	@Transactional(readOnly = false)
	public List<Bookmark> findByClassificationAndUserId(Classification classification,String uid) {
        return bookmarkDao.findByClassificationAndUserId(classification,uid);
    }
	
	@Transactional(readOnly = false)
	public BigInteger getBookmarkCountByCidAndUserId(String cId, String uId) {
		return bookmarkDao.findAllCountByCidAndUid(cId, uId) ;
	}
	
	@Transactional(readOnly = false)
	public Bookmark getOneByUrl(String url, String userId, String title) {
		return bookmarkDao.findOneByUrl(url, userId, title);
	}
	
	public BigInteger searchCollectCount(String cId){
        return bookmarkDao.searchCollectCount(cId);
    }
	
	public void ModifyClassificationId(String id) {
		bookmarkDao.updateClassificationIdNull(id);
	}
	
}
