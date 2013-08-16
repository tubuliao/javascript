package com.isoftstone.tyw.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.KeyWord;
import com.isoftstone.tyw.repository.info.KeyWordDao;
@Component
@Transactional(readOnly = true)
public class KeyWordService {
    @Autowired
    private KeyWordDao keyWordDao;
    
    public List<KeyWord> searchHotWords(int isTag){
       return keyWordDao.findkeyword(isTag);
     }
}
