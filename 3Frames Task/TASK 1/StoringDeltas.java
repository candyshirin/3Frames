// # StoringDeltas README

// ## Overview
// This Java program demonstrates a simple approach to store versions and files efficiently by storing deltas instead of each version separately. The program uses a custom data structure to manage files and their versions.

// ## Code Structure
// The code consists of a single Java file, `StoringDeltas.java`, which contains the following classes:
// 1. `VersionedFile`: Represents a file and its deltas.
// 2. `VersionControlSystem`: Manages multiple files and their versions.
// 3. `Main`: Contains the `main` method to demonstrate the usage of the `VersionControlSystem`.

// ## Classes
// ### VersionedFile
// - Represents a file and its deltas.
// - Constructor: Initializes the base version and creates an empty list for deltas.
// - `addDelta(String delta)`: Adds a delta to the file.
// - `getFullVersion()`: Retrieves the full version of the file by applying all deltas.

// ### VersionControlSystem
// - Manages multiple files and their versions using a map.
// - Constructor: Initializes the map.
// - `addFile(String fileName, String baseVersion)`: Adds a new file with its base version.
// - `addDelta(String fileName, String delta)`: Adds a delta to a file.
// - `getFileVersion(String fileName)`: Retrieves any version of a file.

// ### Main
// - Contains the `main` method to demonstrate the usage of the `VersionControlSystem`.
// - Creates an instance of `VersionControlSystem`.
// - Adds files with base versions and applies deltas to them.
// - Retrieves versions of files and prints them.

// ## Additional Notes
// - This implementation is kept simple for demonstration purposes. Depending on requirements, further optimizations and features can be added.
// - Error handling and more robust file management can be implemented for production use.
// - Feel free to modify and extend the code as needed.





import java.util.*;

public class StoringDeltas {

    // Custom data structure to represent a file and its deltas
    // more changes can be done depending upon the requirments
    static class VersionedFile {
        private String baseVersion;
        private List<String> deltas;

        public VersionedFile(String baseVersion) {
            this.baseVersion = baseVersion;
            this.deltas = new ArrayList<>();
        }

        public void addDelta(String delta) {
            deltas.add(delta);
        }

        public String getFullVersion() {
            StringBuilder fullVersion = new StringBuilder(baseVersion);
            for (String delta : deltas) {
                fullVersion.append(delta);
            }
            return fullVersion.toString();
        }
    }

    // Class to manage multiple files and their versions
    // can we written in different files as well and can be more optimized
    static class VersionControlSystem {
        private Map<String, VersionedFile> files;

        public VersionControlSystem() {
            files = new HashMap<>();
        }

        // Method to add a new file with its base version
        public void addFile(String fileName, String baseVersion) {
            files.put(fileName, new VersionedFile(baseVersion));
        }

        // Method to add a delta to a file
        public void addDelta(String fileName, String delta) {
            if (files.containsKey(fileName)) {
                files.get(fileName).addDelta(delta);
            } else {
                System.out.println("File not found: " + fileName);
            }
        }

        // Method to retrieve any version of a file
        public String getFileVersion(String fileName) {
            if (files.containsKey(fileName)) {
                return files.get(fileName).getFullVersion();
            } else {
                return "File not found: " + fileName;
            }
        }
    }

    public static void main(String[] args) {
        VersionControlSystem vcs = new VersionControlSystem();

        // Add files with base versions  
        vcs.addFile("file1.txt", "Base content of file 1\n");
        vcs.addFile("file2.txt", "Base content of file 2\n");

        // Add deltas to files
        vcs.addDelta("file1.txt", "Delta 1 for file 1\n");
        vcs.addDelta("file2.txt", "Delta 1 for file 2\n");
        vcs.addDelta("file1.txt", "Delta 2 for file 1\n");

        // Retrieve any version of a file
        System.out.println("Version of file1.txt:");
        System.out.println(vcs.getFileVersion("file1.txt"));
        System.out.println("Version of file2.txt:");
        System.out.println(vcs.getFileVersion("file2.txt"));
    }
}
