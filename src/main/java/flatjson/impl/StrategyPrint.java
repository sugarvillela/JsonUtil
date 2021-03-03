package flatjson.impl;

import com.fasterxml.jackson.databind.JsonNode;
import flatjson.iface.TraverseStrategy;

import java.util.ArrayList;

public class StrategyPrint implements TraverseStrategy {
    private final ArrayList<String> list;

    public StrategyPrint() {
        list = new ArrayList<>();
    }

    @Override
    public void branch(JsonNode node, String key, int level) {
        list.add(String.format("%" + (level * 4 - 3) + "s    %s: %s", "", key, node.getNodeType()));
    }

    @Override
    public void leaf(JsonNode node, String key, int level) {
        list.add(String.format("%" + (level * 4 - 3) + "s    %s=%s type=%s", "", key, nodeValue(node), node.getNodeType()));
    }

    @Override
    public ArrayList<String> toList() {
        return list;
    }

    private String nodeValue(JsonNode node){
        if (node.isTextual()) {
            return node.textValue();
        } else if (node.isNumber()) {
            return String.valueOf(node.numberValue());
        }
        else{
            throw new IllegalStateException("implement value type");
        }
    }
}
