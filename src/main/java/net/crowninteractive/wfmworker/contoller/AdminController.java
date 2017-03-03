
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.contoller;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.commons.io.IOUtils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//~--- JDK imports ------------------------------------------------------------

import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author osita
 */
@RestController
@RequestMapping(
    method = RequestMethod.POST,
    value  = "/v1/general"
)
public class AdminController {
    @RequestMapping(
        method = RequestMethod.POST,
        value  = "send_sms"
    )
    public String sendSms(@RequestBody CustomObj obj) {
        sendSMS(obj.getRecipents(), obj.getMessage());

        return "OK";
    }

    public static void sendSMS(String phone, String message) {
        try {
            String resp =
                "http://auxli.com/coolwaresms/api/index.html?sender=EKEDC&recipients=" + phone + "&message="
                + URLEncoder.encode(message, "UTF-8")
                + "&uid=OG3Nei1l53FmP9kP8JNJTsRnGK3cWQqj3cwCXSO8FNNFhEl0ylET1Gzlxkgw7mYvCdHa3cYJEVhqZh6VUhs9eEh1rCRyy7gJYAk5jQevnH6CCVvGd9K";

            System.out.println("sms result " + IOUtils.toString(new URL(resp)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
