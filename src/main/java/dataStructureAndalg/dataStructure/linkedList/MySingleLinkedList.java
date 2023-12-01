package dataStructureAndalg.dataStructure.linkedList;

import java.util.LinkedList;

public class MySingleLinkedList {
    private HeroNode node = new HeroNode("head",null,0);

    public void addNodeByOrder(HeroNode source){
        int sourceNo = source.no;

        if(!node.hasNext()){
            node.next = source;
            return;
        }
        HeroNode item = node;
        while (true){
            if(item.hasNext()){
                if(item.next.no <sourceNo){
                    item = item.next;
                    continue;
                }else{
                    HeroNode orign = item.next;
                    item.next = source;
                    source.next = orign;
                    break;
                }
            }
            item.next = source;
            break;
        }
    }


    public void addNode(HeroNode source){
        int sourceNo = source.no;

        if(!node.hasNext()){
            node.next = source;
            return;
        }
        HeroNode item = node;
        while (true){
            if(item.hasNext()){
                item = item.next;
                continue;
            }
            item.next = source;
            break;
        }
    }

    @Override
    public String toString() {
        return "MySingleLinkedList{" +
                "node=" + node +
                '}';
    }

    public static void main(String[] args) {
        MySingleLinkedList mySingleLinkedList = new MySingleLinkedList();
        mySingleLinkedList.addNode(new HeroNode("账单","小单",54));
        mySingleLinkedList.addNode(new HeroNode("王五","小五",5));
        mySingleLinkedList.addNode(new HeroNode("刘柳","小流",16));
        mySingleLinkedList.addNode(new HeroNode("as","as",1));
        mySingleLinkedList.addNode(new HeroNode("dd","dd",106));
        mySingleLinkedList.addNode(new HeroNode("ss","ss",26));

        LinkedList<Object> objects = new LinkedList<>();

//        HeroNode lastestNum = mySingleLinkedList.getLastestNum(1);
//        System.out.println(mySingleLinkedList.relieveNode());
        antiPrint(mySingleLinkedList.node);
    }

    public HeroNode getLastestNum(int key){
        int count = 1;
        HeroNode item1 = this.node;
        HeroNode item2 = null;
        while(item1.hasNext()){
            item1 = item1.next;
            count++;
            if(count == key){
                item2 = this.node;
            }
            if(item2 != null){
                item2 = item2.next;
            }
        }
        if(key == 1){
            return item1;
        }
        return item2;
    }

    public MySingleLinkedList relieveNode(){
        if(!this.node.hasNext()){
            return this;
        }
        HeroNode firstNode = this.node.next;
        HeroNode node = firstNode.next;
        HeroNode result = firstNode;
        HeroNode item = null;
        result.next = null;
        while(node != null){
            item = node;
            if(node.hasNext()){
                node = node.next;
            }else{
                node = null;
            }
            item.next = result;
            result = item;
        }
        MySingleLinkedList mySingleLinkedList = new MySingleLinkedList();
        mySingleLinkedList.addNode(result);
        return mySingleLinkedList;
    }


    public static void antiPrint(HeroNode node){
        if(node.hasNext()){
            antiPrint(node.next);
        }
        System.out.println(node);
    }
}
