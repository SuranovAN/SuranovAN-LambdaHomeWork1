package Lambda.Calculator;

public class Main {
    public static void main(String[] args) {
        Calculator calc = Calculator.instance.get();
        int a = calc.plus.apply(1, 2);
        int b = calc.minus.apply(1,1);
        //на ноль делить нельзя, я сделал проверку по y  если он равен 0 то и ответ пусть будет 0
        int c = calc.devide.apply(a, b);
        calc.println.accept(c);
        calc.println.accept(a);

    }
}
