package com.cnu2016;

import java.util.HashMap;
import java.util.Map;

/*  Rule: findbugs:HE_INHERITS_EQUALS_USE_HASHCODE
*   Following code has violation ---
*   Voilation:
*   Since, HashCode() method is not overridden, these two instances
    are not in common to the default hashCode implementation(which
    returns the identity hash code, an arbitrary value assigned to
    the object by the VM)
    Therefore, the class is very likely to violate the invariant
    that equal objects must have equal hashcodes.

    Fix: provide a simple hashCode() method
*
*/

class ClassA {
    private long id;
    private int code;

    public ClassA(long id, int code) {
        super();
        this.id = id;
        this.code = code;
    }

    // override equals()
    public boolean equals(Object obj) {
        //null instanceof Object will always return false
        if (!(obj instanceof ClassA))
            return false;
        if (obj == this)
            return true;
        return  this.id == ((ClassA) obj).id &&
                this.code == ((ClassA) obj).code;
    }

    /*  Rule: squid:EmptyStatementUsageCheck
    *   Following code has violation ---
    *
    *   Empty statements, i.e. ;, are usually introduced by mistake
    *
    */
    public void rule2 (){
        System.out.println("Hello, world!");;  //There was double semicolon , i.e. ;;.
        for (int i = 0; i < 3; System.out.println(i), i++); //it is a bad practice to have side-effects outside of the loop body
    }

}

/*
*   Rule: squid:S1699
*
*   Calling an overridable method from a constructor
*
*/

class SeniorClass {
    public SeniorClass(){
        toString(); //may throw NullPointerException if overridden
    }
    public String toString(){
        return "IAmSeniorClass";
    }
}
class JuniorClass extends SeniorClass {
    private String name;
    public JuniorClass(){
        super(); //Automatic call leads to NullPointerException
        name = "JuniorClass";
    }
    public String toString(){
        return name.toUpperCase();
    }
}

/*
*   Rule : squid:S2176
*
*   Class names should not shadow interfaces or superclasses
*
 */

class Foo extends package2.Foo {
    // content of class
}


public class Rules {
    public static void main(String[] args) {

        ClassA test = new ClassA(2L,0);

        Map m = new HashMap();
        m.put(test,"Jeff Smith");
        System.out.println(m.get(new ClassA(2L,0)));    // Output : null

        test.rule2();
    }
}

