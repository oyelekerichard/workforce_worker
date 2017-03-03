/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.misc;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author USER
 */
public class EmailText {
    public static String getApprovalContent() throws IOException{
        
      String content = Files.toString(new File(Config.getInstance().approvalEmailTemplateUrl()), Charsets.UTF_8);
      return content;
}
     public static String getRejectionContent() throws IOException{
        
      String content = Files.toString(new File(Config.getInstance().rejectionEmailTemplateUrl()), Charsets.UTF_8);
      return content;
}
    
}
