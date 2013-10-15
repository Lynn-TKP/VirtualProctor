package org.teachingkidsprogramming.virtualproctor;

import java.util.ArrayList;
import java.util.List;

import org.teachingkidsprogramming.virtualproctor.tests.BlobFile;

import com.spun.util.ObjectUtils;

public class BlobFileUtilities
{

  public static BlobFile[] latestFilesOnly(List<BlobFile> foundFiles)
  {
    ArrayList<BlobFile> keptFiles = new ArrayList<BlobFile>();
    for (BlobFile file : foundFiles)
    {
      boolean latest = true;
      for (BlobFile sub : foundFiles)
      {
        if (ObjectUtils.isEqual(sub.getFileName(), file.getFileName()))
        {
          if (sub.getCreation().after(file.getCreation()))
          {
            latest = false;
          }
        }
      }
      if (latest)
      {
        keptFiles.add(file);
      }
    }
    return keptFiles.toArray(new BlobFile[keptFiles.size()]);
  }
}
