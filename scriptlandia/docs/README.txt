    Scriptlandia

Description

Scriptlandia is the Launching Pad for running various scripts (Ant, Maven, BeanShell, JavaScript,
Groovy, JRuby etc.) within Java Virtual Machine Platform. The idea is to build an environment
that facilitates not only script execution, but also gives the ability for the scripts to be 
aware of each other. Also, it's the effort to use classloader that can download 
dependencies "on-the-fly."

Often you have to do some easy, small task, which requires simple calculation or text/data
transformation. You have two choices here: to write the batch/shell script or the program in some
programming language (Java language, for example). Both approaches have drawbacks.

In the first case you are restricted with the (batch/shell) language that cannot express your
intentions in clear way. The second choice is more appropriate for the (Java) programmers,
but still, you have to think about such things as how to compile and how to run
(Java) code. Even more, if you want to slightly modify the original code, you need to
remember, where sources and classes are. The situation is a little bit
overcomplicated.

The solution is to use dynamic (or scripting) languages written for Java Virtual Machine (JVM).
Java as a platform is mature enough now and has a lot of convenient and contemporary APIs. 
The scripting language engine for JVM works as simplified front-end to the set of Java APIs.

Ant tool by itself also can be used as the scripting language. Sometimes it is
easier to use XML syntax that exists for Ant scripts and ready to use Ant tasks,
than write them from the scratch. We will go further, making Ant scripts
"first-class citizens" in the world of scripting languages. To do so, we use ".ant"
extension as standard extension for scripts, written in Ant syntax: antlets
(former "build.xml" file).

We can do exactly the same thing for maven scripts. Inside our Scriptlandia world
maven-lets will maintain correct stack of dependent libraries, required by given
user script. Maven script will have convenient ".maven" extension (former "pom.xml" file).


Use cases


1. Prototyping/Home work automation

You want to provide simple implementation of some idea and you don't want to
create build/configuration/helper files.

2. Code for customizations

You are developing the new software platform and future features are unknown
at the current time. You can have customer-specific customization as scripting
code, leaving main core intact. Later on, if you decide that code is
production-ready, it can be moved into the core. You have to move only general
ideas from your scripts into the core.

3. Reverse Engineering

You are trying to understand how 3-rd party library works. Unfortunately, 
you don't have the sources. You can decompile the bytecode and analyze it visually.
If you have the question about specific chunk of code (say, what does this static
code do?), you can copy this code as the Beanshell script and run-test it immediately.

4. Templates generation

Groovy has an excellent feature such as native support for various markup
languages (html, xml etc.). You can use it to generate xml/html files for tests.
Jelly and velocity also can solve this task. Plus add Velocity and Freemarker to this set.

5. Using script as configuration file

In simplest case you can use *.properties or *.ini file to express your configuration needs.
In more complex scenarios you will probably have *.xml file (in order to express tree-like
relationships between configuration parameters).

You can also express your configuration as a sort of external program, written in scripting
language. This is most dynamic way and it is already used in Jini Framework. By using 
higher level language in your configuration, you can express your needs in simpler way.

6. Fill it out by yourself.


    Architecture


1. On the dedicated server we have Central Repository (sponsored, non-profit, for everybody
in the community/ IT industry) of java components represented in binary format. It could 
be a separate java library, some convenient tool or even GUI program.

2. Each component is provided with the group name, artifact name and the version.

3. Each component has binary artifact and could also contain sources, javadocs or other
optional artifacts.

4. Each component describes dependencies to other components in the form of Dependencies File.
As the result, we have Dependencies Tree (or Transitive Dependencies).

5. Small client program connects to the remote Central Repository and downloads required 
components to the client's computer. This program also could be used as "smart" start-up
program for launching other programs. As the result, it could be smart class loader
that loads required dependencies "on-the-fly".

6. All downloaded components are stored in the Local Repository - it is the mirror of 
Central Repository and it contains only required components with their dependencies.

7. If somebody wants to introduce new program, s(he) describes it in the form of dependencies,
then s(he) writes the code. As the result, it is required to distribute new code only - 
all dependencies will be downloaded later and only "on-demand" - when it is really required.

8. "Smart" start-up program reads Dependencies File, installs all the required dependencies
and then starts the original program.

