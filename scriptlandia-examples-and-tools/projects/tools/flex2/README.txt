To install, correct paths to "flex.home" and "scriptlandia.tools.home" and then run

>install.ant

Files (flex2-compiler.ant flex2-player.bat) will be copies into ${scriptlandia.tools.home}. Flex home 
has this structure:

${flex.home}
	player
		SAFlashPlayer.exe 
	sdk
		<SDK>

After the installation you can:

- compile flex2 (generate *.swf):

>flex2-compiler.ant test.mxml

- run flex2:

>flex2-player.bat test.swf