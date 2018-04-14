package springboot.ltq.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class TemplateParseUtil {

    /**
     * 解析模板生成Excel
     *
     * @param templateDir  模板目录
     * @param templateName 模板名称
     * @param excelPath    生成的Excel文件路径
     * @param data         数据参数
     * @throws IOException
     * @throws TemplateException
     */
    public static OutputStreamWriter parse(HttpServletResponse response, String templateDir, String templateName, Map<String, Object> data) throws IOException, TemplateException {
        //初始化工作
        Configuration cfg = new Configuration();
        //设置默认编码格式为UTF-8
        cfg.setDefaultEncoding("UTF-8");
        //全局数字格式
        cfg.setNumberFormat("0.00");
        //设置模板文件位置
        Resource resource = new ClassPathResource(templateDir);
//        File file = resource.getFile();
        cfg.setDirectoryForTemplateLoading(resource.getFile());
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        //加载模板
        Template template = cfg.getTemplate(templateName, "utf-8");
        OutputStreamWriter writer = null;
        //填充数据至Excel
        writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        template.process(data, writer);
        return writer;
    }

//
//    /**
//     * 解析模板返回字节数组
//     *
//     * @param templateDir  模板目录
//     * @param templateName 模板名称
//     * @param data         数据参数
//     * @throws IOException
//     * @throws TemplateException
//     */
//    public  byte[] parse(String templateDir, String templateName, Map<String, Object> data) throws TemplateException, IOException {
//        Configuration cfg = new Configuration();
//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setNumberFormat("0.00");
//        cfg.setDirectoryForTemplateLoading(new File(templateDir));
//        cfg.setObjectWrapper(new DefaultObjectWrapper());
//        Template template = cfg.getTemplate(templateName, "utf-8");
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        Writer out = new OutputStreamWriter(outStream, "UTF-8");
//        template.process(data, out);
//        return outStream.toByteArray();
//    }
//
//    /**
//     * 自定义模板字符串解析
//     *
//     * @param templateStr 模板字符串
//     * @param data        数据
//     * @return 解析后的字符串
//     * @throws IOException
//     * @throws TemplateException
//     */
//    public  String parse(String templateStr, Map<String, Object> data)
//            throws IOException, TemplateException {
//        Configuration cfg = new Configuration();
//        cfg.setNumberFormat("#.##");
//        //设置装载模板
//        StringTemplateLoader stringLoader = new StringTemplateLoader();
//        stringLoader.putTemplate("myTemplate", templateStr);
//        cfg.setTemplateLoader(stringLoader);
//        //加载装载的模板
//        Template temp = cfg.getTemplate("myTemplate", "utf-8");
//        Writer out = new StringWriter();
//        temp.process(data, out);
//        return out.toString();
//    }

//
//    @Test
//    public void excelTest() {
//        List<Good> userList = new ArrayList<Good>();
//        for (int i = 1; i < 10; i++) {
//            Good user = new Good();
//            user.setGoodsName("狗娃" + i);
//            user.setNumber("许文强");
//            user.setVolume("123456");
//            user.setWeight("上海虎头帮总舵");
//            user.setXuetou("28");
//            userList.add(user);
//        }
//        //测试Excel文件生成
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("list", userList);
//        try {
//
//            TemplateParseUtil.parse("src/main/template", "excel.ftl", "src/main/tempFile/excel.xls", data);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}
