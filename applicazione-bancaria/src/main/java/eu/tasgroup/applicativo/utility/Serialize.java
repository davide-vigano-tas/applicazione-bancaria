package eu.tasgroup.applicativo.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class Serialize {

	public static String toString(Serializable o) throws IOException{
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ObjectOutputStream stream = new ObjectOutputStream(output);
		
		stream.writeObject(o);
		
		output.close();
		stream.close();
		
		return Base64.getEncoder().encodeToString(output.toByteArray());
	}
	
	public static Object fromString(String string) throws IOException, ClassNotFoundException{
		byte[] byteObj = Base64.getDecoder().decode(string);
		
		ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(byteObj));
		
		Object obj = stream.readObject();
		
		stream.close();
		
		return obj;
	}
}
