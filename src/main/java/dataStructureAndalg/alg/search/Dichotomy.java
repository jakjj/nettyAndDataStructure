package dataStructureAndalg.alg.search;

public class Dichotomy {

    public static void main(String[] args) {
        int[] arr = new int[]{2,5,6,8,9,15,45,78,83,86,87,102};
        System.out.println(search(arr,78,0,arr.length));
    }

    public static int search(int[] arr, int source, int left, int right){
        if(right < left){
            return -1;
        }
        int length = right - left;
        int point = (int)length/2 + left;
        if(arr[point] > source){
            return search(arr,source,left,point);
        }
        if(arr[point] < source){
            return search(arr,source,point+1,right);
        }
        if(arr[point] == source){
            return point;
        }
        return -1;
    }
}
