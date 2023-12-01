package dataStructureAndalg.alg.sort.eightQueen;

public class EightQUeen {

    private int count = 0;

    public static void main(String[] args) {
        EightQUeen eightQUeen = new EightQUeen();
        int[] first = new int[8];
        eightQUeen.cycleLine(1,first);
        System.out.println("总计：" + eightQUeen.getCount());
    }

    public int getCount() {
        return count;
    }

    /**
     *
     * @param columnNum 列序号 1-8
     * @param nums 历史序号
     * @return
     */
    public void cycleLine(int columnNum,int[] nums){

        for (int i = 1;i<=8;i++){
            if(numCheck(columnNum,i,nums)){
                //终点，输出
                if(columnNum == 8){
                    count++;
                    nums[7] = i;
                    printResul(nums);
                    return;
                }
                //成功了，维护历史记录，发起新的递归
                //拼入行号，即y
                nums[columnNum-1] = i;
                cycleLine(columnNum+1, nums);
            }
        }
//        nums =null;
    }

    /**
     * 校验某个列序号是否可用
     * @param checkNum 要校验的列序号
     * @param nums 历史记录
     * @return
     */
    public boolean numCheck(int columnNum, int checkNum, int[] nums){

        for (int i = 0; i < columnNum-1; i++) {
            if(nums[i] == 0){
                return true;
            }
            int lineNumi = nums[i];
            //行判断
            if(lineNumi == checkNum){
                return false;
            }
            //判断斜率绝对值是否为1
            if(Math.abs(lineNumi - checkNum) == Math.abs(i+1-columnNum)){
                return false;
            }

            //斜边判断 y==x+c; c== lineNumi - (i+1)
            if(columnNum + (lineNumi - (i+1)) == checkNum){
                return false;
            }
            //斜边判断 y==-x+c;c== lineNumi + (i+1)
            if((lineNumi + (i+1))-columnNum == checkNum){
                return false;
            }
        }
        return true;
    }

    public void printResul(int[] nums){
        System.out.print("一个可行的结果：");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(" "+ (i+1) + "," + nums[i]);
        }
        System.out.println();
    }
}
