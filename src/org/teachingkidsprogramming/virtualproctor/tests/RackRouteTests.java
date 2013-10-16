package org.teachingkidsprogramming.virtualproctor.tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.jrack.JRack;
import org.jrack.RackEnvironment;
import org.jrack.RackResponse;
import org.teachingkidsprogramming.virtualproctor.RackRoute;
import org.teachingkidsprogramming.virtualproctor.Route;

@UseReporter(DiffReporter.class)
public class RackRouteTests extends TestCase
{
  public static class MockRoute implements JRack
  {
    private final String        title;
    private static StringBuffer log = new StringBuffer();
    public MockRoute()
    {
      this.title = "ViaAttribute";
    }
    public MockRoute(String title)
    {
      this.title = title;
    }
    @Override
    @Route("/Mock")
    public RackResponse call(Map<String, Object> environment) throws Exception
    {
      log.append(String.format("Called %s with '%s'\r\n", title, environment.get(RackEnvironment.PATH_INFO)));
      return null;
    }
  }
  public void testRouteMatch() throws Exception
  {
    RackRoute r = new RackRoute(new MockRoute("Default"));
    r.addRoute("/about", new MockRoute("About"));
    r.addRoutesFor(MockRoute.class);
    String calls[] = {"/", "/about", "/Mock"};
    for (String path : calls)
    {
      HashMap<String, Object> environment = new HashMap<String, Object>();
      environment.put(RackEnvironment.PATH_INFO, path);
      r.call(environment);
    }
    Approvals.verify(MockRoute.log.toString());
  }
}
