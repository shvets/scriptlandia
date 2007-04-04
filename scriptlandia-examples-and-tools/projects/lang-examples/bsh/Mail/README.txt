This example shows how to convert e-mail into FTP-like transport. You should have
"mail.jar" and "activation.jar" in your "lib" folder.

Say, you have the big file to be send. You divide it on parts,
appropriate for email server (1-2 Mb) and put them into IN folder. Yo have to do 
it manually with the help of some external tool. 

By starting "send.bsh" all files from "OUT" folder will be send as separate e-mails.

By starting "fetch.bsh" these files will be automatically retrieved from the destination
mailbox and saved in "IN" folder.

Before running this example, you have to modify these files:

  - mail.send.properties;
  - mail.fetch.properties.