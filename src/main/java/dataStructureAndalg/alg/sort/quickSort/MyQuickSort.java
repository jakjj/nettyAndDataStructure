package dataStructureAndalg.alg.sort.quickSort;

import java.util.Random;

public class MyQuickSort {

    public static void main(String[] args) {

        MyQuickSort myQuickSort = new MyQuickSort();
        int[] arr = new int[]{25,36,1,25,41,2,3,14,5,9,85,74,68,45,0};


//        int[] arr = new int[80000];
//        Random random = new Random();
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = random.nextInt(100000);
//        }

        long start = System.currentTimeMillis();
        myQuickSort.quickSort(arr,0,arr.length-1);

        long end = System.currentTimeMillis();
//        System.out.println(end-start);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public void quickSort(int[] arr,int startIndex, int endIndex){

        if (startIndex >= endIndex) {
            return;
        }
        int point = process(arr, startIndex, endIndex);

        quickSort(arr,startIndex,point-1);
        quickSort(arr,point+1,endIndex);
    }

    public int process(int[] arr,int startIndex, int endIndex){
        int point = arr[endIndex];
        int left = startIndex;
        int right = endIndex;
        while(left < right){
            //从左往右找大的
            while(left<right && arr[left]<point){
                left++;
            }
            //从右往左找小的
            while(left<right && arr[right]>=point){
                right--;
            }
            if(left<right){
                swap(arr,left,right);
            }
        }
        arr[endIndex] = arr[left];
        arr[left] = point;
        return left;
    }

    public void swap(int[] arr,int left,int right){
        int i = arr[left];
        arr[left] = arr[right];
        arr[right] = i;
    }
}
