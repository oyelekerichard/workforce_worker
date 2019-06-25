/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ci.test;

import com.google.gson.Gson;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.dao.WorkOrderTempDao;
import net.crowninteractive.wfmworker.entity.EnumerationWorkOrder;
import net.crowninteractive.wfmworker.misc.WorkOrderJson;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author uchep
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config-test.xml"})
public class EnumerationTest {

    @Autowired
    private WorkOrderTempDao temp;

    @Autowired
    private WorkOrderDao wdao;

    String sql = "{\n"
            + "  \"summary\": \"Work for TEST USER reported by MEDIUM [The pp meter is over reading according to d user]\",\n"
            + "  \"description\": \"Work for TEST USER reported by MEDIUM [The pp meter is over reading according to d user]\",\n"
            + "  \"queueTypeToken\": \"oKrLbJSfaAxOMU0JRV4Y1K8Zum96C3\",\n"
            + "  \"businessUnit\": \"APAPA\",\n"
            + "  \"contactNumber\": \"Unknown\",\n"
            + "  \"referenceType\": \"ACCOUNT NUMBER\",\n"
            + "  \"referenceTypeData\": \"0362110325-01\",\n"
            + "  \"priority\": \"MEDIUM\",\n"
            + "  \"city\": \"LAGOS\",\n"
            + "  \"address\": \"101 OJO ROAD AJEROMI APAPA AJEROMI LAGOS\",\n"
            + "  \"serviceWireCode\": \"03021007107012301100101\",\n"
            + "  \"reportedBy\": \"TEST USER\",\n"
            + "  \"disco\": \"03\",\n"
            + "  \"subDisco\": \"APAPA\",\n"
            + "  \"injectionSubstation\": \"03021007\",\n"
            + "  \"injectionSubstationName\": \"APAPA ROAD INJ26 T1\",\n"
            + "  \"powerTransformer\": \"030210071\",\n"
            + "  \"powerTransformerName\": \"T1\",\n"
            + "  \"feeder\": \"03021007107\",\n"
            + "  \"feederName\": \"AJEGUNLE EXPRESS\",\n"
            + "  \"htPole\": \"030210071070123\",\n"
            + "  \"highTensionPhysicalId\": \"0000\",\n"
            + "  \"distributionSubstation\": \"03021007107012301\",\n"
            + "  \"distributionSubstationName\": \"ECN BUSTOP\",\n"
            + "  \"upriser\": \"030210071070123011\",\n"
            + "  \"servicePole\": \"030210071070123011001\",\n"
            + "  \"serviceWire\": \"03021007107012301100101\",\n"
            + "  \"nercId\": \"03021007107012301100101021\",\n"
            + "  \"connectionType\": \"11KV/415KV\",\n"
            + "  \"transformer\": \"03021007107012301\",\n"
            + "  \"enumerationData\": {\n"
            + "    \"connection_type\": \"11KV/415KV\",\n"
            + "    \"feederid\": \"03021007107\",\n"
            + "    \"transformername\": \"ECN BUSTOP\",\n"
            + "    \"transformerid\": \"03021007107012301\",\n"
            + "    \"cableupriserid\": \"030210071070123011\",\n"
            + "    \"ltpoleid\": \"030210071070123011001\",\n"
            + "    \"servicewireno\": \"03021007107012301100101\",\n"
            + "    \"customer_number\": \"03021007107012301100101021\",\n"
            + "    \"supply_type\": \"1\",\n"
            + "    \"premises_type\": \"BUNGALOW\",\n"
            + "    \"nature_of_use_electricity\": \"COMMERCIAL\",\n"
            + "    \"landlord_name\": \"OJUKO ADIYAT\",\n"
            + "    \"customer_name_on_bill\": \"OJUKO ADIYAT\",\n"
            + "    \"customer_phone_no\": \"Unknown\",\n"
            + "    \"e_mail_address\": \"Unknown\",\n"
            + "    \"customer_account_no\": \"0362110325-01\",\n"
            + "    \"plot\": \"N/A\",\n"
            + "    \"house_no\": \"101\",\n"
            + "    \"street\": \"OJO ROAD AJEROMI\",\n"
            + "    \"landmark\": \"APAPA AJEROMI\",\n"
            + "    \"city\": \"LAGOS\",\n"
            + "    \"tariff\": \"CIS\",\n"
            + "    \"meter_no\": \"0101150391981\",\n"
            + "    \"meter_by_pass\": \"No\",\n"
            + "    \"meter_design_type\": \"ELECTRONIC\",\n"
            + "    \"meter_status\": \"FUNCTIONAL\",\n"
            + "    \"meter_seal_no\": \"Unknown\",\n"
            + "    \"meter_reading\": \"Unknown\",\n"
            + "    \"dials\": \"Unknown\",\n"
            + "    \"multiplier_factor_on_meter\": \"Unknown\",\n"
            + "    \"ct_ratio\": \"11\",\n"
            + "    \"pt_ratio\": \"415\",\n"
            + "    \"x_coordinate\": \"537275.8596\",\n"
            + "    \"y_coordinate\": \"713960.5931\",\n"
            + "    \"billing_type\": \"PPM\",\n"
            + "    \"phase_designation\": \"A\",\n"
            + "    \"cutout_size\": \"60A\",\n"
            + "    \"number_of_air_conditioner\": \"Unknown\",\n"
            + "    \"approximate_total_rating_of_ac\": \"Unknown\",\n"
            + "    \"comment\": \"The pp meter is over reading according to d user\",\n"
            + "    \"created_by\": 0,\n"
            + "    \"update_stat\": \"UPDATED\",\n"
            + "    \"is_edservicepoint_active\": true,\n"
            + "    \"htpoleid\": \"030210071070123\",\n"
            + "    \"priority\": \"MEDIUM\",\n"
            + "    \"provider\": \"TEST USER\",\n"
            + "    \"old_tariff\": \"R2T\",\n"
            + "    \"transformer_rating\": \"22\",\n"
            + "    \"transformer_type\": \"HIGH\"\n"
            + "  }\n"
            + "}";

    public void testCreateWorkOrderTemp() {
        WorkOrderJson woj = new Gson().fromJson(sql, WorkOrderJson.class);
        temp.createEnumerationWorkOrder(woj);
    }

    public void get() {
        EnumerationWorkOrder enumerationWorkOrder = wdao.getEnumerationWorkOrder("DP2Hn7775MoL1w6VxIZnD1e4GJWeHX");
        System.out.println(new Gson().toJson(enumerationWorkOrder));
    }

}
