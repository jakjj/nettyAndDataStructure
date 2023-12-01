package dataStructureAndalg.dataStructure.nbl;

import java.util.Scanner;
import java.util.Stack;

//逆波兰表达式计算
public class NBLCalc {

    private Stack<Double> numStack = new Stack<>();
    private Stack<String> operatorStack = new Stack<>();
    private final String operatorStr = "+-*/^";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入逆波兰表达式");
        String expression = scanner.nextLine();
        NBLCalc nblCalc = new NBLCalc();
        Double calc = nblCalc.calc(expression);
        System.out.println("计算结果为： " +calc);
    }

    //3 2 5 3 * + * 2 5 * +

    public Double calc(String expression){
        String[] s = expression.split(" ");
        for (int i=0;i<s.length;i++) {
            String itemStr = s[i];
            if(isNumber(itemStr)){
                numStack.push(Double.parseDouble(itemStr));
            }
            if(isOperator(itemStr)){
                calcSimple(itemStr);
                System.out.println(numStack);
            }
        }
        return numStack.pop();
    }

    public boolean isNumber(String str){
        if(str==null){
            return false;
        }
        try {
            double i = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isOperator(String str){
        if(str==null){
            return false;
        }
        if(operatorStr.contains(str)){
            return true;
        }
        return false;
    }

    public void calcSimple(String operatorStr){
        Double pop2 = numStack.pop();
        Double pop1 = numStack.pop();
        switch (operatorStr){
            case "+":{
                numStack.push(pop1 + pop2);
                break;
            }
            case "-":{
                numStack.push(pop1 - pop2);
                break;
            }
            case "*":{
                numStack.push(pop1 * pop2);
                break;
            }
            case "/":{
                numStack.push(pop1 / pop2);
                break;
            }
            case "^":{
                numStack.push(Math.pow(pop1,pop2));
                break;
            }
        }

    }
}
