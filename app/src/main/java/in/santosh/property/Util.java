package in.santosh.property;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import androidx.annotation.RequiresApi;

public class Util {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getProperty(String key, Context context) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();

        String SAMPLE_JSON_DATA = "{\n" +
                "    \"data\": {\n" +
                "        \"firstName\": \"Spider\",\n" +
                "        \"lastName\": \"Man\",\n" +
                "        \"age\": 21,\n" +
                "        \"cars\":[ \"Ford\", \"BMW\", \"Fiat\" ]\n" +
                "    }\n" +
                "}";

        File f=saveBitmapInFile();
        FileWriter fw=new FileWriter(f);
        fw.write(SAMPLE_JSON_DATA);
        fw.close();
        ObjectMapper objectMapper = new ObjectMapper();

        Map props = transformJsonToMap(objectMapper.readTree(SAMPLE_JSON_DATA),null);
        properties=mapToProperties(props);
        System.out.println(props.toString());



        return properties.getProperty(key);

    }
    public static Properties mapToProperties(Map<String, String> map) {
        Properties p = new Properties();
        Set<Map.Entry<String,String>> set = map.entrySet();
        for (Map.Entry<String,String> entry : set) {
            p.put(entry.getKey(), entry.getValue());
        }
        return p;
    }


//    public String readFile(File file, Charset charset) throws IOException {
//        return new String(Files.readAllBytes(file.toPath()), charset);
//    }
    public static File saveBitmapInFile() {

        try {
            File fileDir = new File(Environment.getExternalStorageDirectory(), "/" + "Property");
            if (!fileDir.exists())
                fileDir.mkdirs();
            File file = new File(fileDir, "config.properties");

            if (!file.exists())
                file.createNewFile();

            return file;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
            // signatureViewModel.getInfo(payType, Float.parseFloat(getCollectedValue));
        }

    //------------ Transform jackson JsonNode to Map -----------
    public static Map<String, String> transformJsonToMap(JsonNode node, String prefix){

        Map<String,String> jsonMap = new HashMap<>();

        if(node.isArray()) {
            //Iterate over all array nodes
            int i = 0;
            for (JsonNode arrayElement : node) {
                jsonMap.putAll(transformJsonToMap(arrayElement, prefix+"[" + i + "]"));
                i++;
            }
        }else if(node.isObject()){
            Iterator<String> fieldNames = node.fieldNames();
            String curPrefixWithDot = (prefix==null || prefix.trim().length()==0) ? "" : prefix+".";
            //list all keys and values
            while(fieldNames.hasNext()){
                String fieldName = fieldNames.next();
                JsonNode fieldValue = node.get(fieldName);
                jsonMap.putAll(transformJsonToMap(fieldValue, curPrefixWithDot+fieldName));
            }
        }else {
            //basic type
            jsonMap.put(prefix,node.asText());
            System.out.println(prefix+"="+node.asText());
        }

        return jsonMap;
    }
        //return null;
    }    // return true;

            // }



