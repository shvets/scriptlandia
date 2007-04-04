import java.io.File;

import com.antwerkz.gosling.Default;
import com.antwerkz.gosling.Description;
import com.antwerkz.gosling.Project;
import com.antwerkz.gosling.tasks.Copy;
import com.antwerkz.gosling.tasks.Jar;
import com.antwerkz.gosling.tasks.Javac;
import com.antwerkz.gosling.tasks.Javadoc;
import com.antwerkz.gosling.tasks.Zip;
import com.antwerkz.gosling.types.DirSet;
import com.antwerkz.gosling.types.FileSet;
import com.antwerkz.gosling.types.Path;

public class Gosling extends Project {
    public static final String VERSION = "0.1";

    private static final String BUILD_DIR = "target/classes";
    private static final String DIST_DIR = "target/dist";
    private static final String DIST_FILE = "target/gosling-" + VERSION + ".zip";
    private static final String BOOTSTRAP_BIN_DIR = "bootstrap/bin";
    private static final String BOOTSTRAP_LIB_DIR = "bootstrap/lib";
    private static final String JAVA_SRC_DIR = "src/main/java";
    private static final String BIN_SRC_DIR = "src/main/bin";
    private static final String JAVADOC_DIR = "api";

    public Gosling() {
        loadProperties("gosling/gosling.properties");
    }

    @Default
    @Description("Builds the Gosling project")
    public void build() {
        new Javac(this, BUILD_DIR)
            .addSources(new FileSet("src/main/java")
                .addInclude("**/*.java"))
            .addOption("-Xlint:unchecked")
            .addOption("-Xlint:deprecated")
            .addOption("-g")
            .execute();
        new Copy(this)
            .setDestDir(new File(BUILD_DIR))
            .addFileSet(new FileSet(JAVA_SRC_DIR)
                .addExclude("**/*.java"))
            .execute();
    }

    @Description("Removes the build directory")
    public void clean() {
        delete("build");
    }

    @Description("Builds the distribution bundles")
    public void dist() {
        clean();
        jar();
        mkdir(DIST_DIR);

        new Copy(this)
            .setDestDir(new File(DIST_DIR + "/bin"))
            .addFileSet(new FileSet(BIN_SRC_DIR))
            .execute();

        new Copy(this)
            .setDestDir(new File(DIST_DIR + "/lib"))
            .addFileSet(new FileSet(BUILD_DIR)
                .addInclude("*.jar"))
            .execute();

        new Zip(this, new File(DIST_FILE))
            .addFileSet(new FileSet(".")
                .addInclude("build.*")
                .addInclude("src/**")
                .addInclude("lib/*.jar"))
            .execute();
    }

    @Description("Builds a jar of the compiled resources")
    public void jar() {
        build();
        new Jar(this, new File("build/gosling.jar"))
            .addFileSet(new FileSet(BUILD_DIR)
                .addInclude("**"))
            .execute();
    }

    @Description("Builds the javadoc")
    public void javadoc() {
        delete(JAVADOC_DIR);
        new Javadoc(this, new File(JAVADOC_DIR))
            .setSourcepath(new Path(this)
                .setPath(JAVA_SRC_DIR))
            .addPackageset(new DirSet(JAVA_SRC_DIR))
            .execute();
    }

  public static void main(String[] argv) {
    new Gosling().build();
  }

}