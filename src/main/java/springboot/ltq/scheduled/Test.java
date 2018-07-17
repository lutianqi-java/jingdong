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
import springboot.ltq.entity.Job;

import java.io.IOException;

public class Test {


    //https://www.zhipin.com/c101020100-p100101/e_104-y_4/?ka=sel-salary-4
    //https://www.zhipin.com/c101020100-p100101/e_104-y_4/?page=2&ka=page-2
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        HttpGet httpGet2 = new HttpGet("https://www.zhipin.com/c101020100-p100101/e_104-y_4/?page=2&ka=page-1");
        httpGet2.setConfig(requestConfig);


        String srtResult = "";
        HttpResponse httpResponse = httpCilent2.execute(httpGet2);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            srtResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");//获得返回的结果
            System.err.println(srtResult);
            Document doc = Jsoup.parse(srtResult);

            Elements element = doc.getElementsByClass("item");
//            for (Elements ele : elements) {
//
//            }
            for (Element links : element) {
                String url = links.select("a").attr("href");
                Element link = links.getElementsByClass("text").first();
                Element masthead = link.select("div>h4").first();
                Element masthead1 = link.select("div>span").first();
                String jobName = masthead.text();
                String money = masthead1.text();
                String company = link.getElementsByClass("name").first().text();
                Elements elements = link.select("div>em");
                String address = elements.get(0).text();
                String year = elements.get(1).text();
                String grade = elements.get(2).text();
                Job job = new Job(jobName, money, company, address, year, grade, null);



            }
//            System.err.println(element);
            System.out.println("---------------------------------------------------");
        }
    }

//    public static void main(String[] args) throws IOException {
//        String keyword = "144hz%E6%98%BE%E7%A4%BA%E5%99%A8%202k";
//        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectTimeout(5000)   //设置连接超时时间
//                .setConnectionRequestTimeout(5000) // 设置请求超时时间
//                .setSocketTimeout(5000)
//                .setRedirectsEnabled(true)//默认允许自动重定向
//                .build();
//        HttpGet httpGet2 = new HttpGet("https://s.taobao.com/search?ie=utf8&initiative_id=staobaoz_20180416&stats_click=search_radio_all%3A1&js=1&imgfile=&q=" + keyword + "&suggest=0_2&_input_charset=utf-8&wq=32%E5%AF%B8%E6%9B%B2%E9%9D%A2%E6%98%BE%E7%A4%BA%E5%99%A82k&suggest_query=32%E5%AF%B8%E6%9B%B2%E9%9D%A2%E6%98%BE%E7%A4%BA%E5%99%A82k&source=suggest");
//        httpGet2.setConfig(requestConfig);
//        String srtResult = "";
//        HttpResponse httpResponse = httpCilent2.execute(httpGet2);
//        if (httpResponse.getStatusLine().getStatusCode() == 200) {
//            srtResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");//获得返回的结果
////                    System.out.println(srtResult);
//            //采用Jsoup解析
//            Document doc = Jsoup.parse(srtResult);
//            System.err.println(srtResult);
//            System.err.println("111");
//        }
//
//    }

    // https://s.taobao.com/api?_ksTS=1523848203905_224&callback=jsonp225&ajax=true&m=customized&stats_click=search_radio_all:1&q=32%E5%AF%B8%E6%9B%B2%E9%9D%A2%E6%98%BE%E7%A4%BA%E5%99%A82k%20144hz&s=36&imgfile=&initiative_id=staobaoz_20180416&bcoffset=-1&js=1&ie=utf8&rn=cc935d4deae81ae68fa13c2c04b81fba


}