package jsonutil;

import flatjson.iface.TreeTraverse;
import flatjson.impl.StrategyFlatten;
import flatjson.impl.StrategyPrint;
import flatjson.impl.JsonTraverse;

public class Main {

    public static void main(String[] args) {
        System.out.println("hello world");
        String jsonText = "{\"name\":\"Jake\",\"salary\":3000,\"phones\":"
                + "[{\"phoneType\":\"cell\",\"phoneNumber\":\"111-111-111\"},"
                + "{\"phoneType\":\"work\",\"phoneNumber\":\"222-222-222\"}],"
                +"\"taskIds\":[11,22,33],"
                + "\"address\":{\"street\":\"101 Blue Dr\",\"city\":\"White Smoke\"}}";
        System.out.println("jsonText: " + jsonText);

        System.out.println();
        testPrint(jsonText);

        System.out.println();
        testFlatten(jsonText);
    }

    private static void testPrint(String jsonText){
        TreeTraverse treeTraverse = new JsonTraverse(jsonText);
        treeTraverse.traverse(new StrategyPrint());
        String result = String.join("\n", treeTraverse.getStrategy().toList());

        System.out.println(result);
    }

    private static void testFlatten(String jsonText){
        TreeTraverse treeTraverse = new JsonTraverse(jsonText);
        treeTraverse.traverse(new StrategyFlatten());
        String result = String.join("\n", treeTraverse.getStrategy().toList());

        System.out.println(result);
    }
}
