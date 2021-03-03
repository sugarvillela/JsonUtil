package flatjson.iface;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public interface TraverseStrategy {
    void branch(JsonNode node, String key, int level);
    void leaf(JsonNode node, String key, int level);
    ArrayList<String> toList();
}
