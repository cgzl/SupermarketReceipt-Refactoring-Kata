package dojo.supermarket;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.JunitReporter;

/**
 * Configure the reporter used by Approval Tests.
 * Documentation: https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/Reporters.md
 */
public class PackageSettings {
    public static ApprovalFailureReporter UseReporter         = JunitReporter.INSTANCE;
}

/*
 * Utility classes should not have public constructors
 * To prevent the class from being instantiated, you should 
 * define a non-public constructor. This will prevent the compiler 
 * from implicitly generating a public parameterless constructor.
 */
