package json;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Encoder class to go to and from JSON. This way GSON is only in one class.
 * All calls to encode or decode JSON are sent to this class.
 */


//should every handler class share the same encoder?? We only want one instance of gson...
    //so how can we do that??
public class Encoder {

    /**
     * Gson object member variable
     */
    Gson gson = new Gson();

    public Encoder(){}

    /**
     * Returns the encoded json object given the object to encode.
     * @param objectToEncode
     * @return
     */
    public String encode(Object objectToEncode)
    {
        return gson.toJson(objectToEncode);
    }

    /**
     * Takes in a JSON string and ToClass and uses GSON to convert the json to an object.
     * @param json
     * @param toJsonClass
     * @return
     */
    public Object decode(String json, Class toJsonClass)
    {
        return gson.fromJson(json, toJsonClass);
    }

    /**
     * same as "decode" method except that it takes in a fileName.
     * @param fileName
     * @param toJsonClass
     * @return
     */
    public Object decodeFile(String fileName, Class toJsonClass)
    {
        try
        {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            return gson.fromJson(fileReader, toJsonClass);
        }
        catch(FileNotFoundException e)
        {
            System.out.print(e.getMessage());
        }
        catch(Exception e)
        {
            System.out.print(e.getMessage());
        }
        return null;
    }

    /**
     * Writes the string to an OutputStream file to be used by the Proxy Class
     * @param str
     * @param os
     * @throws IOException
     */
    public void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    /**
     * Reads the string from an InputStream file to be used by the Proxy Class
     * @param is
     * @return
     * @throws IOException
     */
    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

}