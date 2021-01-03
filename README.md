# Skyail
## 一套简单、易用、专注开发细节的java开发框架，内容囊括java轻应用开发，企业级应用开发，大数据应用开发，前端应用开发等。以企业中遇到的真实问题为背景，集成当下最新的、轻量的、实用的功能或者组件，让日常开发更快速，更省心。

<img src="http://www.5ixiudou.com/hexo-images/post-64.png" />

# skyail-hummingbird  蜂鸟(蜂鸟虽小，五脏俱全)
## 轻量、易用、注重开发细节的快速开发项目。可以用于日常任务、数据处理、业务接口等场景。

## 一。全套后端代码自动生成
### 1.配置generator.properties
    #数据库连接信息
    driver=org.sqlite.JDBC
    url=jdbc:sqlite:./db/db.db?date_string_format=yyyy-MM-dd HH:mm:ss
    username=
    password=
    #表名称，多张表之间用,分隔
    table_names=s_user
    #模块名称
    module_name=
    #代码根目录名称
    base_dir=com.skyail
### 2.执行CodeGenerator中的main方法生成代码

## 二。支持多种数据源
### 1.mysql
### 2.oracle
### 3.postgresql
### 4.sqlite

## 三。认证相关
### 1.token的用户名密码设置
#### 通过 application.yml 中的 auth.user 节点进行设置。
    auth:
      user:
        username: admin
        password: 71d8b5968604e655c8a7a8a254b8f8c6

### 2.自定义用户加载逻辑
#### 可以用于自定义加载用户名密码逻辑，比如从数据库中加载用户名和密码等。
#### 2.1首先开启自定义用户加载
    auth:
      custom-user: false
#### 2.2自定义service，实现接口 ICustomUserService
    @Service
    public class CustomUserServiceImpl implements ICustomUserService{
    
        @Override
        public User loadUserInfo(String username) {
            User user = new User();
            //可以从数据库中加载用户信息
            user.setPassword("71d8b5968604e655c8a7a8a254b8f8c6");
            user.setUsername("admin");
            return user;
        }
    }

### 3.认证放权
#### 3.1 方式一：通过注解 @AuthIgnore 来跳过token认证。
    @GetMapping("/findByCondition")
    @AuthIgnore
    public R findByCondition(String name ){
        Test test = new Test();
        test.setName(name);
        QueryWrapper wrapper = new QueryWrapper(test);
        return R.data(service.list(wrapper));
    }
#### 3.2 方式二：通过在配置文件中配置：auth.ignore来跳过token认证。
    auth:
      ignore:
        - /test/findByCondition
        - /test_new/**
        
## 四。国密算法
#### 集成 SM2、SM3、SM4国密算法，加密更高效、更安全。

## 五。开发细节支持
### 1.XSS防御。增强系统防护能力。
    #xss过滤
    xss:
      enable: true
      excludes:
        #- /test/*
        #- /test1/*
### 2.接口参数自动trim。统一处理接口首位空字符问题。
    #接口输入参数自动trim
    auto-trim:
      enable: true
### 3.便捷接口参数校验。通过注解的方式对接口参数进行校验。
#### 3.1 第一步 在 entity 中添加注解
    @NotNull(message = "id不能为空")
    private Integer id;
#### 3.2 第二步 在 controller中开启校验
    @GetMapping("/testValidate")
    public String testValidate(@Validated Test test){
        if(!StringUtils.isEmpty(test.getId())){
            return "id is "+test.getId();
        }
        return null;
    }
### 4.缓存支持。
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
### 5.统一异常处理。
#### 实现异常的统一处理，无需再设置异常处理逻辑。
### 6.接口返回包装。
    @PostMapping("/findByConditionPost")
    public R findByConditionPost( @RequestBody Test test ){
        QueryWrapper wrapper = new QueryWrapper(test);
        return R.data(service.list(wrapper));
    }


