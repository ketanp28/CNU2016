import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class SimpleTransformer implements ClassFileTransformer {

    public SimpleTransformer() {
        super();
    }


    public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {


        if (className.contains("java") || className.contains("sun")) {
            return bytes;
        }
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(bytes));
            for (CtBehavior ctBehavior : ctClass.getDeclaredBehaviors()) {
                // output parameters
                ctBehavior.insertAfter("{System.out.println($_);}");
                ctBehavior.insertAfter("{System.out.println(\"Exiting " + ctBehavior.getName() + "\");}");
                // input parameters
                ctBehavior.insertBefore("{for (int i=0; i < $args.length; i++) {System.out.println($args[i]);}}");
                ctBehavior.insertBefore("{System.out.println(\"Entering " + ctBehavior.getName() + "\");}");
            }
            bytes = ctClass.toBytecode();
            ctClass.detach();
            return bytes;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
//                }
        return null;
    }
}


