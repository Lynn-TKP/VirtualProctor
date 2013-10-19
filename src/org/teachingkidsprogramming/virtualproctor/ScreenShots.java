package org.teachingkidsprogramming.virtualproctor;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;
import org.teachingkidsprogramming.virtualproctor.tests.BlobFile;

import com.spun.util.ObjectUtils;

public class ScreenShots implements JRack
{
  @Override
  @Route("/ScreenShots")
  public RackResponse call(Map<String, Object> environment) throws Exception
  {
    String json = getJsonForImages(VirtualProctorViewerRack.getAllImagesFromCache());
    return standardJson(json);
  }
  public String getJsonForImages(BlobFile[] files)
  {
    try
    {
      JSONObject array = new JSONObject();
      for (int i = 0; i < files.length; i++)
      {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileName", files[i].getFileName());
        jsonObject.put("imageUrl", "/Image?fileName=" + files[i].getKey());
        array.put("" + i, jsonObject);
      }
      array.put("length", files.length);
      JSONObject all = new JSONObject();
      all.put("images", array);
      return all.toString();
    }
    catch (JSONException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static RackResponse standardJson(String html)
  {
    return new RackResponse(RackResponseUtils.ReturnCode.OK, getStandardJsonHeader(), html);
  }
  public static Map<String, String> getStandardJsonHeader()
  {
    Map<String, String> header = new HashMap<String, String>();
    header.put(RackResponseUtils.CONTENT_TYPE, "application/json");
    return header;
  }
}
