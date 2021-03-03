package flatjson.impl;

import com.fasterxml.jackson.databind.JsonNode;
import flatjson.iface.TraverseStrategy;

import java.util.ArrayList;

public class StrategyFlatten implements TraverseStrategy {
    private final ArrayList<ArrayList<String>> flatList;
    private ArrayList<String> currRow;
    int row;

    public StrategyFlatten() {
        flatList = new ArrayList<>();
        row = 0;
    }

    @Override
    public void branch(JsonNode node, String key, int level) {
        currRow = getNextRow(key, level);
    }

    @Override
    public void leaf(JsonNode node, String key, int level) {
        currRow = getNextRow(key, level);
        currRow.add(nodeValue(node));
        flatList.add(currRow);
    }

    @Override
    public ArrayList<String> toList() {
        ArrayList<String> list = new ArrayList<>();
        for(ArrayList<String> flatRow : flatList){
            list.add(String.join(".", flatRow));
        }
        return list;
    }

    private ArrayList<String> getNextRow(String key, int level){
        ArrayList<String> nextRow;
        if(currRow == null){
            nextRow = new ArrayList<>();
        }
        else {
            nextRow = new ArrayList<>(currRow);
            while(nextRow.size() > level - 1){
                nextRow.remove(nextRow.size() - 1);
            }
        }
        nextRow.add(key);
        return nextRow;
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
