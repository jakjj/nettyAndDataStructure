package dataStructureAndalg.alg.sort.selectSort;

import java.util.Random;

public class SherSort {

    public static void main(String[] args) {

        int[] arr = new int[80000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100000);
        }

        long start = System.currentTimeMillis();

//        int[] arr = new int[]{25,36,1,25,41,2,3,14,5,9,85,74,68,45,0};
        sort(arr);

        long end = System.currentTimeMillis();
        System.out.println(end-start);

//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i] + " ");
//        }

    }

    public static void sort(int[] arr){
        int length = arr.length;
        int cycle = length/2;
        while(true){
            if(cycle < 1){
                return;
            }
            for(int i=cycle;i<arr.length;i++){
                int index = i;
                int i1 = arr[i];
                while(index-cycle>=0 && arr[index-cycle]>i1){
                    arr[index] = arr[index-cycle];
                    index -= cycle;
                }
                arr[index] = i1;
            }
            cycle =(int) Math.floor(cycle/2);
        }

    }
}
