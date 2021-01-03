package com.skyail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.skyail.entity.Test;
import com.skyail.mapper.TestMapper;
import com.skyail.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aixiudou
 * @since 2020-11-08
 */
@Service
@EnableCaching
@Slf4j
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

    @Override
    @CachePut(value = "test")
    public Test saveTest(Test test) {
        baseMapper.insert(test);
        return test;
    }

    @Override
    @Cacheable(value = "test")
    public List<Test> findTest(long id) {
        log.info("here is find");
        Test test = new Test();
        test.setId(1);
        QueryWrapper wrapper = new QueryWrapper(test);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(value = "test")
    public void deleteTest(long id) {
        baseMapper.deleteById(id);
    }
}
