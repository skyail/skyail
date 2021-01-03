package com.skyail.service;

import com.skyail.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aixiudou
 * @since 2020-11-08
 */
public interface ITestService extends IService<Test> {

    Test saveTest(Test test);

    List<Test> findTest(long id);

    void deleteTest(long id);
}
