package com.kazurayam.ks.extentreports

import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.configuration.RunConfiguration
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.FileVisitResult
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes;

abstract class ReportBuilder {

	abstract void attachEReport(TestSuiteContext testSuiteContext, String setDocumentTitle, String setReportTitle, String projectDir)

	abstract void startEReport(TestCaseContext testCaseContext)

	abstract void flushEReport()

	abstract String getScreenshot() throws IOException

	abstract void takeScreenshotFailure(TestCaseContext testCaseContext) throws IOException

	void deleteFolderContents() throws IOException {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		Path folderPath = projectDir.resolve("Extent")
		if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
			Files.walkFileTree(folderPath,
					new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
							Files.delete(file); // Delete file
							return FileVisitResult.CONTINUE;
						}
						@Override
						public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
							if (exc == null) {
								Files.delete(dir); // Delete directories
								return FileVisitResult.CONTINUE;
							} else {
								throw exc;
							}
						}
					}
					)
		}
	}

	abstract void addScreenshot(String s)

	abstract void attachLog(String details)
}
