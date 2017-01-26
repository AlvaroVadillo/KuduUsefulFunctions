package org.kududb.examples.sample;


import org.apache.kudu.client.*;
import org.apache.log4j.Logger;



public class DeleteTable {

    private static final Logger LOG = Logger.getLogger(DeleteTable.class);
    private static final String KUDU_MASTER = System.getProperty(
            "kuduMaster", "localhost");

    public static void main(String[] args) {
        System.out.println("-----------------------------------------------");
        System.out.println("Will try to connect to Kudu master at " + KUDU_MASTER);
        System.out.println("Run with -DkuduMaster=myHost:port to override.");
        System.out.println("-----------------------------------------------");
        String tableName = "Table_2";
        KuduClient client = new KuduClient.KuduClientBuilder(KUDU_MASTER).build();

        try {
            client.deleteTable(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


