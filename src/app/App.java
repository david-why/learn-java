package app;
import java.lang.reflect.Parameter;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * An app that outputs all methods declared in this class. 
 */

public class App {
    public static void main(String[] args) throws Exception {
        String[] noPStrings = {"print", "in", "printf", "println", "main"};
        Method[] methodsApp = App.class.getDeclaredMethods();
        Method[] methodsFunctions = Functions.class.getDeclaredMethods();
        Method[] methods = new Method[methodsApp.length + methodsFunctions.length];
        for (int i = 0; i < methodsApp.length; i++) methods[i] = methodsApp[i];
        for (int i = 0; i < methodsFunctions.length; i++) methods[methodsApp.length + i] = methodsFunctions[i];
        if (methods.length == 0) {
            println("\u001b[31mNo methods defined!\u001b[0m");
            return;
        }
        println("\u001b[32;1mMethods defined: ");
        int i = 0;
        Method[] methods2 = new Method[methods.length];
        for (Method m : methods) {
            if (in(noPStrings, m.getName())) continue;
            i++;
            methods2[i] = m;
            printf("    \u001b[34;1m%d\u001b[39m. \u001b[4m%s\u001b[0m(", i, m.getName());
            Parameter[] params = m.getParameters();
            for (Parameter param : params) {
                printf("\u001b[36;1m%s\u001b[0m \u001b[35m%s\u001b[0m", param.getType().getSimpleName(), param.getName());
                if (param != params[params.length - 1]) print(", ");
            }
            printf(") in class %s\n", in(methodsApp, m)?"App":"Functions");
        }
        printf("\u001b[33;1mPlease choose a method from 1 to %d: \u001b[0m", i);
        Scanner scanner = new Scanner(System.in);
        int sel = scanner.nextInt();
        if (sel < 1 || sel > i) {
            println("Out of range! ");
            scanner.close();
            return;
        }
        Object[] params = new Object[methods2[sel].getParameters().length];
        i = 0;
        if (methods2[sel].getParameters().length != 0) {
            for (Parameter param : methods2[sel].getParameters()) {
                printf("\u001b[33;1mInput parameter \u001b[0m\u001b[35m%s\u001b[33;1m of type \u001b[36m%s\u001b[33m: \u001b[0m", param.getName(), param.getType().getSimpleName());
                Object input;
                switch(param.getType().getSimpleName()) {
                    case "int":
                        input = scanner.nextInt(); break;
                    case "Boolean":
                        input = scanner.nextBoolean(); break;
                    case "short":
                        input = scanner.nextShort(); break;
                    case "byte":
                        input = scanner.nextByte(); break;
                    case "long":
                        input = scanner.nextLong(); break;
                    case "float":
                        input = scanner.nextFloat(); break;
                    case "double":
                        input = scanner.nextDouble(); break;
                    default:
                        input = scanner.nextLine(); break;
                }
                params[i++] = input;
            }
        }
        println("\u001b[32;1mStandard output of function:\n------------------------------\u001b[0m");
        methods2[sel].invoke(new App(), params);
        println("\u001b[32;1m------------------------------\u001b[0m");
        scanner.close();
    }
    
    private static void print(String s) {
        System.out.print(s);
    }

    private static void println(String s) {
        System.out.println(s);
    }

    private static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    private static Boolean in(Object[] list, Object obj) {
        for (Object o : list) {
            if (o == obj) return true;
        }
        return false;
    }

    private static Boolean passScore(int pass, int score) {
        if (pass < 0 || 0 > score) {
            println("Both pass and score cannot be negative!");
            return false;
        }
        if (score >= pass) {
            println("You passed! Hooray!");
            return true;
        } else {
            println("You didn't pass. Try again next time!");
            return false;
        }
    }
}
