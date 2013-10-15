package org.teachingkidsprogramming.virtualproctor.tests;

import java.util.List;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.teachingkidsprogramming.virtualproctor.UploadImageRack;
import org.teachingkidsprogramming.virtualproctor.VirtualProctorViewerRack;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.tools.development.testing.LocalBlobstoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class GoogleFileTest extends TestCase
{
  public void testGetFiles() throws Exception
  {
    LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalBlobstoreServiceTestConfig());
    helper.setUp();
    UploadImageRack.writeToFile("1.png", GoogleFileTest.class.getResourceAsStream("testImage.jpg"));
    UploadImageRack.writeToFile("2.png", GoogleFileTest.class.getResourceAsStream("testImage.jpg"));
    UploadImageRack.writeToFile("3.png", GoogleFileTest.class.getResourceAsStream("testImage.jpg"));
    Approvals.verifyAll("image",
        VirtualProctorViewerRack.getImagesForQuery(VirtualProctorViewerRack.getFindFilesQuery()));
  }
  public void testFileInfo() throws Exception
  {
    LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalBlobstoreServiceTestConfig());
    helper.setUp();
    UploadImageRack.writeToFile("1.png", GoogleFileTest.class.getResourceAsStream("testImage.jpg"));
    DatastoreService service = DatastoreServiceFactory.getDatastoreService();
    List<Entity> foundFiles = service.prepare(VirtualProctorViewerRack.getFindFilesQuery()).asList(
        FetchOptions.Builder.withDefaults());
    BlobFile wrap = BlobFile.wrap(foundFiles.get(0));
    //BlobInfo wrap2 = new BlobInfoFactory().createBlobInfo(foundFiles.get(0));
    System.out.println(wrap.getKey());
    Approvals.verify(wrap.toString());
  }
  public void testname() throws Exception
  {
    String expected = "samantha";
    assertEquals(expected, "sam");
  }
}
