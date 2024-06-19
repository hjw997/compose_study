package com.example.chapter02_modifier.kotlin_implicit_receiver;

public class ATest {

}

class OutClass {
    int outInt = 1;
    int commonInt = 3;

    class InnerClass {
        int innerInt = 2;
        int commonInt = 4;

        public void innerMethod() {
            System.out.println(innerInt);
            System.out.println(this.innerInt);
            System.out.println(outInt);

            System.out.println(this.innerInt);
            System.out.println(OutClass.this.outInt);

            System.out.println(commonInt);
            System.out.println(OutClass.this.commonInt);
        }
    }
}
