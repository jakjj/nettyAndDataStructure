package dataStructureAndalg.dataStructure;

public class MyQueue {
    private final int maxSize;
    private int front=0;
    private int sum=0;

    private final int[]arr;

    public MyQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }


    public int addQueue(int i){
        if(sum >= maxSize){
            throw new ArrayIndexOutOfBoundsException();
        }
        int index = (front+sum) % maxSize;
        arr[index] = i;
        sum++;
        return i;
    }

    public boolean isEmpty(){
        return sum == 0;
    }

    public boolean isMax(){
        return sum == maxSize;
    }

    public int getQueue(){
        if(isEmpty()){
            throw new ArrayIndexOutOfBoundsException();
        }
        int result = arr[front];
        arr[front] = 0;
        front = (front+1)%maxSize;
        sum--;
        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for (int i : arr) {
            sb.append(i + ",");
        }
        sb.setLength(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
}
