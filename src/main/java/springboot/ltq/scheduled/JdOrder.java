package springboot.ltq.scheduled;

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


    public static void main(String[] args) {
        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        HttpGet httpGet2 = new HttpGet("https://search.jd.com/Search?keyword=%E6%98%BE%E7%A4%BA%E5%99%A8144hz&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&ev=3613_92261%7C%7C92260%5E#J_searchWrap");

//       https://search.jd.com/Search?keyword=%E6%98%BE%E7%A4%BA%E5%99%A8144hz&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&ev=3613_92261%7C%7C92260%5E#J_searchWrap
//       https://search.jd.com/Search?keyword=%E6%98%BE%E7%A4%BA%E5%99%A8144hz&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&ev=3613_92261%7C%7C92260%5E&page=3&s=61&click=0
//       https://search.jd.com/Search?keyword=%E6%98%BE%E7%A4%BA%E5%99%A8144hz&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&ev=3613_92261%7C%7C92260%5E&page=5&s=121&click=0
//       https://search.jd.com/Search?keyword=%E6%98%BE%E7%A4%BA%E5%99%A8144hz&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&ev=3613_92261%7C%7C92260%5E&page=7&s=181&click=0
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        try {
            HttpResponse httpResponse = httpCilent2.execute(httpGet2);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                srtResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");//获得返回的结果
                System.out.println(srtResult);
                getData(srtResult);
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


    public static List<JdModel> getData(String html) throws Exception {
        //获取的数据，存放在集合中
        List<JdModel> data = new ArrayList<JdModel>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements = doc.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
        for (Element ele : elements) {
            String bookID = ele.attr("data-sku");
            String bookPrice = ele.select("div[class=p-price]").select("strong").select("i").text();
            String trest1 =  ele.select("div[class=p-name p-name-type-2]").text();
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
