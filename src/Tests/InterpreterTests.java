package Tests;

import Tran.*;
import AST.TranNode;
import Interpreter.Interpreter;
import org.junit.jupiter.api.Test;

public class InterpreterTests {
    @Test
    public void SimpleAdd() {
        String program = """
                class SimpleAdd
                    
                    shared start()
                        number x
                        number y
                        number z
                        
                        x = 6
                        y = 6
                        z = x + y 
                """;
        run(program);
    }

    @Test
    public void SimpleAddInstantiate() {
        String program = """
                class SimpleAdd
                    number x
                    number y
                    
                    construct()
                        x = 6
                        y = 6
                        
                    add()
                        number z
                        z = x + y 
                        
                    shared start()
                        SimpleAdd t
                        t = new SimpleAdd()
                        t.add()
                        
                """;
        run(program);
    }

    @Test
    public void SimpleAddInstantiateAndPrint() {
        String program = """
                class SimpleAdd
                    number x
                    number y
                    
                    construct()
                        x = 6
                        y = 6
                        
                    add()
                        number z
                        z = x + y 
                        console.write(z)
                        
                    shared start()
                        SimpleAdd t
                        t = new SimpleAdd()
                        t.add()
                        
                """;
        run(program);
    }

    @Test
    public void Loop1() {
        String program = "class LoopOne\n" +
                         "    shared start()\n" +
                         "        boolean keepGoing\n" +
                         "        number n\n" +
                         "        n = 0\n" +
                         "        keepGoing = true\n" +
                         "        loop keepGoing\n" +
                         "            if n >= 15\n" +
                         "                keepGoing = boolean.false\n" +
                         "            else\n" +
                         "                n = n + 1\n" +
                         "                console.write(n)\n";
        run(program);
    }

    @Test
    public void student() {
        String program = "class student\n" +
                "    number gradea\n" +
                "    number gradeb\n" +
                "    number gradec\n" +
                "    string firstname\n" +
                "    string lastname\n" +
                "    \n" +
                "    construct (string fname, string lname, number ga, number gb, number gc)\n" +
                "        firstname = fname\n" +
                "        lastname = lname\n" +
                "        gradea = ga\n" +
                "        gradeb = gb\n" +
                "        gradec = gc\n" +
                "    \n" +
                "    getAverage() : number avg \n" +
                "        avg = (gradea + gradeb + gradec)/3\n" +
                "    \n" +
                "    print() \n" +
                "        console.write(firstname, \" \", lastname, \" \", getAverage())\n" +
                "    \n" +
                "    shared start()\n" +
                "        student sa\n" +
                "        student sb\n" +
                "        student sc\n" +
                "        sa = new student(\"michael\",\"phipps\",100,99,98)\n" +
                "        sb = new student(\"tom\",\"johnson\",80,75,83)\n" +
                "        sc = new student(\"bart\",\"simpson\",30,25,33)\n" +
                "        sa.print()\n" +
                "        sb.print()\n" +
                "        sc.print()\n";
        run(program);
    }

    private static void run(String program) {
        var l  = new Lexer(program);
        try {
            var tokens = l.Lex();
            var tran = new TranNode();
            var p = new Parser(tran,tokens);
            p.Tran();
            System.out.println(tran.toString());
            var i = new Interpreter(tran);
            i.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
