package dataStructureAndalg.alg;

import java.util.*;

public class JosephusProblem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入人数：n 与间隔数: k");
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        Map<Integer,Integer> map = new HashMap<>();
        JosephusPrint(n,k,1,1,map);

        List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<Integer,Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                //有小到大
                return o1.getKey()-o2.getKey();
            }
        });
        for (Map.Entry<Integer, Integer> e: list) {
            System.out.println(e.getKey()+":"+e.getValue());
        }

    }

    /**
     *
     * @param n 总人数
     * @param m 循环间隔
     * @param outCount 出局时总人数（含自己）
     * @param outNum 出局时序号
     */
    public static void JosephusPrint(int n, int m, int outCount, int outNum, Map<Integer,Integer> map){
        //f(n) = k;f(1) = 1
        //f(n) = ((f(n-1) + m -1)%n + 1
        //初始化条件，curnNum变化为各轮次中的序号
        int curnNum = outNum;
        //从上一轮开始，循环向上
        for(int i = outCount+1;i<=n;i++){
            //根据关系式，求得上一轮序号
            int preNum = (curnNum + m-1)%i +1;
            //当前轮中序号为1，则上一轮中被剔除的是序号为 preNum-1的玩家
            if(curnNum==1){
                int cur = preNum-1==0?i:preNum - 1;
                //递归获取该玩家的首轮序号
                JosephusPrint(n,m,i,cur,map);
            }
            //记录序号
            curnNum = preNum;
        }
        map.put(n-outCount+1,curnNum);
        //循环结束，输出序号，每轮剔除一个，所以是线性的，不会遗漏
//        System.out.println(outCount + " ---- " + curnNum);
    }
}
