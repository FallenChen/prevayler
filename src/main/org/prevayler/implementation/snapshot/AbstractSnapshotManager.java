//Prevayler(TM) - The Free-Software Prevalence Layer.
//Copyright (C) 2001-2004 Klaus Wuestefeld
//This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
//Contributions: Jacob Kjome.

package org.prevayler.implementation.snapshot;

import java.io.*;

import org.prevayler.foundation.*;


/**
 * Abstract base class providing 99% of the functionality to implement a SnapshotManager.  Simply extend this class and implement the abstract writeSnapshot()/readSnapshot() methods.  Make sure to call super(Object, String).
 */
public abstract class AbstractSnapshotManager implements SnapshotManager {

    private File _directory;
	private Object _recoveredPrevalentSystem;
	private long _recoveredVersion;

	// this is only here for NullSnapshotManager support
	protected void nullInit(Object recoveredPrevalentSystem) {
		_directory = null;
		_recoveredVersion = 0;
		_recoveredPrevalentSystem = recoveredPrevalentSystem;
	}

	/**
     * @param newPrevalentSystem The prevalentSystem to serialize/deserialize
     * @param snapshotDirectoryName The path of the directory where the last snapshot file will be read and where the new snapshot files will be created.
	 */
	protected void init(Object newPrevalentSystem, String snapshotDirectoryName) throws IOException, ClassNotFoundException {
		checkValidSuffix(suffix());

		_directory = FileManager.produceDirectory(snapshotDirectoryName);
		_recoveredVersion = latestVersion();
		_recoveredPrevalentSystem = _recoveredVersion == 0
			? newPrevalentSystem
			: readSnapshot(_recoveredVersion);
	}

	public Object recoveredPrevalentSystem() { return _recoveredPrevalentSystem; }


	public long recoveredVersion() { return _recoveredVersion; }


	public void writeSnapshot(Object prevalentSystem, long version) throws IOException {
		File tempFile = File.createTempFile("snapshot" + version + "temp", "generatingSnapshot", _directory);

		writeSnapshot(prevalentSystem, tempFile);

		File permanent = snapshotFile(version);
		permanent.delete();
		if (!tempFile.renameTo(permanent)) throw new IOException("Temporary snapshot file generated: " + tempFile + "\nUnable to rename it permanently to: " + permanent);
	}


	private void writeSnapshot(Object prevalentSystem, File snapshotFile) throws IOException {
        OutputStream out = new FileOutputStream(snapshotFile);
        try {
            writeSnapshot(prevalentSystem, out);
        } finally {
            out.close();
        }
	}


	/**
	 * @see org.prevayler.implementation.snapshot.SnapshotManager#writeSnapshot(Object, OutputStream)
	 */
    public abstract void writeSnapshot(Object prevalentSystem, OutputStream out) throws IOException;


    /**
     * Returns "snapshot", the default suffix/extension for snapshot files. You can overload this method and return a different suffix if you want, such as "XmlSnapshot", but it must end with either "snapshot" or "Snapshot" and may only contain letters and digits.
	 */
	protected String suffix() {
		return "snapshot";
	}


	/**
     * Returns zero if no snapshot file was found.
	 */
	private long latestVersion() throws IOException {
		return latestVersion(_directory);
	}

	private static long latestVersion(File directory) throws IOException {
		String[] fileNames = directory.list();
		if (fileNames == null) throw new IOException("Error reading file list from directory " + directory);

		long result = 0;
		for (int i = 0; i < fileNames.length; i++) {
			long candidate = version(fileNames[i]);
			if (candidate > result) result = candidate;
		}
		return result;
	}


	private Object readSnapshot(long version) throws ClassNotFoundException, IOException {
		File snapshotFile = snapshotFile(version);
		return readSnapshot(snapshotFile);
	}


	private Object readSnapshot(File snapshotFile) throws ClassNotFoundException, IOException {
        FileInputStream in = new FileInputStream(snapshotFile);
        try {
            return readSnapshot(in);
        } finally { in.close(); }
	}


	/**
	 * @see org.prevayler.implementation.snapshot.SnapshotManager#readSnapshot(InputStream)
	 */
    public abstract Object readSnapshot(InputStream in) throws IOException, ClassNotFoundException;


    private File snapshotFile(long version) {
		return snapshotFile(version, _directory, suffix());
	}


	private static final int DIGITS_IN_SNAPSHOT_FILENAME = 19;
	private static final String SNAPSHOT_SUFFIX_PATTERN = "[a-zA-Z0-9]*[Ss]napshot";
	private static final String SNAPSHOT_FILENAME_PATTERN = "\\d{" + DIGITS_IN_SNAPSHOT_FILENAME + "}\\." + SNAPSHOT_SUFFIX_PATTERN;

	private static void checkValidSuffix(String suffix) {
		if (!suffix.matches(SNAPSHOT_SUFFIX_PATTERN)) {
			throw new IllegalStateException("Snapshot filename suffix must match /" + SNAPSHOT_SUFFIX_PATTERN + "/, but '" + suffix + "' does not");
		}
	}

	private static File snapshotFile(long version, File directory, String suffix) {
		String fileName = "0000000000000000000" + version;
		return new File(directory, fileName.substring(fileName.length() - DIGITS_IN_SNAPSHOT_FILENAME) + "." + suffix);
	}

	/**
     * Returns -1 if fileName is not the name of a snapshot file.
	 */
	private static long version(String fileName) {
		if (!fileName.matches(SNAPSHOT_FILENAME_PATTERN)) return -1;
		return Long.parseLong(fileName.substring(0, fileName.indexOf(".")));    // "00000.snapshot" becomes "00000".
	}


	/**
     * Find the latest snapshot file. Returns null if no snapshot file was found.
	 */
	public static File latestSnapshotFile(File directory, String suffix) throws IOException {
		long version = latestVersion(directory);
		if (version == 0) {
			return null;
		} else {
			return snapshotFile(version, directory, suffix);
		}
	}

}
