package org.soal.findbugs.tutorial;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.FieldDescriptor;

/**
 * Report a bug if a method called "foo" or "bar" is called.
 */
public class DemoDetector6  extends OpcodeStackDetector {

    private BugReporter reporter;

    public DemoDetector6(BugReporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public void sawOpcode(int seen) {
        if (seen == GETSTATIC){

            try {
                FieldDescriptor operand = getFieldDescriptorOperand();
                ClassDescriptor classDescriptor = operand.getClassDescriptor();
                if ("java/lang/System".equals(classDescriptor.getClassName()) &&
                        ("err".equals(operand.getName())||"out".equals(operand.getName()))) {
                    reporter.reportBug(
                            new BugInstance("DEMO_BUG_6", Priorities.HIGH_PRIORITY)
                                    .addClass(this)
                                    .addMethod(this)
                                    .addSourceLine(this)
                    );
                }
            } catch (Exception e) {
                //ignore
            }
        }
    }
}
