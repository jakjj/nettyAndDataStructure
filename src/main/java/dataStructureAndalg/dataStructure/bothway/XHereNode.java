package dataStructureAndalg.dataStructure.bothway;

public class XHereNode {
    public int no;
    public String name;
    public XHereNode pre;
    public XHereNode next;


    public XHereNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public boolean hasNext(){
        return this.next != null;
    }

    public boolean hasPre(){
        return this.pre != null;
    }

    @Override
    public String toString() {
        return "XHereNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
