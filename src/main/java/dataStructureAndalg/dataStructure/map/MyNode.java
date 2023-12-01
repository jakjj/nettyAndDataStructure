package dataStructureAndalg.dataStructure.map;

public class MyNode<T,V> {

    private T key;
    private V val;

    public MyNode<T,V> next = null;




    public MyNode(T key, V val) {
        this.key = key;
        this.val = val;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "(k: " + key + " v: " + val + ")  ";
    }
}
