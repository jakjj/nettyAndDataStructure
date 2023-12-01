import dataStructureAndalg.dataStructure.MyQueue;

import java.util.*;

public class Demo{

    public static void main(String[] args) {

        MyQueue myQueue = new MyQueue(15);
        Random random = new Random();

        int count = 0;

        while(true){

            System.out.println("------------------");
            int randomI = random.nextInt(1)+1;
            int i2 = random.nextInt(15)+1;
            if((randomI%2==0 || myQueue.isEmpty()) && !myQueue.isMax()){
                for(int i = 0;i<i2;i++){
                    if(!myQueue.isMax()){
                        int i1 = random.nextInt(50);
                        System.out.println("append: " + i1);
                        myQueue.addQueue(i1);
                    }
                }
            }else{
                for(int i = 0;i<i2;i++){
                    if(!myQueue.isEmpty()){
                        System.out.println("pop: " + myQueue.getQueue());
                    }
                }
            }
            System.out.println(myQueue);

            if(++count > 20){
                return;
            }
        }
    }
}