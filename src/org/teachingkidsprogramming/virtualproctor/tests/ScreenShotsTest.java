package org.teachingkidsprogramming.virtualproctor.tests;

import junit.framework.TestCase;

import org.apache.sling.commons.json.JSONObject;
import org.approvaltests.Approvals;
import org.teachingkidsprogramming.virtualproctor.ScreenShots;

public class ScreenShotsTest extends TestCase
{
  public void testJson() throws Exception
  {
    BlobFile[] images = {blobFile("llewellynscomputer", "imageKey1"), blobFile("lynnscomputer", "imageKey2"),};
    String json = new ScreenShots().getJsonForImages(images);
    verifyJson(json);
  }
  private BlobFile blobFile(String fileName, String key)
  {
    BlobFile blobFile = new BlobFile();
    blobFile.setFileName(fileName);
    blobFile.setKey(key);
    return blobFile;
  }
  private void verifyJson(String json) throws Exception
  {
    try
    {
      String pretty = new JSONObject(json).toString(2);
      Approvals.verify(pretty);
    }
    catch (Exception e)
    {
      Approvals.verify(json);
    }
  }
}
