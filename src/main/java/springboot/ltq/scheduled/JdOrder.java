package springboot.ltq.scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import springboot.ltq.entity.JdModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdOrder {

    //    https://blog.csdn.net/hwt3525055/article/details/76147346
    private static final Integer pageSize = 30;
    private static Integer total_page_number = 0;

    public static void main(String[] args) {
        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();

        Integer index = 1;
        Integer page = 1;
        Integer total = 0;
        if (index == 1 || index <= pageSize) {
            HttpGet httpGet2 = new HttpGet("https://search.jd.com/s_new.php?keyword=%E6%98%BE%E7%A4%BA%E5%99%A8144hz&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&ev=3613_92261%7C%7C92260%5E&page=2&s=31&scrolling=y&log_id=1523699513.73127&tpl=1_M&show_items=17927597230,5834295,18549407599,26662821394,6094781,19941871431,18312670501,5520840,5725871,26173320587,21399259349,12141925167,5068744,14820591762,5159062,5239271,2204007,1725769344,11525022776,6652415,26430776213,5256561,6287167,5483795,4447931,3173659,2015172,4737150,2173173,6331503");
//       https://search.jd.com/Search?keyword=%E6%98%BE%E7%A4%BA%E5%99%A8144hz&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&ev=3613_92261%7C%7C92260%5E#J_searchWrap
//
//       https://search.jd.com/Search?keyword=%E6%98%BE%E7%A4%BA%E5%99%A8144hz&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&ev=3613_92261%7C%7C92260%5E&page=5&s=121&click=0
//       https://search.jd.com/Search?keyword=%E6%98%BE%E7%A4%BA%E5%99%A8144hz&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&ev=3613_92261%7C%7C92260%5E&page=7&s=181&click=0
            httpGet2.setConfig(requestConfig);
            String srtResult = "";
            try {
                HttpResponse httpResponse = httpCilent2.execute(httpGet2);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    srtResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");//获得返回的结果
//                    System.out.println(srtResult);
                    //采用Jsoup解析
                    Document doc = Jsoup.parse(srtResult);
                    //获取html标签中的内容
                    Elements elements = doc.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
                    Elements text1 = doc.getElementsByTag("script").eq(3);
                    if (index == 0) {
                        /*循环遍历script下面的JS变量*/
                        for (Element element : text1) {
                            /*取得JS变量数组*/
                            String[] datas = element.data().toString().split("var");
                            /*取得单个JS变量*/
                            for (String variable : datas) {
                                /*过滤variable为空的数据*/
                                if (variable.contains("=")) {
                                    /*取到满足条件的JS变量*/
                                    if (variable.contains("LogParm")) {
                                        String js = variable.substring(variable.indexOf("result_count", 0), variable.indexOf("}", 0) + 1);
                                        JSONObject js_detail = JSON.parseObject(js);
                                        total = Integer.valueOf(js_detail.get("result_count").toString());
                                        total_page_number = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
                                        /*取得JS变量存入map*/
                                    }
                                }
                            }
                        }
                    }
                    getData(elements);
                    index++;
                    page += pageSize;
                } else {
                    System.err.println("发送邮件");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpCilent2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public static List<JdModel> getData(Elements elements) {
        //获取的数据，存放在集合中
        List<JdModel> data = new ArrayList<JdModel>();
//        String ttt = doc.select("div[class=p-wrap]").select("span[class=p-skip]").select("em").select("a").text();
        for (Element ele : elements) {
            String bookID = ele.attr("data-sku");
            String bookPrice = ele.select("div[class=p-price]").select("strong").select("i").text();
            String trest1 = ele.select("div[class=p-name p-name-type-2]").text();
            String bookName = ele.select("div[class=p-name p-name-type-2]").select("a").select("em").text();
            //创建一个对象，这里可以看出，使用Model的优势，直接进行封装
            JdModel jdModel = new JdModel();
            //对象的值
            jdModel.setBookID(bookID);
            jdModel.setBookName(bookName);
            jdModel.setBookPrice(bookPrice);
            System.err.println(bookPrice);
            //将每一个对象的值，保存到List集合中
            data.add(jdModel);
        }
        //返回数据
        return data;
    }

}
