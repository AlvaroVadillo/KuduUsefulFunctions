package org.kududb.examples.sample;

import org.apache.kudu.client.*;


public class KuduInsert {

    private static final String KUDU_MASTER = System.getProperty(
            "kuduMaster", "localhost");

    public static void main(String[] args) {
        System.out.println("-----------------------------------------------");
        System.out.println("Will try to connect to Kudu master at " + KUDU_MASTER);
        System.out.println("Run with -DkuduMaster=myHost:port to override.");
        System.out.println("-----------------------------------------------");
        String tableName = "Table_1";
        KuduClient client = new KuduClient.KuduClientBuilder(KUDU_MASTER).build();

        try {

            KuduTable table = client.openTable(tableName);
            KuduSession session = client.newSession();
            for (int i = 0; i < 50000; i++) {
                Insert insert = table.newInsert();
                PartialRow row = insert.getRow();
                row.addInt(0, i);
                row.addString(1, "This is the row number: "+ i);
                session.apply(insert);
            }

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