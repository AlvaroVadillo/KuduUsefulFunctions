package org.kududb.examples.sample;


import org.apache.kudu.client.*;

import java.util.ArrayList;
import java.util.List;



public class KuduScan {

    private static final String KUDU_MASTER = System.getProperty(
            "kuduMaster", "localhost");

    public static void main(String[] args) {
        System.out.println("-----------------------------------------------");
        System.out.println("Will try to connect to Kudu master at " + KUDU_MASTER);
        System.out.println("Run with -DkuduMaster=myHost:port to override.");
        System.out.println("-----------------------------------------------");
        String tableName = "Table_2";
        Integer cont = 0;
        KuduClient client = new KuduClient.KuduClientBuilder(KUDU_MASTER).build();

        try {
            KuduTable table = client.openTable(tableName);
            table.getSchema();
            List<String> projectColumns = new ArrayList<>(2);
            projectColumns.add("key");
            projectColumns.add("value");

            KuduScanner scanner = client.newScannerBuilder(table)
                    .setProjectedColumnNames(projectColumns)
                    .build();
            while (scanner.hasMoreRows()) {
                RowResultIterator results = scanner.nextRows();
                while (results.hasNext()) {
                    RowResult result = results.next();
                    cont++;
                    System.out.println(result.rowToString());
                }
            }
            System.out.println("Number of rows: " + cont);
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



