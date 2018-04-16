package springboot.ltq.scheduled;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        String keyword = "144hz%E6%98%BE%E7%A4%BA%E5%99%A8%202k";

        CloseableHttpClient httpCilent2 = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        HttpGet httpGet2 = new HttpGet("https://search.jd.com/s_new.php?keyword=144hz%E6%98%BE%E7%A4%BA%E5%99%A8%202k&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&page=2&s=29&scrolling=y&log_id=1523801375.21180&tpl=1_M");
        httpGet2.setConfig(requestConfig);

        httpGet2.setHeader(":authority", "search.jd.com");
        httpGet2.setHeader(":method", "GET");
        httpGet2.setHeader(":path", "/s_new.php?keyword=%E7%94%B5%E8%84%91&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&offset=6&wq=%E7%94%B5%E8%84%91&page=6&s=131&scrolling=y&log_id=1523790420.93457&tpl=1_M&show_items=5143491,5189394,6072614,15660770359,4345751,5168128,11199566916,5005865,14541812605,5933840,3794327,4334250,3951151,3794329,5702720,6031883,5025522,5352356,3682513,5168562,4796475,5088073,4472046,4129905,6226251,3178510,6704065,4979920,3915827,5223493");
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
            System.err.println(srtResult);
            Document doc = Jsoup.parse(srtResult);
            Elements elements = doc.select("li[class=gl-item]");
            System.err.println("1111");
        }
    }
//    https://search.jd.com/search?keyword=144hz%E6%98%BE%E7%A4%BA%E5%99%A8%202k&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=3.def.0.V01&wq=144&ev=291_84559%5E&page=3&s=61&click=0
    //https://search.jd.com/search?keyword=144hz%E6%98%BE%E7%A4%BA%E5%99%A8%202k&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=3.def.0.V01&wq=144&ev=291_84559%5E&page=3&s=61&click=0
}