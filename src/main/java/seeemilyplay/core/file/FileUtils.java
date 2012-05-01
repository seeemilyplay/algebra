package seeemilyplay.core.file;

import java.io.File;
import java.io.IOException;

import seeemilyplay.core.errorhandling.ErrorHandler;
import seeemilyplay.core.errorhandling.ExceptionThrowingTask;

/**
 * A class of handy static methods for manipulating
 * files.
 */
public class FileUtils {

	private FileUtils() {
		super();
	}

	public static void createFileIfNeeded(File file) {
		if (file.exists()) {
			if (!file.isFile()) {
				throw new Error();
			}
			return;
		}
		ErrorHandler.getInstance().run(new CreateFileTask(file));
	}

	private static class CreateFileTask implements ExceptionThrowingTask {

		private final File file;

		public CreateFileTask(File file) {
			this.file = file;
		}

		public void run() throws IOException {
			LowLevelFileUtils.createNewFile(file);
		}
	}

	public static void createFolderIfNeeded(File folder) {
		if (folder.exists()) {
			if (!folder.isDirectory()) {
				throw new Error();
			}
			return;
		}
		LowLevelFileUtils.mkdir(folder);
	}

	public static void recursiveDelete(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				recursiveDelete(child);
			}
		}
		LowLevelFileUtils.delete(file);
	}
}
