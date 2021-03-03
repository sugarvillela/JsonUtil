package flatjson.iface;

public interface TreeTraverse {
    void traverse(TraverseStrategy strategy);
    TraverseStrategy getStrategy();
}
