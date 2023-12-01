package dataStructureAndalg.dataStructure.bothway;

import java.util.Iterator;
import java.util.LinkedList;

public class BothwayLinkedList {

    public XHereNode head = new XHereNode(0,"head");

    public void addNode(XHereNode source){
        if(!head.hasNext()){
            head.next = source;
        }
        XHereNode node = head;
        while(node.hasNext()){
            node = node.next;
        }
        node.next = source;
    }

    public void removeNode(){

    }

    public static void main(String[] args) {
        LinkedList<String> strings = new LinkedList<>();
        strings.add("a");
        strings.add("b");
//        strings.add("c");
//        strings.add("d");
//        strings.add("e");


        Iterator<String> iterator = strings.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            if("b".equals(next)){
                iterator.remove();
            }
        }
    }
}
