package seeemilyplay.core.file;

import java.io.File;
import java.lang.reflect.Constructor;

import junit.framework.TestCase;


public class FileUtilsTest extends TestCase {

	private static final File TEST_FOLDER = new File(FileUtilsTest.class.getName());

	private File file;
	private Error e;

	protected void setUp() throws Exception {
		super.setUp();
		file = null;
		e = null;
		FileUtils.recursiveDelete(TEST_FOLDER);
		FileUtils.createFolderIfNeeded(TEST_FOLDER);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		FileUtils.recursiveDelete(TEST_FOLDER);
	}

	public void testPrivateConstructorRunsWithoutProblem() {
		whenPrivateConstructorCalled();

		thenErrorIsntThrown();
	}

	public void testFileCreation() {

		givenFileName("file.txt");

		whenCreatingFile();

		thenFileExists();
	}

	public void testFolderCreation() {

		givenFolderName("folder");

		whenCreatingFolder();

		thenFolderExists();
	}

	public void testFileCreationWhenItsNotNeeded() {

		givenFileNameOfExistingFile("file.txt");

		whenCreatingFile();

		thenFileExists();
	}

	public void testFolderCreationWhenItsNotNeeded() {

		givenFolderNameOfExistingFolder("folder");

		whenCreatingFolder();

		thenFolderExists();
	}

	public void testFileCreationFailsIfFolderExists() {

		givenFileNameOfExistingFolder("file.txt");

		whenCreatingFile();

		thenErrorIsThrown();
	}

	public void testFolderCreationFailsIfFileExists() {

		givenFolderNameOfExistingFile("folder");

		whenCreatingFolder();

		thenErrorIsThrown();
	}

	public void testRecursiveDeletionOnFiles() {

		givenFileNameOfExistingFile("file.txt");

		whenRecursiveDelete();

		thenFileDoesntExist();
	}

	public void testRecursiveDeleteOnNonExistentFileIsOk() {

		givenFileName("file.txt");

		whenRecursiveDelete();

		thenFileDoesntExist();
	}

	public void testRecursiveDeleteOnNonExistentFolder() {

		givenFolderName("file.txt");

		whenRecursiveDelete();

		thenFolderDoesntExist();
	}

	public void testRecursiveDeletionOnFolders() {

		givenASampleDirectoryBelowFolder("folder");

		whenRecursiveDelete();

		thenFolderDoesntExist();
	}

	private void givenFileName(String fileName) {
		setFileName(fileName);
	}

	private void givenFolderName(String folderName) {
		setFileName(folderName);
	}

	private void givenFileNameOfExistingFile(String fileName) {
		setFileName(fileName);
		createFile();
	}

	private void givenFolderNameOfExistingFolder(String folderName) {
		setFileName(folderName);
		createFolder();
	}

	private void givenFileNameOfExistingFolder(String fileName) {
		setFileName(fileName);
		createFolder();
	}

	private void givenFolderNameOfExistingFile(String folderName) {
		setFileName(folderName);
		createFile();
	}

	private void setFileName(String fileName) {
		file = new File(TEST_FOLDER, fileName);
	}

	private void whenCreatingFile() {
		try {
			createFile();
		} catch (Error e) {
			this.e = e;
		}
	}

	private void whenCreatingFolder() {
		try {
			createFolder();
		} catch (Error e) {
			this.e = e;
		}
	}

	private void whenRecursiveDelete() {
		FileUtils.recursiveDelete(file);
	}

	private void whenPrivateConstructorCalled() {
		try {
			Class<?> cls = FileUtils.class;
		    Constructor<?> constructor = cls.getDeclaredConstructors()[0];
		    constructor.setAccessible(true);
		    constructor.newInstance((Object[])null);
		} catch (Throwable t) {
			this.e = new Error(t);
		}
	}

	private void createFile() {
		FileUtils.createFileIfNeeded(file);
	}

	private void createFolder() {
		FileUtils.createFolderIfNeeded(file);
	}

	private void thenFileExists() {
		assertFileExists();
	}

	private void thenFileDoesntExist() {
		assertFileDoesntExist();
	}

	private void thenFolderExists() {
		assertFileExists();
	}

	private void thenFolderDoesntExist() {
		assertFileDoesntExist();
	}

	private void assertFileExists() {
		assertTrue(file.exists());
	}

	private void assertFileDoesntExist() {
		assertFalse(file.exists());
	}

	private void thenErrorIsThrown() {
		assertNotNull(e);
	}

	private void thenErrorIsntThrown() {
		assertNull(e);
	}

	private void givenASampleDirectoryBelowFolder(String folderName) {
		setFileName(folderName);
		setupTestDirectory(folderName);
	}

	private void setupTestDirectory(String rootFolderName) {
		File folderA = new File(TEST_FOLDER, rootFolderName);
		File fileA1 = new File(folderA, "fileA1.txt");
		File fileA2 = new File(folderA, "fileA2.txt");
		File folderB = new File(folderA, "folderB");
		File fileB1 = new File(folderA, "fileB1.txt");
		File fileB2 = new File(folderA, "fileB2.txt");

		FileUtils.createFolderIfNeeded(folderA);
		FileUtils.createFileIfNeeded(fileA1);
		FileUtils.createFileIfNeeded(fileA2);
		FileUtils.createFolderIfNeeded(folderB);
		FileUtils.createFileIfNeeded(fileB1);
		FileUtils.createFileIfNeeded(fileB2);
	}
}
