package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.springframework.stereotype.Repository;
<#if mapperAnnotation>
    import org.apache.ibatis.annotations.Mapper;
</#if>

<#if mapperAnnotation>
    @Mapper
</#if>
<#if kotlin>
    interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Repository
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
