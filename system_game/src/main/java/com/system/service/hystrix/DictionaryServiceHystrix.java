package com.system.service.hystrix;

import com.system.bean.Dictionary;
import com.system.service.DictionaryService;
import org.springframework.stereotype.Component;

/**
 * @author zzy
 * @Date 2018/8/25 上午10:02
 */
@Component
public class DictionaryServiceHystrix implements DictionaryService{
    @Override
    public String getPage(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public String add(Dictionary dictionary) {
        return null;
    }

    @Override
    public String update(Dictionary dictionary) {
        return null;
    }

    @Override
    public String delete(Integer[] ids) {
        return null;
    }

    @Override
    public String getDictionary(Integer id) {
        return null;
    }

    @Override
    public String checkCode(String code, Integer id) {
        return null;
    }

    @Override
    public String checkValue(String value, Integer parentId, Integer id) {
        return null;
    }

    @Override
    public String getName(String code, String value) {
        return null;
    }

    @Override
    public String getDicListByCode(String code) {
        return null;
    }
}
