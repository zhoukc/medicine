<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>action</title>
</head>
<style>
    li {
        list-style-type: none;
    }

    a {
        color: #000
    }
</style>
<body>
<#list alist as item>
    <ul>
            <#if (item.description!=null)>
                <li>注释:${item.description}</li>
            </#if>
        <li>路径:${item.path}</li>
        <li>请求方法:${item.method},请求类型:${item.produces}</li>
           <#if (item.inputName!=null)>
                <li><a href="/help/parameter?type=${item.inputName}">输入参数:${item.inputSimpleName}</a></li>
           </#if>
        <li><a href="/help/parameter?type=${item.outputName}">输出参数:${item.outputSimpleName}</a></li>
        <li>输入格式:${item.inputExample}</li>
        <li>输出格式:${item.outputExample}</li>
    </ul>
</#list>
</body>
</html>
