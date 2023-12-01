package dataStructureAndalg.alg.tree.huffmanTree;

import java.util.HashMap;
import java.util.Map;

public class DataTar {

    public static void main(String[] args) {
        String str = "i like the code for what? Can u tell me?";
        DataTar dataTar = new DataTar();
        dataTar.zip(str);
    }

    public String zip(String str){
        char[] chars = str.toCharArray();
        HuffmanTree tree = HuffmanTree.createTree(str);
        Map<Character,String> codedTable = tree.getTables();
        System.out.println("ch[]2huffman2codeTable:" + codedTable);

        String encode = Encode(chars, codedTable);
        System.out.println("encode: " + encode);
        String decod = Decod(encode, codedTable);
        System.out.println("decod: " + decod);

        return encode;
    }

    private Map<Character,String> getCodedTable(Map<Character,Integer> map,Map<String,Integer> tables){
        Map<Character,String> coded = new HashMap<>();
        map.forEach((k,v)->{
            tables.forEach((k1,v1)->{
                if(v.equals(v1) && !coded.containsKey(k)){
                    coded.put(k,k1);
                    tables.put(k1,null);
                }
            });
        });
        return coded;
    }

    /**
     * 编码
     * @param chars
     * @param codeTable
     * @return
     */
    private String Encode(char[] chars, Map<Character,String> codeTable){
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < chars.length; i++) {
            if(' ' == chars[i]){

            }
            if(codeTable.containsKey(chars[i])){
                sb.append(codeTable.get(chars[i]));
            }
        }
        return sb.toString();
    }

    /**
     * 解码
     * @param str
     * @param codeTable
     * @return
     */
    public String Decod(String str, Map<Character,String> codeTable){
        StringBuffer sb = new StringBuffer("");
        while(true){
            for (Character character : codeTable.keySet()) {
                String msg = codeTable.get(character);
                if(str.startsWith(msg)){
                    str= str.replaceFirst(msg,"");
                    sb.append(character);
                }
            }
            if("".equals(str)){
                break;
            }
        }
        return sb.toString();
    }


}
