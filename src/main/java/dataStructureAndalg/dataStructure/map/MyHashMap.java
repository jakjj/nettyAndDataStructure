package dataStructureAndalg.dataStructure.map;


public class MyHashMap<T,V> {

    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("test",56);
        map.put("asd",128);
        map.put("cdcd",32);
        System.out.println(map.toString());
        map.put("test",5487);
        System.out.println(map.toString());
        System.out.println("test---" + map.get("test"));
        map.remove("test");
        System.out.println(map.toString());
    }

    private MyLinkedList<T,V>[]arr = null;

    private int size = 8;

    public MyHashMap() {

        this.arr = (MyLinkedList<T,V>[])new MyLinkedList[size];
        initArr();
    }
    public MyHashMap(int size) {
        this.size = size;
        this.arr = (MyLinkedList<T,V>[])new MyLinkedList[size];
        initArr();
    }

    public V get(T id){
        int hashCode = hashFun(id);
        MyLinkedList<T, V> tvMyLinkedList = arr[hashCode];
        return tvMyLinkedList.getNode(id).getVal();
    }

    public MyNode<T,V> remove(T id){
        int hashCode = hashFun(id);
        MyLinkedList<T, V> tvMyLinkedList = arr[hashCode];
        return tvMyLinkedList.remove(id);
    }

    public boolean put(T id,V val){
        int hashCode = hashFun(id);
        MyLinkedList<T, V> tvMyLinkedList = arr[hashCode];
        return tvMyLinkedList.add(id,val);
    }

    public boolean containsKey(T id){
        int hashCode = hashFun(id);
        MyLinkedList<T, V> tvMyLinkedList = arr[hashCode];
        return tvMyLinkedList != null;
    }


    @Override
    public String toString() {
        StringBuffer bf = new StringBuffer("hashTable: \n");
        for (int i = 0; i < arr.length; i++) {
            MyLinkedList<T, V> tvMyLinkedList = arr[i];
            bf.append("index: " + i + "\n");
            bf.append(tvMyLinkedList.toString()+ "\n");
        }
        return bf.toString();
    }

    private void initArr(){
        for (int i = 0; i < this.arr.length; i++) {
            this.arr[i] = new MyLinkedList<>();
        }
    }

    private int hashFun(T id){
        int i = id.toString().hashCode();
        return i%size;
    }
}
