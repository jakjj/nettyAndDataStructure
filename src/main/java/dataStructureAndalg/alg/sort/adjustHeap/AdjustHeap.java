package dataStructureAndalg.alg.sort.adjustHeap;

import java.util.Random;

public class AdjustHeap {

    public static void main(String[] args) {
        int[] arr = new int[]{25,36,1,25,41,2,3,14,5,9,85,74,68,45,0};
//        int[] arr = new int[80000];
//        Random random = new Random();
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = random.nextInt(100000);
//        }
        AdjustHeap adjustHeap = new AdjustHeap();

        long start = System.currentTimeMillis();

        adjustHeap.sort(arr);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public void sort(int[] arr){
        //每做一次大顶堆，交换取得的最大值到数组尾部，length-1再做大顶堆，最终取得排序后的数组
        for (int len = arr.length; len >= 2; len--) {
            int i = len/2 -1;
            adjustHeap(arr,i,len);
        }
    }

    /**
     * 数组做大顶堆
     * @param arr
     * @param i 数组最后一个非叶子节点位置
     * @param length 当前处理的长度
     */
    public void adjustHeap(int[] arr, int i, int length){
        //nodeIndex--,找上一个非叶子节点
        for(int nodeIndex = i;nodeIndex>=0;nodeIndex--){
            int temp = arr[nodeIndex];
            int tempIndex = nodeIndex;
            //获取当前非叶子节点的左子节点（必有
            int k = 2*tempIndex+1;
            //循环向下,依次梳理子树
            for(;k<length;k=2*k+1){
                //比较左右子节点，取最大值
                if(k+1 < length && arr[k+1] > arr[k]){
                    //指向右节点
                   k++;
                }
                //判断交换，做成大顶堆
                if(arr[k] > temp){
                    //发生交换，则向子树中寻找temp的具体存放位置
                    arr[tempIndex] = arr[k];
                    tempIndex = k;
                }else{
                    break;
                }
            }
            //temp最终位置
            arr[tempIndex] = temp;
        }
        swap(arr,length-1,0);
    }

    public void swap(int[] arr, int i,int k){
        int temp = arr[i];
        arr[i] = arr[k];
        arr[k] = temp;
    }
}
