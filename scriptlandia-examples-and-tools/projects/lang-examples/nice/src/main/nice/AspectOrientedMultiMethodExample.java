// Listing 5

package com.enterprise;

public class Email implements Sendable {
   private ?String body;
   private ?String toAddress;
   private String fromAddress;
   private String subject;
   
   getBody() = body;
   getToAddress() = toAddress;   
   
   public String getFromAddress() = fromAddress;   
   public String getSubject() = subject;   
   
   public void setBody(String string) = body = string;  
   public void setToAddress(String string) = toAddress = string;         
   public void setFromAddress(String string) = fromAddress = string;      
   public void setSubject(String string) = subject = string;
}

public class Fax implements Sendable {
   private String body;
   private String toAddress;
   
   getBody() = body;
   getToAddress() = toAddress;   
   
   public void setBody(String string) = body = string;      
   public void setToAddress(String string) = toAddress = string;
}  

// Listing 6

package com.enterprise;

public interface Sendable {
   ?String getBody();
   ?String getToAddress();
}

// Listing 7

package com.acme.validate;

public interface Validatable {
   boolean validateAddress();
}

// Listing 13

package com.acme.validate;
import com.enterprise;

interface com.enterprise.Sendable implements Validatable;

validateAddress(Email email) = 
   email.getToAddress != null;

validateAddress(Fax fax){
   let address = fax.getToAddress;
   return 
      (address != null && address.length >= 11);
}