This is real separation of new code from related dependencies. If your application 
is, say, dependent on "jdom" library, your distribution does not have to include this file. 
Instead, you provide dependencies for the project and they will be downloaded automatically.


    History


Initially, it was only Ant. I start working with it and found plenty of benefits, organizing 
my work around Ant.

After some period of time I start experimenting with "script" task. It helps to "fine-tune"
Ant scripts. First dynamic language for "script" task was "javascript". Few months later it 
was replaced with "beanshell".

Ant tool in conjunction with some dynamic language can do almost everything. But one thing 
is missing here: the notion of Repository. In order to have it, you have to switch to Maven
or use Ant tasks for Maven. In both scenarios you are tied to Maven.

Another interesting thing to discuss is how to start Java code. Instead of writing pretty 
complex *.bat or *.sh scripts, it is possible to move this hard-to-maintain code into java scope. 
It requires flexible classloading. Some implementations to facilitate this process already 
exist. For example, I could name few of them: 

- Ant launcher - used by Ant (http://ant.apache.org);
- Forehead framework - used by Maven 1 (http://forehead.sourceforge.net);
- Classworlds framework - used by Maven 2 (http://classworlds.codehaus.org).


Notes about current implementation

In my experiments with "smart" strart-up implementations I started with my own class loader based 
on Ant launcher. Later on it was replaced with forehead and then with classworlds. Currently
I have the implementation, based on classworlds, that can "understand" maven transitive dependencies
(from "pom.xml").

Currently I use Maven2 mechanizm for describing transitive dependencies as well as 
maven delivery mechanizm. 

"Smart" start-up program is implemented as the modification of classloader from Classworlds project.
For resolving/downloading dependencies, "bootstrap-mini" subproject from maven 2 source is used.

Maven Source Repository is located here:

svn checkout http://svn.apache.org/repos/asf/maven/components/trunk/ maven-site

It was an effort to use pomstrap classloader (http://pomstrap.prefetch.com/en/) to read/load
dependencies. It works only for small cases. It's not ready to use.


Links:

http://www.robert-tolksdorf.de/vmlanguages.html 
https://scripting.dev.java.net/
http://www.onjava.com/lpt/a/5795


Nice to have features

1. It is possible to build light-weight repository that has dependencies files only,
but refer to Maven2 (or some other place) repository for binaries.


Versions for used components


ClassWorlds    1.1          http://classworlds.codehaus.org
JDIC           0.9.1        https://jdic.dev.java.net
Nailgun Server 0.7.1        http://www.martiansoftware.com/nailgun
log4j          1.2.13       http://logging.apache.org/log4j

Maven          2.0.6        http://maven.apache.org
Ant            1.7.0        http://ant.apache.org

BSF            2.4.0        http://jakarta.apache.org/bsf
Beanshell      2.0b5        http://www.beanshell.org/bsh-2.0b5.jar
Javascript     1.6R4        http://www.mozilla.org/rhino
Groovy         1.0          http://groovy.codehaus.org
Jelly          1.0          http://jakarta.apache.org/commons/jelly
Velocity       1.5          http://velocity.apache.org
Freemarker     2.3.9        http://freemarker.sourceforge.net
Velocity Tools 1.1          http://jakarta.apache.org/velocity
JRuby          0.9.9        http://jruby.codehaus.org
Pnuts          1.1          https://pnuts.dev.java.net
Jython         2.2b1        http://www.jython.org
Scriptlandia   2.2.2        http://sourceforge.net/projects/scriptlandia
Scriptella     0.8          http://scriptella.javaforge.com
Jscheme        7.2          http://jscheme.sourceforge.net
Jaskell        1.0          http://jaskell.codehaus.org
TCL            1.4.0        http://tcljava.sourceforge.net
AWK            0.14         http://jawk.sourceforge.net
F3             1.0          http://blogs.sun.com/chrisoliver/resource/tutorial.jnlp
Fortress       156          http://fortress.sunsource.net
Scala          2.4.0        http://scala-lang.org


e-mail:        shvets_alexander@yahoo.com, shvets@comcast.net
home-page:     http://home.comcast.net/~shvets
blog:          http://scriptlandia.blogspot.com
