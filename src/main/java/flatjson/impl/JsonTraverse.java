package flatjson.impl;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import flatjson.iface.TraverseStrategy;
import flatjson.iface.TreeTraverse;

import java.io.IOException;

public class JsonTraverse implements TreeTraverse {
    private JsonNode rootNode;
    private TraverseStrategy strategy;

    public JsonTraverse(String jsonText) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            rootNode = objectMapper.readTree(jsonText);
        } catch (IOException e) {
            rootNode = null;
            throw new IllegalArgumentException(e.getMessage());
        }
        //System.out.printf("root: %s type=%s%n", rootNode, rootNode.getNodeType());
    }

    @Override
    public void traverse(TraverseStrategy strategy) {
        this.strategy =  strategy;
        this.recurse(rootNode, 1);
    }

    @Override
    public TraverseStrategy getStrategy() {
        return strategy;
    }

    private void recurse(JsonNode node, int level) {
        if (node.getNodeType() == JsonNodeType.ARRAY) {
            traverseArray(node, level);
        } else if (node.getNodeType() == JsonNodeType.OBJECT) {
            traverseObject(node, level);
        } else {
            throw new RuntimeException("Not yet implemented");
        }
    }

    private void traverseObject(JsonNode node, int level) {
        node.fieldNames().forEachRemaining((String fieldName) -> {
            JsonNode childNode = node.get(fieldName);
            callStrategy(childNode, fieldName, level);

            if (!isLeaf(childNode)) {
                recurse(childNode, level + 1);
            }
        });
    }

    private void traverseArray(JsonNode node, int level) {
        int i = 0;
        for (JsonNode jsonArrayNode : node) {
            callStrategy(jsonArrayNode, "" + (i++), level);
            if (!isLeaf(jsonArrayNode)) {
                recurse(jsonArrayNode, level + 1);
            }
        }
    }

    private boolean isLeaf(JsonNode node) {
        return node.getNodeType() != JsonNodeType.OBJECT &&
                node.getNodeType() != JsonNodeType.ARRAY;
    }

    private void callStrategy(JsonNode node, String key, int level) {
        if (isLeaf(node)) {
            strategy.leaf(node, key, level);
        } else {
            strategy.branch(node, key, level);
        }
    }
}
