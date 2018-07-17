package springboot.ltq.scheduled;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fedex {

    public static void main(String[] args) {


        //获取可关闭的 httpCilent
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000000).setConnectionRequestTimeout(1000000)
                .setSocketTimeout(1000000).setRedirectsEnabled(true).build();

        HttpPost httpPost = new HttpPost("https://www.fedex.com/trackingCal/track");
        //设置超时时间
        httpPost.setConfig(requestConfig);


        httpPost.setHeader("Accept", "*/*");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Cookie", "_abck=8B5DBF034AE2197B9BE8029682CCF89E172DE89CBC7E0000C013CC5A1483514B~0~yh/6i8UAelupQWlQGJLU3Fx7kJAr0cdrGKiF+V1qc2w=~-1~-1; wdpl_id=30711172601523323839075580105741_1525420841004; siteDC=edc; bm_sz=BA86E48D13074F3E04879DC258EB61B2~QAAQnOgtF1GvtSBjAQAAdhp4ONJssX0iyUjQefva5bA2Z9kzD3kd3F5kjk4i95IpMoOLQuObkjsJbALF9YN0yx1VD6e6XtwNEAHSWT4b1XJUlWVOBkEzRXaiOtNrUL3SzbzR0qKsl5wGDK/D9bj+atWm+p9SAuGSLH3jGkcvvbR/3hDv0hK86hMoPFTP6w==; AMCVS_1E22171B520E93BF0A490D44%40AdobeOrg=1; QSI_HistorySession=http%3A%2F%2Fwww.fedex.com%2Fus%2Fdeveloper%2F~1525660740587; check=true; fdx_rememberme=COCAM888; SMIDENTITY=e+IFE3VBEhRUIOPP23WAzocYYh6wc0+hOJ+WFaDpH9IEdwvWqmO6sXpgAKeLLqvCZnxCxKBZM0GTfPhql/6LkzKpyGfVZcbWXirohg2sPb1F7pgtY5qvcW9ZImeLfrBQotc1yOHTNNi8ieKc/go54a3QevT3XeuCX8eWGn/NNL7GgH+aqNExRt4QJXy35URPTxuQLqL/3oArwtP181IfqXgSLZGBsdgfElS/61HZvDNOnbYu/PIqFCrZGTktp0GMiYIBuzwKXOgR5PfNi/VyQX9cfbzyVvqsnAxBOmqQNaZZhJFBVP5w5y2s3N2eihO1Z40wyacGzLVJ3a/kKDgp103RmhGl8VMa8Tywj3HoUHrdmUqabTYWmF40k1BihVnG7CBgjlThYNtjFuxDPV4bFgRvsBQHy7DEyxxWiVSa9bv2NAjztxcGkAUBDj83ez4JVILoP5Z6427w2yGvdG8lV9mczMoA+NA6PfCSAfax1WX2BmSNB3wmktgSeHk0WdRN1Bu6bgduC+fFV2+5TgZ5wnhm2cyxXKC4; fcl_fname=WANG; fcl_uuid=000g070fjk; fdx_cbid=31211212281525660853064510094971; cc=/cn/; https://www.fedex.com/cn/developer/web-services/process.html_group=toggle-c4; aam_sc=aamsc%3D1825561%7C1825557%7C3400763; WSTMSESSIONID=l5Q4k0YMh0a0shVk1aaOkzbXv4agm4Gkho57wQMG3L9S1s3qEsGR!-1881788366; https://www.fedex.com/us/developer/web-services/office/process.html_group=toggle-c2; fdx_locale=en_CA; countryPath=ca_english; Nina-nina-fedex-session=%7B%22locale%22%3A%22en_CA%22%2C%22lcstat%22%3Afalse%7D; fcl_contactname=WANG YUN; fdx_login=10025.c768.3ef2e7d5; tracking_locale=en_CA; SMSESSION=1QGYoyorjANraGvh6frB8c0wYhmz1EHyEWRRomM4PANzGwTz77iTCVolHzDcwyWkr8ydKVp52D2+PJUsmf217tWFB4Pq1N/QeMpDuM3oYSnr5+HxlsHdyYrG99mGHwPe/Qf+iDds598IWNr0SUJDt8krAVdl1O3xZHOYlDYeRa/eqL3ErA0Uoi2vGRDrR5rs/o7Esw6r6habfDXKSVWO5Y2nyL94bs8puiEgXp6La3g8xJUvb/ENGXz5J7faMQdePtlXILnOa8Bg9fSTpd6Snp9BQqqBoFGVZtjdjbejJ+EzAApyuiTDi63+Aqg9SkOeON67TCOQVJfYS8tW7GEzYBKO1unzJbxPpQuFrzHdLHRdmmQwU2sTfIKZY2EEcwn9UGmS6GxYyyepQR+XvUhSQjqTBrf5jVjSN9CxTvE3I3+7ydoJWrexy5FzrLAVeBK4S+RXAX04L5aiZVmBXy10UTeBo6yRzlkDG8VCqucZRKYEnIR2S32YpXWfQYYewdDvHxkeW2WaL6iWWrRRhMUwHsT67kJjXDQVW1vAQWqbaVtaB0caRWKjZr8rYc9fBYe9FCmQa+eYU/A/NE9v0G827ZUxwHrOht7Sw5ywArYNNQRbQLLtB6QWmTkfJjZJsVaoCGrgSb1ADYW3u5/fQl7sxAgugjUOq9RAaGy+xe0P4zNThhQdJSqVZox3DT+sgwm1AMinD6RbfAB5yx3/O/OrVXyBUNNYNQTOv9NB7LSLK5EdFLhf5rckVrv2tkntRjqOirGX8O0ldnisQuLQsqDSavTpft5tXwmVoiL+5hWMisdSjCSVVqP6feQMztjrLEu9GY3g+8BVfvkp/0QF1gtqm2deXooTkB/lhfIfu8FMDiTtLUYyA0xb8FrowlSiUsQ1qahz3ZHcZxB9nSTgtyxjK/JRcLoTa8xg2mijp8duZ7Bu7YeCMgby9en3zUvXwqmxt6NSf+aNNOccHMTOWjhZoFNsOCy/4IeQosKQkC7mIfpmBNzbwPPtQ8eUMH5G9KAJ; mbox=session#1525669185394-727185#1525671046; ak_bmsc=BC94DB672BDA8B91F3378E76CC418750172DE8A6BB39000072DDEF5A2BB0630F~plxPaM718Wq38JgDea8hbm/AOQ/13zNzxwP4t2ZW7tMqN9GG0MgUhSEHaGSYFOlGz497O/ZxOGccN+GCBKDtzX52Bl1JUbyy5j3YeVnp9pP90HVUCkGzViwlQbTSKbd4O1uekUXdjX+iXjiX7fHI1m6lvOcAqbRwam+l6plBuhhB7w1ohZIgfYJ7jsM0k/oNKAAFX+1ReRtCR5zzw/NlvB+irXnaFCF7SljN1OPe4v6ld9NwE5/Ff5shfE9RMKh1jc; AMCV_1E22171B520E93BF0A490D44%40AdobeOrg=817868104%7CMCIDTS%7C17659%7CMCMID%7C19383838102297676812594577854475492023%7CMCAAMLH-1526265679%7C11%7CMCAAMB-1526273997%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1525676397s%7CNONE%7CMCAID%7CNONE%7CMCSYNCSOP%7C411-17666%7CvVersion%7C2.3.0%7CMCCIDH%7C1469359350; s_sq=fedexus%3D%2526c.%2526a.%2526activitymap.%2526page%253Dus%25252Fen%25252Ffedex%25252Fdeveloper%25252Fweb_services%2526link%253D%2525E8%2525B5%2525B0%2526region%253Dtoggle-c4%2526pageIDType%253D1%2526.activitymap%2526.a%2526.c%2526pid%253Dus%25252Fen%25252Ffedex%25252Fdeveloper%25252Fweb_services%2526pidt%253D1%2526oid%253Dfunctiononclick%252528event%252529%25257BgoToPage%252528this.form.LD1%25252Cthis.form.ss_1a_doc%252529%25257D%2526oidt%253D2%2526ot%253DBUTTON; bm_sv=4805097F1E92ADD7FB9E5537AACA9082~Z1PTZdGzxtr2j3BvXOaSP8iAW5asZsFR1XJkXiGQQDTLSxb2KLzkZkwlgYtAsw025zrYEg0TBvsxv5iSM/sNBA2iyp7nGRMszuHeULcgEfCvy/9s6FVRs/BwhPfH/+RBfxHQ4lYbnzvJ9DTMoQVWapp1lke5IW1UruYG55pqLm4=; s_sess=%20s_cpc%3D0%3B%20s_cm%3Dundefinedwww.baidu.comNatural%2520Search%3B%20s_ppv%3Dca%252Fen%252Ffedex%252Fbusinesstools%252Ftrackingtools%252C84%252C47%252C1961%3B%20setLink%3D%3B%20s_cc%3Dtrue%3B%20s_visit%3D1%3B%20SC_LINKS%3D%3B; s_pers=%20s_tbms_tbm14%3D1%7C1526876884063%3B%20s_skwcvp%3D%255B%255B%2527O%25253AKeyword%252520Unavailable%2527%252C%25271525421371055%2527%255D%252C%255B%2527O%25253AKeyword%252520Unavailable%2527%252C%25271525665484072%2527%255D%255D%7C1683431884072%3B%20s_vnum%3D1525708800903%2526vn%253D2%7C1525708800903%3B%20s_dfa%3Dfedexca%252Cfedexglbl%7C1525670985429%3B%20sc_fcl_uuid%3D000g070fjk%7C1533445185444%3B%20gpv_pageName%3Dca%252Fen%252Ffedex%252Funified%252Ftrackdetailspage%7C1525671158392%3B%20s_nr%3D1525669358395-Repeat%7C1557205358395%3B%20s_invisit%3Dtrue%7C1525671158397%3B%20s_tbm%3D1%7C1525671158399%3B");
        httpPost.setHeader("Host", "www.fedex.com");
        httpPost.setHeader("Origin", " https://www.fedex.com");
        httpPost.setHeader("Referer", "https://www.fedex.com/apps/fedextrack/?action=track&trackingnumber=489110210613556&cntry_code=ca_english");
        httpPost.setHeader("User-Agent", "ozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        httpPost.setHeader("X-Requested-With", " XMLHttpRequest");


        //装配post请求参数
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("data", "{\"TrackPackagesRequest\":{\"appType\":\"WTRK\",\"appDeviceType\":\"DESKTOP\",\"supportHTML\":true,\"supportCurrentLocation\":true,\"uniqueKey\":\"\",\"trackingInfoList\":[{\"trackNumberInfo\":{\"trackingNumber\":\"489110210479169\",\"trackingQualifier\":\"\",\"trackingCarrier\":\"\"}},{\"trackNumberInfo\":{\"trackingNumber\":\"489110210479176\",\"trackingQualifier\":\"\",\"trackingCarrier\":\"\"}},{\"trackNumberInfo\":{\"trackingNumber\":\"489110210479183\",\"trackingQualifier\":\"\",\"trackingCarrier\":\"\"}}]}}\n"));  //请求参数
        list.add(new BasicNameValuePair("action", "trackpackages")); //请求参数

        list.add(new BasicNameValuePair("locale", "en_CA")); //请求参数
        list.add(new BasicNameValuePair("version", "1")); //请求参数
        list.add(new BasicNameValuePair("format", "json")); //请求参数
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            //设置post求情参数
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String strResult = "";
            if (httpResponse != null) {
                System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                } else {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                    System.err.println("发送邮件");
                }
            } else {

            }
            System.out.println(strResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close(); //释放资源
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}