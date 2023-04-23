package sd2223.trab1.servers.rest;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.util.Random;
import java.util.logging.Logger;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import sd2223.trab1.servers.java.Discovery;
import sd2223.trab1.servers.java.JavaFeeds;

public class RestFeedServer {

    private static Logger Log = Logger.getLogger(RestFeedServer.class.getName());

    static {
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    public static final int PORT = 8080;

    private static final int BUF_SIZE = 1024;

    public static final String SERVICE = "feeds";

    private static final String SERVER_URI_FMT = "http://%s:%s/rest";

    public static void main(String[] args) {
        try {
            var domain = args[0];
            var seed = args[1];
            JavaFeeds.DOMAIN = domain;
            JavaFeeds.SEED = new Random(Long.parseLong(seed));

            ResourceConfig config = new ResourceConfig();
            config.register(RestFeedResource.class);

            //config.register(CustomLoggingFilter.class);

            String ip = InetAddress.getLocalHost().getHostAddress();
            String serverURI = String.format(SERVER_URI_FMT, ip, PORT);
            JdkHttpServerFactory.createHttpServer(URI.create(serverURI.replace(ip, "0.0.0.0")), config);

            Discovery.getInstance().announce(domain, SERVICE, "tcp://" + InetAddress.getLocalHost().getHostName()
                    + ":" + PORT);

            System.err.println("---------------------------------------------------");
            try(var ss = new ServerSocket(PORT)){
                while(true){
                    var cs = ss.accept();

                    int n;

                    var buf = new byte[BUF_SIZE];
                    while((n = cs.getInputStream().read(buf))>0)
                        System.out.write(buf, 0, n);

                    System.err.println("Connection closed.");

                }
            }
        }catch (Exception e){
            Log.severe(e.getMessage());
        }
    }

}
