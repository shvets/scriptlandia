//

import org.codehaus.groovy.scriptom.ActiveXProxy

def outlook = new ActiveXProxy("Outlook.Application")
def namespace = outlook.GetNamespace("MAPI") // There is only "MAPI"

// 6 == Inbox; other values in Outlook's VBA documentation
def inbox = namespace.GetDefaultFolder(6)
def mails = inbox.Items

println "Elements in your Inbox: " + mails.Count.value

for (i in 1..mails.Count.value) {
	def mail = mails.Item(i)
	println i + ": " + mail.Subject.value + " (" + mail.Size.value + " bytes)"
}
