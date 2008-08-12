package simple;  
  
import javax.swing.*;  
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.ActionCommandExecutor;
import org.springframework.richclient.application.PageComponentContext;

public class CustomerView extends AbstractView {  
  
    /** 
     * Handler for the "New" action. 
     */  
  //  private ActionCommandExecutor newContactExecutor = new NewExecutor();  
  
    @Override  
    protected JComponent createControl() {  
        JPanel panel = new JPanel();  
        return panel;  
    }  
  
    /** 
     * Register the local command executor to be  
     * associated with named commands. This is called by  
     * Spring RCP prior 
     * to making the view visible. 
     */  
    @Override  
    protected void registerLocalCommandExecutors(PageComponentContext context) {  
        context.register("newCommand", new NewExecutor());  
    }  
  
    /** 
     * Private inner class to create a new customer. 
     */  
    private class NewExecutor implements ActionCommandExecutor {  
        @Override  
        public void execute() {  
            JOptionPane.showMessageDialog(null, "new customer...");  
        }  
    }  
      
}  