// test1.groovy 

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.*
import org.eclipse.swt.layout.RowLayout as Layout

def display = new Display()
def shell = new Shell(display)

shell.layout = new Layout(SWT.VERTICAL)

shell.text = 'Groovy / SWT Test'

def label = new Label(shell, SWT.NONE)
label.text = 'Simple demo of Groovy and SWT'
shell.defaultButton = new Button(shell, SWT.PUSH)
shell.defaultButton.text = '  Push Me  '

shell.pack()
shell.open()

while (!shell.disposed) {
    if (!shell.display.readAndDispatch()) shell.display.sleep()
}

