package flatjson.impl;

import flatjson.iface.TreeTraverse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonTraverseTest {

    @Test
    void givenJsonTreeLikeString_printsTree(){
        String jsonText = data1();
        System.out.println(jsonText);

        TreeTraverse treeTraverse = new JsonTraverse(jsonText);
        treeTraverse.traverse(new StrategyPrint());

        System.out.println(String.join("\n", treeTraverse.getStrategy().toList()));
        String actual = String.join("\n", treeTraverse.getStrategy().toList());

        String expected = "     name=Jake type=STRING\n" +
                "     salary=3000 type=NUMBER\n" +
                "     phones: ARRAY\n" +
                "         0: OBJECT\n" +
                "             phoneType=cell type=STRING\n" +
                "             phoneNumber=111-111-111 type=STRING\n" +
                "         1: OBJECT\n" +
                "             phoneType=work type=STRING\n" +
                "             phoneNumber=222-222-222 type=STRING\n" +
                "     taskIds: ARRAY\n" +
                "         0=11 type=NUMBER\n" +
                "         1=22 type=NUMBER\n" +
                "         2=33 type=NUMBER\n" +
                "     address: OBJECT\n" +
                "         street=101 Blue Dr type=STRING\n" +
                "         city=White Smoke type=STRING";
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void givenJsonTreeLikeString_flattensTree(){
        String jsonText = data1();
        System.out.println(jsonText);

        TreeTraverse treeTraverse = new JsonTraverse(jsonText);
        treeTraverse.traverse(new StrategyFlatten());

        System.out.println(String.join("\n", treeTraverse.getStrategy().toList()));
        String actual = String.join("\n", treeTraverse.getStrategy().toList());
        String expected = "name.Jake\n" +
                "salary.3000\n" +
                "phones.0.phoneType.cell\n" +
                "phones.0.phoneNumber.111-111-111\n" +
                "phones.1.phoneType.work\n" +
                "phones.1.phoneNumber.222-222-222\n" +
                "taskIds.0.11\n" +
                "taskIds.1.22\n" +
                "taskIds.2.33\n" +
                "address.street.101 Blue Dr\n" +
                "address.city.White Smoke";

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void givenJsonString_printsTree(){
        String jsonText = data3();
        System.out.println(jsonText);

        TreeTraverse treeTraverse = new JsonTraverse(jsonText);
        treeTraverse.traverse(new StrategyPrint());

        System.out.println(String.join("\n", treeTraverse.getStrategy().toList()));
        String actual = String.join("\n", treeTraverse.getStrategy().toList());

        String expected = "     0: OBJECT\n" +
                "         name=Jake type=STRING\n" +
                "         salary=3000 type=NUMBER\n" +
                "         phones: ARRAY\n" +
                "             0: OBJECT\n" +
                "                 phoneType=cell type=STRING\n" +
                "                 phoneNumber=111-111-111 type=STRING\n" +
                "             1: OBJECT\n" +
                "                 phoneType=work type=STRING\n" +
                "                 phoneNumber=222-222-222 type=STRING\n" +
                "         taskIds: ARRAY\n" +
                "             0=11 type=NUMBER\n" +
                "             1=22 type=NUMBER\n" +
                "             2=33 type=NUMBER\n" +
                "         address: OBJECT\n" +
                "             street=101 Blue Dr type=STRING\n" +
                "             city=White Smoke type=STRING\n" +
                "     1: OBJECT\n" +
                "         name=Phil type=STRING\n" +
                "         salary=3400 type=NUMBER\n" +
                "         phones: ARRAY\n" +
                "             0: OBJECT\n" +
                "                 phoneType=cell type=STRING\n" +
                "                 phoneNumber=333-333-3333 type=STRING\n" +
                "             1: OBJECT\n" +
                "                 phoneType=work type=STRING\n" +
                "                 phoneNumber=444-444-4444 type=STRING\n" +
                "         taskIds: ARRAY\n" +
                "             0=44 type=NUMBER\n" +
                "             1=55 type=NUMBER\n" +
                "         address: OBJECT\n" +
                "             street=202 Green Ave type=STRING\n" +
                "             city=Greenville type=STRING";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void givenJsonString_flattensTree(){
        String jsonText = data3();
        System.out.println(jsonText);

        TreeTraverse treeTraverse = new JsonTraverse(jsonText);
        treeTraverse.traverse(new StrategyFlatten());

        System.out.println(String.join("\n", treeTraverse.getStrategy().toList()));
        String actual = String.join("\n", treeTraverse.getStrategy().toList());
        String expected = "0.name.Jake\n" +
                "0.salary.3000\n" +
                "0.phones.0.phoneType.cell\n" +
                "0.phones.0.phoneNumber.111-111-111\n" +
                "0.phones.1.phoneType.work\n" +
                "0.phones.1.phoneNumber.222-222-222\n" +
                "0.taskIds.0.11\n" +
                "0.taskIds.1.22\n" +
                "0.taskIds.2.33\n" +
                "0.address.street.101 Blue Dr\n" +
                "0.address.city.White Smoke\n" +
                "1.name.Phil\n" +
                "1.salary.3400\n" +
                "1.phones.0.phoneType.cell\n" +
                "1.phones.0.phoneNumber.333-333-3333\n" +
                "1.phones.1.phoneType.work\n" +
                "1.phones.1.phoneNumber.444-444-4444\n" +
                "1.taskIds.0.44\n" +
                "1.taskIds.1.55\n" +
                "1.address.street.202 Green Ave\n" +
                "1.address.city.Greenville";

        Assertions.assertEquals(expected, actual);
    }
    String data1(){
        return "{\"name\":\"Jake\",\"salary\":3000,\"phones\":"
                + "[{\"phoneType\":\"cell\",\"phoneNumber\":\"111-111-111\"},"
                + "{\"phoneType\":\"work\",\"phoneNumber\":\"222-222-222\"}],"
                +"\"taskIds\":[11,22,33],"
                + "\"address\":{\"street\":\"101 Blue Dr\",\"city\":\"White Smoke\"}}";
    }
    String data2(){
        return "{\"name\":\"Phil\",\"salary\":3400,\"phones\":"
                + "[{\"phoneType\":\"cell\",\"phoneNumber\":\"333-333-3333\"},"
                + "{\"phoneType\":\"work\",\"phoneNumber\":\"444-444-4444\"}],"
                +"\"taskIds\":[44,55],"
                + "\"address\":{\"street\":\"202 Green Ave\",\"city\":\"Greenville\"}}";
    }
    String data3(){
        return "[" + data1() + "," + data2() + "]";
    }
}