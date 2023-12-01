package dataStructureAndalg.dataStructure.myStack;

public class MyStack {

    private int maxSize;
    private int index = -1;
    private int[] arr;

    public MyStack(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }
    public boolean isFull(){
        return index >= maxSize-1;
    }
    public boolean isEmpty(){
        return index == -1;
    }

    public void push(int i){
        if(isFull()){
            throw new ArrayIndexOutOfBoundsException();
        }
        arr[index+1] = i;
        index++;
    }
    public int pop(){
        if(isEmpty()){
            throw new ArrayIndexOutOfBoundsException();
        }
        int result = arr[index];
        arr[index] = -1;
        index--;
        return result;
    }

}
