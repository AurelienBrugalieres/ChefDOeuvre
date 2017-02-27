package skynamiccontrol.map;

import javafx.application.Platform;
import javafx.scene.image.Image;
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

import static com.sun.javafx.util.Utils.clamp;

/**
 * Created by fabien on 23/02/17.
 */
public class BackMapLayer extends Pane{
    public static int TILE_DIMENSION = 256;
    private static int GOOGLE_VERSION = 716;
    private QuadTree imageTree;
    private int zoom;
    private String DIRECTORY = "pictures/";
    private Image noMap;

    public BackMapLayer(int zoom) {
        super();
        this.zoom = zoom;
        imageTree = new QuadTree(0, 0, Math.pow(2, zoom), Math.pow(2, zoom));

        File pictureDirectory = new File( DIRECTORY);
        if(!pictureDirectory.exists()) {
            try{
                pictureDirectory.mkdir();
            }
            catch(SecurityException se){
                System.out.println("Security exception !!!");
            }
        }

        File file = new File( "SkynamicControl/src/resources/bitmaps/nomap.png");
        if(file.exists() && !file.isDirectory()) {
            noMap = new Image(file.toURI().toString());
        }

    }

    private void addTile(String filename, int x, int y) {
        File file = new File( filename);
        if(file.exists() && !file.isDirectory()) {
            ImageView tile = new ImageView(file.toURI().toString());
            this.getChildren().add(tile);
            imageTree.set(x, y, tile);
            tile.setTranslateX(x * TILE_DIMENSION);
            tile.setTranslateY(y * TILE_DIMENSION);
        }
    }

    private void setNoTile(int x, int y) {
        ImageView noTile = new ImageView(noMap);
        this.getChildren().add(noTile);
        imageTree.set(x, y, noTile);
        noTile.setTranslateX(x * TILE_DIMENSION);
        noTile.setTranslateY(y * TILE_DIMENSION);
    }

    private void requestTile(int X, int Y, int zoom) {
        //Is the image in memory ?
        if (imageTree.contains(X, Y)) {
            return;
        }

        setNoTile(X, Y);

        //Is the image in local directory ?
        String filename = String.format("%s%d,%d,%d",DIRECTORY , zoom, X, Y);
        //String filename = DIRECTORY+zoom+""+X+""+Y+;
        File file = new File( filename);
        if(file.exists() && !file.isDirectory()) {
            addTile(filename, X, Y);
            return;
        }

        //Download the image from the internet !
        //String url = String.format("http://khm0.google.com/kh/v=%d&x=%d&s=&y=%d&z=%d", GOOGLE_VERSION, X, Y, zoom);
        String url = "http://tile.openstreetmap.org/" + zoom + "/" + X + "/" + Y + ".png";
        downloadAsync(url, filename, X, Y, zoom);
    }



    private void downloadAsync(String sourceUrl, String filename, int X, int Y, int zoom) {
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


    private void downloadFromUrl(URL url, String localFilename) throws IOException {
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

    public void paveZone(double xMin, double yMin, double xMax, double yMax) {
        int x0 = clamp(0,(int) xMin/TILE_DIMENSION, (int)Math.pow(2, zoom) - 1);
        int y0 = clamp(0,(int) yMin/TILE_DIMENSION, (int)Math.pow(2, zoom) - 1);
        int xend = clamp(0,(int) xMax/TILE_DIMENSION, (int)Math.pow(2, zoom) - 1);
        int yend = clamp(0,(int) yMax/TILE_DIMENSION, (int)Math.pow(2, zoom) - 1);

        for(int x=x0; x<xend+1; x++) {
            for(int y=y0; y<yend+1; y++) {
                requestTile(x, y, zoom);
            }
        }
    }
}
