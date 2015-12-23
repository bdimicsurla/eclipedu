package plugin.configuration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	
	private Properties properties;
	
	public Config(){
		properties=new Properties();
		properties.setProperty("Version", ""+1.0);
		properties.setProperty("Project_name", "eclipeduplugin");
		properties.setProperty("Remote_repository", "https://github.com/bdimicsurla/eclipedu.git");
		properties.setProperty("Key_path", "D:/Projects/Project's Java/Fax/VezbeZaDiplomski/kljucevi/studentIS1");
		properties.setProperty("Kvalues_path", "D:/");
		properties.setProperty("Work_repository", "D:/Git/EclipsePlugin/");
		properties.setProperty("CPUsername", "KlincovM");
		properties.setProperty("CPPassword", "dusandragan22");
		try {
			properties.store(new FileOutputStream("config.properties"),null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setProperties(Properties properties){
		this.properties=properties;
	}
	
	public Properties getProperties(){
		return properties;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Config con=new Config();
		
	}


}
