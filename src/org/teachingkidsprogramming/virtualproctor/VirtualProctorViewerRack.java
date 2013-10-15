package org.teachingkidsprogramming.virtualproctor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;
import org.teachingkidsprogramming.virtualproctor.tests.BlobFile;

import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.spun.util.StringUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

public class VirtualProctorViewerRack implements JRack
{
  private static Date lastTimeChecked = new Date();
  @Override
  @Route("/")
  public RackResponse call(Map<String, Object> environment) throws Exception
  {
    String html = FileUtils.readFromClassPath(getClass(), "virtual_proctor.html");
    html = StringUtils.stripWhiteSpace(html);
    return RackResponseUtils.standardHtml(html);
  }
  private BlobFile[] getAllImagesFromCache()
  {
    removeOldFiles();
    return UploadImageRack.uploadedFiles.values().toArray(new BlobFile[0]);
  }
  public void removeOldFiles()
  {
    Date tenMinutesAgo = tenMinutesAgo();
    if (lastTimeChecked.before(tenMinutesAgo))
    {
      lastTimeChecked = new Date();
      for (String k : UploadImageRack.uploadedFiles.keySet())
      {
        if (UploadImageRack.uploadedFiles.get(k).getCreation().before(tenMinutesAgo))
        {
          UploadImageRack.uploadedFiles.remove(k);
        }
      }
    }
  }
  public static Date tenMinutesAgo()
  {
    Date tenMinutesAgo = new Date(System.currentTimeMillis() - (10 * 60 * 1000));
    return tenMinutesAgo;
  }
  public RackResponse call(BlobFile[] files)
  {
    String html = VelocityParser.parseFromClassPath(getClass(), "virtual_proctor.html",
        new ContextAware.ContextAwareMap("files", files));
    html = StringUtils.stripWhiteSpace(html);
    return RackResponseUtils.standardHtml(html);
  }
  public static BlobFile[] getAllImages()
  {
    Date tenMinutesAgo = tenMinutesAgo();
    Query query = getFindFilesQuery().addFilter(BlobInfoFactory.CREATION, FilterOperator.GREATER_THAN,
        tenMinutesAgo);
    return getImagesForQuery(query);
  }
  public static BlobFile[] getImagesForQuery(Query query)
  {
    DatastoreService service = DatastoreServiceFactory.getDatastoreService();
    List<BlobFile> foundFiles = BlobFile.wrapAll(service.prepare(query)
        .asList(FetchOptions.Builder.withDefaults()));
    return BlobFileUtilities.latestFilesOnly(foundFiles);
  }
  public static Query getFindFilesQuery()
  {
    return new com.google.appengine.api.datastore.Query("__BlobInfo__");
  }
}
