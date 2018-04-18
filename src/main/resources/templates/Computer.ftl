<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<table>
    <tr>
        <td>
            序号
        </td>
        <td>图片</td>
        <td>产品</td>
        <td>价格</td>
    </tr>
    <#list list as row>
        <tr>
            <td>${row_index+1}</td>
            <td><img src="${row.img_url}"></td>
            <td>${row.name}</td>
            <td>${row.price}</td>
        </tr>
    </#list>

</table>
</body>
</html>