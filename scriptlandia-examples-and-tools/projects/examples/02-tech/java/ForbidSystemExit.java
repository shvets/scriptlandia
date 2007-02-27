//

import java.security.Permission;

class ExitTrappedException extends SecurityException {}

class ExitProtector {
  public void forbidSystemExitCall() {
    final SecurityManager securityManager = new SecurityManager() {
      public void checkPermission( Permission permission ) {
        if(permission.getName().startsWith("exitVM")) {
          throw new ExitTrappedException() ;
        }
      }
    };

    System.setSecurityManager( securityManager ) ;
  }

  public void enableSystemExitCall() {
    System.setSecurityManager( null ) ;
  }

}


class ExitLibrary  { 

  public void doSomethingAndExit() {
    System.out.println("executing doSomethingAndExit() before exit.");

    System.exit(0);
  }

}

public class ForbidSystemExit {

  public static void main(String[] args) {
    System.out.println("Before System.exit().");

    ExitProtector exitProtector = new ExitProtector();

    try {
      exitProtector.forbidSystemExitCall() ;

      ExitLibrary exitLibrary = new ExitLibrary();

      exitLibrary.doSomethingAndExit();
    }
    catch( ExitTrappedException e ) {
      System.out.println("Caught System.exit().");
    }
    finally {
      exitProtector.enableSystemExitCall();
    }

    System.out.println("After System.exit().");
  }

}
