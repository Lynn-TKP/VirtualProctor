package org.teachingkidsprogramming.virtualproctor.tests;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.teachingkidsprogramming.virtualproctor.BlobFileUtilities;

@UseReporter(DiffReporter.class)
public class BlobFileUtilitiesTest extends TestCase
{
  public void testLatestFiles() throws Exception
  {
    List<BlobFile> files = Arrays.asList(c("1", 2), c("2", 3), c("1", 4), c("1", 5));
    Approvals.verifyAll("file", BlobFileUtilities.latestFilesOnly(files));
  }
  private BlobFile c(String fileName, long time)
  {
    BlobFile blob = new BlobFile();
    blob.setFileName(fileName);
    blob.setCreation(new Date(time * 1000 * 60));
    return blob;
  }
}
