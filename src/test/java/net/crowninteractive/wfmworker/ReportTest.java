/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.crowninteractive.wfmworker.dao.UsersDao;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author johnson3yo
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config-test.xml"})
public class ReportTest {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private WorkOrderDao wdao;

    private static String email = "mika.alanko@crowninteractive.com";

    //@Test
    public void testStatForUser() throws WfmWorkerException {
        System.out.println("----------------------STAT FOR MIKA ALANKO --------------------");
        String dstr = usersDao.findByEmail(email).getDistricts();
        List<String> districts = new ArrayList<String>(Arrays.asList(dstr.split("\\s*,\\s*")));
        System.out.println("----------------------No of Districts--------------- " + districts.size());
        String queueTypeId = usersDao.getQueueTypeIds(email);
        System.out.println("----------------------No of Queue Types -------------------" + queueTypeId.split(",").length);
        String tariffs = usersDao.getAssignedTariffs(email);
        System.out.println("----------------------No of Tariffs ----------------" + tariffs.split(",").length);

        int total = 0;

        for (int i = 0; i < districts.size(); i++) {
            System.out.printf("------------------DISTRICT %s ------------------------\n", districts.get(i));
            List<WorkOrder> list = wdao.getWorkOrderByParams(districts.get(i), "2016-01-01", "2018-10-31", queueTypeId, tariffs);
            System.out.printf("----------------------COUNT IN %s --------   is %d\n", districts.get(i), list.stream().count());
            total += list.stream().count();
        }

        System.out.println("--------------------Total Work Orders --------------------------" + total);

    }

}
