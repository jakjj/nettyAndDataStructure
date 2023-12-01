package dataStructureAndalg.dataStructure.linkedList;

public class HeroNode {

    public String name;
    public String nickName;
    public int no;
    public HeroNode next;

//    public void addNext(HeroNode source){
//        if(next == null){
//            next = source;
//        }
//        HeroNode orign = this.next;
//        source.next = orign;
//        this.next = source;
//    }

    public boolean hasNext(){
        return this.next != null;
    }

    public HeroNode(String name, String nickName, int no) {
        this.name = name;
        this.nickName = nickName;
        this.no = no;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", no=" + no +
//                ", next=" + next +
                '}';
    }
}
