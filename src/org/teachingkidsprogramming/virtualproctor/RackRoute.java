package org.teachingkidsprogramming.virtualproctor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.utils.RackEnviromentHelper;

import com.spun.util.MySystem;

public class RackRoute implements JRack
{
  private final JRack        defaultRack;
  private Map<String, JRack> routes = new HashMap<String, JRack>();
  public RackRoute(JRack defaultRack)
  {
    this.defaultRack = defaultRack;
  }
  @Override
  public RackResponse call(Map<String, Object> environment) throws Exception
  {
    String key = RackEnviromentHelper.getPathInfo(environment);
    MySystem.variable("path", key);
    JRack rack = routes.get(key);
    rack = rack == null ? defaultRack : rack;
    return rack.call(environment);
  }
  public RackRoute addRoute(String path, JRack rack)
  {
    routes.put(path, rack);
    return this;
  }
  public RackRoute addRoutesFor(Class<? extends JRack>... classes)
  {
    for (Class<? extends JRack> rackClass : classes)
    {
      try
      {
        Method callMethod = rackClass.getMethod("call", new Class[]{Map.class});
        if (callMethod.isAnnotationPresent(Route.class))
        {
          addRoute(callMethod.getAnnotation(Route.class).value(), rackClass.newInstance());
        }
      }
      catch (Exception e)
      {}
    }
    return this;
  }
}
