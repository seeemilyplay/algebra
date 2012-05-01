package seeemilyplay.core.file;

import java.io.File;
import java.io.IOException;

/**
 * Used to interact with the operating system.
 * The main reason this functionality was split
 * into its own separate class was so that it
 * could easily be excluded from unit tests - this
 * sort of thing is hard to test.
 */
class LowLevelFileUtils {

	private LowLevelFileUtils() {
		super();
	}

	/**
	 * Calls <code>createNewFile</code> on the given
	 * <code>File</code>, and checks that a file
	 * was actually created, throwing an <code>Error</code>
	 * if one wasn't.
	 *
	 * @param file
	 * @throws IOException
	 */
	public static void createNewFile(File file) throws IOException{
		if (!file.createNewFile()) {
			throw new Error();
		}
	}

	/**
	 * Calls <code>mkdir</code> on the given
	 * <code>File</code>, but checks that a folder
	 * was actually created, throwing an <code>Error</code>
	 * if one wasn't.
	 *
	 * @param folder
	 */
	public static void mkdir(File folder) {
		if (!folder.mkdir()) {
			throw new Error();
		}
	}

	/**
	 * Calls <code>delete</code> on the given
	 * <code>File</code>, but checks that a file
	 * was actually deleted, throwing an <code>Error</code>
	 * if one wasn't.
	 *
	 * @param file
	 */
	public static void delete(File file) {
		if (!file.delete()) {
			throw new Error();
		}
	}
}
