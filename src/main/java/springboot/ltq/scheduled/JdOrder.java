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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springboot.ltq.dao.ComputerDao;
import springboot.ltq.entity.Computer;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdOrder {


    private static final Integer pageSize = 29;
    private static Integer total_page_number = 0;
    private static Integer total = 0;

    private static List<Computer> computerList = new ArrayList<>();

    @Autowired
    ComputerDao computerDao;

    //    @PostConstruct
    public static List<Computer> computer(String keyword) {
        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();

        Integer index = 1;
        Integer page = 1;
        Integer totalNumberGet = 0;
        getOrder(keyword, httpCilent2, requestConfig, index, totalNumberGet);
        return computerList;
    }

    private static void getOrder(String keyword, CloseableHttpClient httpCilent2, RequestConfig requestConfig, Integer index, Integer totalNumberGet) {
        if (totalNumberGet < total || index == 1) {
            HttpGet httpGet2 = new HttpGet("https://search.jd.com/Search?keyword=" + keyword + "&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page=" + index + "&s=" + ((index - 1) * pageSize + 1));
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
                    if (index == 1) {
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
                                        variable = variable.replace("\n\t", "");
                                        variable = variable.replace("\t", "");
                                        variable = variable.replace("\n", "");
                                        String js = variable.substring(variable.indexOf("result_count", 0) - 1, variable.indexOf("}", 0) + 1);
                                        JSONObject js_detail = JSON.parseObject(js);
                                        total = Integer.valueOf(js_detail.get("result_count").toString());
                                        total_page_number = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 5;
                                        /*取得JS变量存入map*/
                                    }
                                }
                            }
                        }
                    }
                    List<Computer> data = getData(elements);
                    index++;
                    totalNumberGet += elements.size();
                    Integer numnber = nextPage(keyword, totalNumberGet, index);
                    totalNumberGet += numnber;
                    index++;
                }
                getOrder(keyword, httpCilent2, requestConfig, index, totalNumberGet);
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
        } else {
            System.err.println("结束");
        }

    }

    public static Integer nextPage(String keyword, Integer totalNumberGet, Integer index) throws IOException {
        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        HttpGet httpGet2 = new HttpGet("https://search.jd.com/s_new.php?keyword=" + keyword + "&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page=" + index + "&s=" + ((index - 1) * pageSize + 1) + "&scrolling=y&log_id=1523801375.21180&tpl=1_M");
        httpGet2.setConfig(requestConfig);
        httpGet2.setHeader(":authority", "search.jd.com");
        httpGet2.setHeader(":method", "GET");
        httpGet2.setHeader(":path", "/s_new.php?keyword=" + keyword + "&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&offset=6&wq=%E7%94%B5%E8%84%91&page=6&s=131&scrolling=y&log_id=1523790420.93457&tpl=1_M&show_items=5143491,5189394,6072614,15660770359,4345751,5168128,11199566916,5005865,14541812605,5933840,3794327,4334250,3951151,3794329,5702720,6031883,5025522,5352356,3682513,5168562,4796475,5088073,4472046,4129905,6226251,3178510,6704065,4979920,3915827,5223493");
        httpGet2.setHeader("scheme", "https");
        httpGet2.setHeader("accept", "*/*");
        httpGet2.setHeader("accept-encoding", "zh-CN,zh;q=0.8");
        httpGet2.setHeader("accept-language", "*/*");
        httpGet2.setHeader("cookie", "qrsc=3; _jrda=1; TrackID=1i5y2KJw1bzQzABrYwEwJhJYRrmNy55aAoYgpSDeC7S3xq25pdZWAxk0S9GWMifXPxZJSDVejwEm6RvT9G1g-J5aIrd5Y_UC5WU6i_Sc2hEM; pinId=eIqlNlzNjzvKGxb6DNDVCbV9-x-f3wj7; PCSYCityID=904; unpl=V2_ZzNtbURVR0ZxXE5ceBBVUWIHRVgRAkcRcAtOASweXlBhVhtcclRCFXwUR1FnGVgUZwsZXEdcQRFFCHZXchBYAWcCGllyBBNNIEwHDCRSBUE3XHxcFVUWF3RaTwEoSVoAYwtBDkZUFBYhW0IAKElVVTUFR21yVEMldQl2VHsQXAFnBBFVRmdzEkU4dlZ7EVQAVwIiXHIVF0lxCUJVfBERBWcKEllCUEAdcThHZHg%3d; CCC_SE=ADC_aJ4muRUfUfH1pdUvFd7M4cz2Kxp1uJ28je7mne4%2b7iu1ziI8TzrmgKyscbdAtIzgmaGV3xebdl6JNP5%2fiCmlFv2LvGhdRZEmt6lxyZ9FUfeGeWbOVjVdg3bJ0U1oJdT7g%2fEDNoFUuM5x4OrHMgJFXVq59hPlR5fda3T0NIhet%2bFgz3fwhn1w6lYrptaJcmDQyXaAg9JW48fYSvfgp5lRMXfdvu9MI7jWq8v7gRKWkP1K5c4K3XRq2dIsxXFuKpbpY4uMKd0NAwugPtd2jHx9Awe3uz12W69j5MNN47J7uGiSVI8qrtM0bPrP3C11apYadhQQjIVQrR%2bAYfZk5YpJpl2EEUtmpLmET5ojLsMOXrjiQilmXt0mZET0OdDHDLPeElxFoeYTGbYBXLvq4HtaF6xskz2VQaHC2TmiUpOz30BtEX71A68%2bgWnumdL3e2sUJ94j%2boCiYbqcATdLsDZt%2f74sG6tR8%2biCc3NPPzMtKBI2JxOEXV4ngVvPkt1qZBzgioIBQUOpiyH%2bx5e6K6tp9OQuDnkQtEPyytjOzIGPbhaoQ5OlkPjDcrVg9be%2bvuBH; __jdv=122270672|baidu-pinzhuan|t_288551095_baidupinzhuan|cpc|0f3d30c8dba7459bb52f2eb5eba8ac7d_0_735b5e99288e45f4bd55429df63d7d80|1523790276249; ipLoc-djd=1-72-2799-0; rkv=V0100; 3AB9D23F7A4B3C9B=PQGWO3IKYG4JF645ONUMM77UMLNFQ3APMVTZ4TQKXNLROJNLBX76K7TQLHGIMQXWG6FIMHU5CCIZ3VO4BPWSQSQAQU; __jda=122270672.1181516295.1520517442.1523790016.1523790276.3; __jdb=122270672.4.1181516295|3.1523790276; __jdc=122270672; xtest=2586.cf6b6759; __jdu=1181516295");
        httpGet2.setHeader("referer", "https://search.jd.com/Search?keyword=" + keyword + "&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&offset=6&wq=%E7%94%B5%E8%84%91&page=5&s=104&click=0");
        httpGet2.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        httpGet2.setHeader("x-requested-with", "XMLHttpRequest");

        String srtResult = "";
        HttpResponse httpResponse = httpCilent2.execute(httpGet2);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            srtResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");//获得返回的结果
            Document doc = Jsoup.parse(srtResult);
            Elements elements = doc.select("li[class=gl-item]");
            getData(elements);
            return elements.size();
        }
        return 0;
    }


    public static List<Computer> getData(Elements elements) {
        //获取的数据，存放在集合中
        List<Computer> data = new ArrayList<Computer>();
        for (Element ele : elements) {
            String sku = ele.attr("data-sku");
            String price = ele.select("div[class=p-price]").select("strong").select("i").text();
            String name = ele.select("div[class=p-name p-name-type-2]").select("a").select("em").text();
            String img_url = ele.select("div[class=p-img]").select("a").select("img").attr("src");
            if (img_url == null || "".equals(img_url)) {
                img_url = ele.select("div[class=p-img]").select("a").select("img").attr("data-lazy-img");
            }
            //创建一个对象，这里可以看出，使用Model的优势，直接进行封装
            Computer computer = new Computer();
            //对象的值
            computer.setSku(sku);
            computer.setName(name);
            computer.setPrice(price);
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            computer.setCreate_time(timeStamp);
            computer.setImg_url(img_url);
            //将每一个对象的值，保存到List集合中
            data.add(computer);
        }
//        if (data.size() > 0) {
//            computerDao.inserComputerList(data);
//        }
        //返回数据
        computerList.addAll(data);
        return data;
    }


    //https://search.jd.com/Search?keyword=" + keyword + "&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page=1
    //https://search.jd.com/s_new.php?keyword=" + keyword + "&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page=2

//    https://search.jd.com/Search?keyword=144hz%E6%98%BE%E7%A4%BA%E5%99%A8%202k&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page=2&s=31
//    https://search.jd.com/s_new.php?keyword=144hz%E6%98%BE%E7%A4%BA%E5%99%A8%202k&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page=2&s=29&scrolling=y&log_id=1523801375.21180&tpl=1_M
}
