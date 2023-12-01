package dataStructureAndalg.dataStructure.map;


public class MyLinkedList<T,V> {

    private MyNode<T,V> head = new MyNode<>(null,null);

    public boolean add(T id, V val){

        MyNode<T, V> node = getNode(id);

        if(node==null){
            node = new MyNode<T,V>(id,val);
        }else{
            node.setVal(val);
            return true;
        }
        MyNode<T,V> item = head;
        while(item.next != null){
            item = item.next;
        }
        item.next = node;
        return true;
    }

    public MyNode<T,V> remove(T id){
        MyNode<T,V> item = head;
        MyNode<T,V> result = null;

        while(item.next != null){
            if(item.next.getKey() == id){
                result = item.next;
                item.next = item.next.next;
            }else{
                item = item.next;
            }
        }
        return result;
    }

    public MyNode<T,V> getNode(T id){
        MyNode<T,V> item = head;
        MyNode<T,V> result = null;

        while(item.next != null){
            if(item.next.getKey() == id){
                result = item.next;
                return result;
            }
            item = item.next;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuffer bf = new StringBuffer(" ");
        MyNode<T,V> item = head;
        while(item.next != null){
            item = item.next;
            bf.append(item.toString());
        }
        return bf.toString();
    }
}
