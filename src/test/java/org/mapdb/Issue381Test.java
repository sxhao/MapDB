package org.mapdb;

import org.junit.Test;

import java.io.File;
import java.util.concurrent.ConcurrentMap;

public class Issue381Test {


    @Test
    public void testCorruption()
            throws Exception
    {

        File f = UtilsTest.tempDbFile();

        for(int j=0;j<10;j++) {
            final int INSTANCES = 1000;
            DBMaker maker = DBMaker.newFileDB(f);
            TxMaker txMaker = maker.makeTxMaker();
            DB tx = txMaker.makeTx();
            byte[] data = new byte[128];

            ConcurrentMap<Long, byte[]> map = tx.getHashMap("persons");
            map.clear();
            for (int i = 0; i < INSTANCES; i++) {
                map.put((long) i, data);
            }
            tx.commit();
            txMaker.close();
        }

    }
}
