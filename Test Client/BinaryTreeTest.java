package client;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by deepakrtp on 03/04/17.
 */

public class BinaryTreeTest {

    private BinaryTree tree;

    @BeforeMethod
    public void initialise() {
        tree = new BinaryTree();
    }

    @DataProvider(name = "SHOULD RETURN TRUE WHEN PERMISSION GRANTED")
    public static Object[][] testCaseSetPermission () {
        return new Object[][] {{0},{1},{2},{3},{4},{5},{6}};
    }

    @DataProvider(name = "SHOULD RETURN TRUE WHEN WHEN PERMISSION GRANTED BY TREE OF SERVERS")
    public static Object[][] testCaseLogicalTree() {
        return new Object[][] {{0,1,2,3,4,5,6},{0,1,4,},{0,1,3},{0,3,4},{0,2,6},{0,2,5},{0,5,6},{1,2,4,6},{1,2,3,5},{3,4,5,6},{1,2,3,6},{1,2,4,5},{1,2}};
    }


    @Test(dataProvider = "SHOULD RETURN TRUE WHEN PERMISSION GRANTED")
    public void testSetPermissionGranted(int serverID) throws Exception {
            tree.setPermissionGranted(serverID);
            Assert.assertEquals(tree.checkifGranted(serverID),true);
    }


    @Test (dataProvider = "SHOULD RETURN TRUE WHEN WHEN PERMISSION GRANTED BY TREE OF SERVERS")
    public void testCheckLogicalTree(int [] serverID) throws Exception {
            for(int i = 0 ; i <  serverID.length;i++) {
                tree.setPermissionGranted(serverID[i]);
            }
            Assert.assertEquals(tree.checkLogicalTree(),true);
    }
}