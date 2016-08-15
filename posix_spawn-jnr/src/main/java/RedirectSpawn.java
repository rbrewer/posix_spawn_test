import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jnr.constants.platform.linux.OpenFlags;
import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;
import jnr.posix.SpawnFileAction;

/**
 * Tests redirecting stdout from a spawned process to a file, using JNR.
 */
public class RedirectSpawn {

    /** The POSIX object that provides the JNR native methods. */
    private static final POSIX S_POSIX = POSIXFactory.getPOSIX();
    /** Filename to stick the output (hopefully). */
    private static final String OUTPUT_FILENAME = "/tmp/bar-log";

    /**
     * Spawn a process to echo bar, with stdout redirected to a file.
     * @param args Command line arguments.
     * @throws IOException If there are problems with the file.
     */
    public static void main(String[] args) throws IOException {
        // Create file actions that direct what to do with the parent's file descriptors as inherited
        // by the child process.
        List<SpawnFileAction> spawnFileActions = new ArrayList<>();
        // Attach a file opened for writing to stdout (fd 1) of child process, creating if it doesn't exist
        spawnFileActions.add(SpawnFileAction.open(OUTPUT_FILENAME, 1,
                OpenFlags.O_WRONLY.value() | OpenFlags.O_CREAT.value() | OpenFlags.O_TRUNC.value(), 0644));

        List<String> argv = Arrays.asList("/usr/bin/echo", "bar");

        ArrayList<String> envp = new ArrayList<String>();
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        // Actually spawn the process. See "man posix_spawnp" on a Linux system for explanation of the parameters.
        long pid = S_POSIX.posix_spawnp(argv.get(0), spawnFileActions, argv, envp);
        if (pid <= 0) {
            System.err.println("Error spawning process.");
        }

        System.out.printf("Child process PID = %d\n", pid);
    }

}
