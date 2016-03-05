package de.chucknorris.client.chucknorristrayicon.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander Friedrichs
 */
public class Settings {

    private static final Logger LOG = LoggerFactory.getLogger(Settings.class);
    private final Properties properties;
    private final ResourceBundle bundle;
    
    public Settings() {
        this.properties = new Properties();
        this.bundle = ResourceBundle.getBundle(Constants.RESOURCEBUNDLE);
        LOG.debug("ResourceBundle loaded: {}", this.bundle);
    }
    
    

    private File getPropFile(){
        String userHome = System.getProperty("user.home");
        StringBuilder path = new StringBuilder(userHome);
        path.append(System.getProperty("file.separator"))
            .append(Constants.SETTINGS_FOLDER)
            .append(System.getProperty("file.separator"))
            .append(Constants.SETTINGS_FILE);
        File props = new File(path.toString());
        return props;
    }
    
    public void loadProps() throws IOException {
        File props = getPropFile();
        if (props.exists() && props.canRead()) {
            this.loadProps(props);
        }        
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder builder = new StringBuilder("Instance of class Settings:").append(lineSeparator);
        builder.append("Application properties:").append(lineSeparator);
        for(Object key : this.properties.keySet()){
            builder.append(key.toString()).append(" -> ").append(this.properties.get(key)).append(lineSeparator);
        }
        builder.append("System properties:").append(lineSeparator);
        for(Object key : System.getProperties().keySet()){
            builder.append(key.toString()).append(" -> ").append(System.getProperty(key.toString())).append(lineSeparator);
        }
        return builder.toString();
    }    
    
    
    public void setProperty(String key, String value) throws IOException{
        this.properties.setProperty(key, value);
        System.setProperty(key, value);        
    }
    
    private void saveProps(File props){
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(props);
            this.properties.store(fileOutputStream, Constants.SETTINGS_COMMENT);
        } catch (IOException ioe){
            LOG.error("Exception while saving the properties", ioe);
        } finally{
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException ex) {
                    LOG.error("Exception while closing the stream", ex);
                }
            }
        }
    }

    private void loadProps(File props) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(props);
            this.properties.load(fileInputStream);
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    LOG.error("Exception while closing Stream", ex);
                }
            }
        }
        for (Object object : this.properties.keySet()) {
            System.setProperty(object.toString(), this.properties.getProperty(object.toString()));
        }
    }

    public void saveProps() throws IOException {
        File folder = getPropFolder();
        if(!folder.exists()){
            folder.mkdirs();
        }
        File file = getPropFile();
        if(!file.exists()){
           file.createNewFile();            
        }
        this.saveProps(getPropFile());
    }

    private File getPropFolder() {
        String userHome = System.getProperty("user.home");
        StringBuilder path = new StringBuilder(userHome);
        path.append(System.getProperty("file.separator"))
            .append(Constants.SETTINGS_FOLDER);
        File props = new File(path.toString());
        return props;
    }
}
