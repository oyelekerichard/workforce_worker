package net.crowninteractive.wfmworker.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.crowninteractive.wfmworker.misc.Config;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Charlee
 */
public class HttpQuery {
    
     private static final Config c = Config.getInstance();

    static {
        disableSslVerification();
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public String getCustomerDetails(String billingID, String type) throws IOException {
    
         CloseableHttpClient httpclient = HttpClients.createDefault();
        HashMap<String, String> queryparams = new HashMap<>();

        if (type.toLowerCase().equals("a")) {
            queryparams.put("accountNumber", billingID);

        } else {
            queryparams.put("meterNumber", billingID);
        }
        String url = URLBuilder.queryURLBuilder(c.getEMCCGetCustomerDetailsURL(), queryparams);
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json");
        HttpResponse response = httpclient.execute(httpGet);
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println("string resp:  " + sb.toString());
        return (sb.toString());
    }

    

}
