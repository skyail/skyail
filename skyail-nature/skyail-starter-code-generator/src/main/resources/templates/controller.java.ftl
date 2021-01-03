package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* <#if table.comment??>
    *   ${table.comment} 前端控制器
    * <#else>
    *    前端控制器
    * </#if>
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle??>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> extends ${superControllerClass}()</#if>
<#elseif superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};

    /**
    * <#if table.comment??>
        *   分页 ${table.comment}
        * <#else>
        *   分页
        * </#if>
    */
    @GetMapping("/list")
    public R<IPage<${entity}>> findByPage(Page<${entity}> page, @Validated ${entity} ${entity?uncap_first}){
        return R.ok(${table.serviceName?uncap_first}.page(page,new QueryWrapper<>(${entity?uncap_first})));
    }

    /**
    * <#if table.comment??>
        *   根据条件查询 ${table.comment}
        * <#else>
        *   根据条件查询
        * </#if>
    */
    @PostMapping("/listByCondition")
    public R<List<${entity}>> findByCondition(@RequestBody ${entity} ${entity?uncap_first}){
        return R.ok(${table.serviceName?uncap_first}.list(new QueryWrapper<>(${entity?uncap_first})));
    }

    /**
    * <#if table.comment??>
        *   提交 ${table.comment}
        * <#else>
        *   提交
        * </#if>
    */
    @PostMapping("/submit")
    public R submit(@RequestBody ${entity} ${entity?uncap_first}){
        return R.ok(${table.serviceName?uncap_first}.saveOrUpdate(${entity?uncap_first}));
    }

    /**
    * <#if table.comment??>
        *   详细信息 ${table.comment}
        * <#else>
        *   详细信息
        * </#if>
    */
    @GetMapping("/detail")
    public R<${entity}> detail (@RequestParam Long id){
        return R.ok(${table.serviceName?uncap_first}.getById(id));
    }

    /**
    * <#if table.comment??>
        *   删除(支持逻辑删除) ${table.comment}
        * <#else>
        *   删除(支持逻辑删除)
        * </#if>
    */
    @GetMapping("/remove")
    public R remove(String ids){
        return R.ok(${table.serviceName?uncap_first}.removeByIds(Arrays.asList(ids.split(","))));
    }


}