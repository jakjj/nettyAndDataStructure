package dataStructureAndalg.alg.tree.huffmanTree;

import java.util.HashMap;
import java.util.Map;

/**
 * 赫夫曼树
 *
 */
public class HuffmanTreeNode {
    //权重
    int weight;
    //编码 0；1
    String code;
    Character data;
    HuffmanTreeNode left;
    HuffmanTreeNode right;


    public HuffmanTreeNode() {
    }

    public HuffmanTreeNode(int weight, Character data) {
        this.weight = weight;
        this.data = data;
    }

    public HuffmanTreeNode(Integer weight) {
        this.weight = weight;
    }

    public Map<Character,String> getTable(){

        Map<Character,String> map = new HashMap<>();
        if(code == null){
            code = "";
        }
        if(left != null){

            left.code = code + "0";
        }
        if(right != null){

            right.code = code + "1";
        }

        if(this.data != null){
            map.put(data,code);
            return map;
        }
        Map<Character,String> table = left.getTable();
        Map<Character,String> table1 = right.getTable();
        if(!table.isEmpty()){
            map.putAll(table);
        }
        if(!table1.isEmpty()){
            map.putAll(table1);
        }
        return map;
    }

    @Override
    public String toString() {
        if(code == null){
            code = "";
        }
        if(left != null){

            left.code = code + "0";
        }
        if(right != null){

            right.code = code + "1";
        }
//        return "\n      [weight: " + weight + "\nleft: " + left + " right:" + right;
        boolean isLast = left==null && right == null;
        if(isLast){
            return  "\n weight: " +weight + "\t\tcode: " + code ;
        }
        return ""+ left + right;
    }
}
