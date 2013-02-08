package entrypoint;
import com.jdotsoft.jarloader.JarClassLoader;

/**
 * This class serves as the entrypoint loader for the JarClassLoader
 * The main game container is bootstrapped from this class
 */
public class ClientEntryPoint {

	public static void main(String[] args) {
	    JarClassLoader jcl = new JarClassLoader();
	    try {
	        jcl.invokeMain("client.EpicnessAppShell", args);
	    } catch (Throwable e) {
	        e.printStackTrace();
	    }
	} // main()
}
