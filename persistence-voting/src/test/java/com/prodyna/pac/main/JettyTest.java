package com.prodyna.pac.main;

import static io.restassured.RestAssured.when;


import io.restassured.RestAssured;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.springframework.web.WebApplicationInitializer;

import java.util.concurrent.ConcurrentHashMap;

//@Ignore("To make Maven happy")
public class JettyTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void startJetty() throws Exception {
        server = new Server(8080);
        Connector connector = new ServerConnector(server);
        server.addConnector(connector);

        WebAppContext wac = new WebAppContext();
        wac.setContextPath("/");
        wac.setConfigurations(new Configuration[]{
                new WebXmlConfiguration(),
                new AnnotationConfiguration() {
                    @Override
                    public void preConfigure(WebAppContext context) throws Exception {
                        ConcurrentHashMap<String, ConcurrentHashSet<String>> map = new ConcurrentHashMap<String, ConcurrentHashSet<String>>();
                        ConcurrentHashSet<String> set = new ConcurrentHashSet<String>();
                        set.add(PersistenceApplication.class.getName());
                        map.put(WebApplicationInitializer.class.getName(), set);
                        context.setAttribute(CLASS_INHERITANCE_MAP, map);
                        _classInheritanceHandler = new ClassInheritanceHandler(map);
                    }
                }
        });
        wac.setServer(server);

        server.setHandler(wac);
        server.start();
    }

    @Before
    public void setUpBeforeTest() {
        RestAssured.reset();
    }
    
	@Test
	public void readHome() throws Exception {
		 
		System.out.println("Running test jetty test");
		
		
		when().get("/info").then().log();
		
		
		System.out.println("Running test jetty test");
	}
	
    

    @AfterClass
    public static void stopJetty() throws Exception {
        server.stop();
        server.join();
    }

    private static Server server;
}
