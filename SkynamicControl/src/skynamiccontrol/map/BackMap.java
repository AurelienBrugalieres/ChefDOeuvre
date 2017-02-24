package skynamiccontrol.map;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import skynamiccontrol.quadtree.QuadTree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by fabien on 23/02/17.
 */
public class BackMap extends Pane{
    public int TILE_DIMENSION = 256;
    private static int GOOGLE_VERSION = 716;
    private ArrayList<QuadTree> imageTrees;
    private String DIRECTORY = "pictures/";

    public BackMap() {
        super();
        imageTrees = new ArrayList<>(Collections.nCopies(19, null));

        File pictureDirectory = new File( DIRECTORY);
        if(!pictureDirectory.exists()) {
            try{
                pictureDirectory.mkdir();
            }
            catch(SecurityException se){
                System.out.println("Security exception !!!");
            }
        }
    }

    public void addTile(String filename, int x, int y) {
        File file = new File( filename);
        if(file.exists() && !file.isDirectory()) {
            ImageView tile = new ImageView(file.toURI().toString());
            this.getChildren().add(tile);

            tile.setTranslateX(x * TILE_DIMENSION);
            tile.setTranslateY(y * TILE_DIMENSION);
        }
    }


    public void requestTileGPS(double latitude, double longitude, int zoom) {
        double z = 1<<zoom;
        double tx = (longitude+180)/360.0;
        double latrad = latitude * Math.PI / 180.0;
        double ty = 0.5 - (0.5/Math.PI)*Math.log(Math.tan(latrad) + 1.0/Math.cos(latrad));
        int X = (int)(tx * z);
        int Y = (int)(ty * z);
        //double resX = tx*z - X;
        //double resY = ty*z - Y;
        System.out.println(X);
        System.out.println(Y);
        System.out.println(zoom);
        requestTile(X, Y, zoom);
    }

    public void requestTile(int X, int Y, int zoom) {
        QuadTree quadTree = imageTrees.get(zoom);
        if(quadTree == null) {
            quadTree = new QuadTree(0, 0, 10000, 100000);
            imageTrees.set(zoom, quadTree);
        }


        //Is the image in memory ?
        quadTree = imageTrees.get(zoom);
        if (quadTree.contains(X, Y)) {
            return;
        }

        //Is the image in local directory ?
        String filename = String.format("%s%d,%d,%d",DIRECTORY , zoom, X, Y);
        //String filename = DIRECTORY+zoom+""+X+""+Y+;
        File file = new File( filename);
        if(file.exists() && !file.isDirectory()) {
            addTile(filename, X, Y);
            return;
        }

        //Download the image from the internet !
        String url = String.format("http://khm0.google.com/kh/v=%d&x=%d&s=&y=%d&z=%d", GOOGLE_VERSION, X, Y, zoom);
        //String url = "http://tile.openstreetmap.org/" + zoom + "/" + X + "/" + Y + ".png";
        downloadAsync(url, filename, X, Y, zoom);
        return;
    }



    void downloadAsync(String sourceUrl, String filename, int X, int Y, int zoom) {
        Thread download = new Thread(()->{
            URL url= null;
            try {
                url = new URL(sourceUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                downloadFromUrl(url, filename);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        addTile(filename, X, Y);
                    }
                });
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });
        download.start();//start the thread
    }


    void downloadFromUrl(URL url, String localFilename) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URLConnection urlConn = url.openConnection();//connect

            is = urlConn.getInputStream();               //get connection inputstream
            fos = new FileOutputStream(localFilename);   //open outputstream to local file

            byte[] buffer = new byte[4096];              //declare 4KB buffer
            int len;

            //while we have availble data, continue downloading and storing to local file
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

    public void paveZone(double xMin, double yMin, double xMax, double yMax, int zoom) {
        double x0 = xMin/TILE_DIMENSION;
        double y0 = yMin/TILE_DIMENSION;
        double xend = xMax/TILE_DIMENSION;
        double yend = yMax/TILE_DIMENSION;

        QuadTree quadTree = imageTrees.get(zoom);
        for(int x=(int)x0; x<(int)xend+1; x++) {
            for(int y=(int)y0; y<(int)yend+1; y++) {
                requestTile(x, y, zoom);
            }
        }
    }
}
