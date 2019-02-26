/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.crowninteractive.wfmworker.dao.RequestObj;
import org.junit.Test;

/**
 *
 * @author johnson3yo
 */
public class ParralelDeliquencyTest {

    private static Integer count = 10000;

    @Test
    public void test() {
        RequestObj[] list = populateRequestObject(count);
        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(new RequestRunnable(0, 2500, list));
        executor.submit(new RequestRunnable(2500, 5000, list));
        executor.submit(new RequestRunnable(5000, 7500, list));
        executor.submit(new RequestRunnable(7500, 10000, list));

    }

    private RequestObj[] populateRequestObject(Integer cnt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class RequestRunnable implements Runnable {

        private int start;
        private int end;
        private RequestObj[] obj;

        public RequestRunnable(int start, int end, RequestObj obj[]) {
            this.start = start;
            this.end = end;
            this.obj = obj;
        }

        @Override
        public void run() {
            for (int c = start; c < end; c++) {
                RequestObj reqobj = obj[c];
                
                
            }
        }

    }

}
