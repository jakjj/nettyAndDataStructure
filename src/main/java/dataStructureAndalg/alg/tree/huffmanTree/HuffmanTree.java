package dataStructureAndalg.alg.tree.huffmanTree;

import java.util.*;

public class HuffmanTree {

    HuffmanTreeNode root;

    public Map<Character,String> getTables(){
        return root.getTable();
    }

    public static HuffmanTree createTree(String str){
        HuffmanTreeNode root = null;
        //获取权重
        char[] chars = str.toCharArray();
        Map<Character,Integer> charDatas = new HashMap<>();
        for(char ch : chars){
            if(charDatas.containsKey(ch)){
                charDatas.put(ch,charDatas.get(ch) + 1);
            }else{
                charDatas.put(ch,1);
            }
        }
        System.out.println("str2ch[]: " + charDatas);
        List<HuffmanTreeNode> nodes = new ArrayList<>();
        //转化结点数组
        for(Map.Entry<Character,Integer> item : charDatas.entrySet()){
            nodes.add(new HuffmanTreeNode(item.getValue(),item.getKey()));
        }

        int times = nodes.size() - 1;
        //循环生成树
        for(int i =0;i<times;i++){
            Collections.sort(nodes,(o1,o2)->{
                return o2.weight-o1.weight;
            });
            //取最小两个，合成新的
            HuffmanTreeNode last1 = nodes.remove(nodes.size()-1);
            HuffmanTreeNode last2 = nodes.remove(nodes.size()-1);
            //root赋值
            HuffmanTreeNode huffmanTreeNode = new HuffmanTreeNode(last1.weight + last2.weight);
            huffmanTreeNode.left = last1;
            huffmanTreeNode.right = last2;
            root = huffmanTreeNode;
            //添加新的元素，循环
            nodes.add(huffmanTreeNode);
        }
        return new HuffmanTree(root);
    }

    public HuffmanTree() {
    }

    public HuffmanTree(HuffmanTreeNode root) {
        this.root = root;
    }


    @Override
    public String toString() {
        return "huffmanTree: " + root;
    }
}
