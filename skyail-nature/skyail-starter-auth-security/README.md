#说明
##一.引入该模块需要实现的方法：
###1.1 继承 JdbcClientDetailsService 类 并实现其中的 loadClientByClientId 方法
#### 作用：加载client。如果不进行重写，会使用默认的逻辑。
#### 参考范例：
    @Service
    public class MyClientsDetailServiceImpl extends JdbcClientDetailsService {

        @Autowired
        private PasswordEncoder passwordEncoder;
    
        public MyClientsDetailServiceImpl(DataSource dataSource){
            super(dataSource);
            this.setPasswordEncoder(passwordEncoder);
        }
    
        @Override
        public ClientDetails loadClientByClientId(String clientId) {
            return super.loadClientByClientId(clientId);
        }

    }
    
###1.2 实现 UserDetailsService 中的 loadUserByUsername 方法
#### 作用：用于加载用户信息
#### 使用范例：
        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if("test".equals(s)){
                SysUser user = new SysUser();
                user.setId(1);
                user.setPassword(encoder.encode("123123"));
                user.setStatus(0);
                List<SysRole> roles = new ArrayList<>();
                SysRole role = new SysRole();
                role.setId(1);
                //这个地方比较坑，spring security oauth2 的 @PreAuthorize("hasRole('admin')") 注解中，hasRole表达式默认会添加前缀 ROLE_,
                //所以，如果生成token的时候，没有这个前缀，那么接口权限就无法通过了，会报 403，所以这里需要添加前缀 ROLE_
                role.setRoleName("ROLE_admin");
                role.setRoleDesc("ROLE_admin");
                roles.add(role);
                user.setRoles(roles);
                return user;
            }
            return null;
        }
        
###1.3 实现 IRoleMapService 中的 loadRoleMap方法
#### 作用：用于加载角色的权限信息(某个url需要怎样的角色才能访问)
#### 实现范例：
    @Service
    public class MyIUrlRoleMapServiceImpl implements IUrlRoleMapService {

        @Override
        public Map<String, String> loadRoleMap() {
            Map<String,String> map = new HashMap<>();
            map.put("/one/**","ROLE_test");
            return map;
        }
    }


##二.主要功能
###1.配置方式放权(ignore，暂不支持注解方式放权) ，并自动忽略鉴权(不再校验角色)。
###2.注解的方式指定接口权限 , @PreAuthorize("hasRole('admin')") 
###3.动态指定接口权限。
###4.配置方式指定url跳过认证。
###5.支持动态认证。
###6.支持动态放权