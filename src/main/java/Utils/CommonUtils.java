package Utils;

import Pojos.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;


public class CommonUtils {


    /**
     * This method will upload the file for choose file by passing path of the file
     * to load.
     *
     * @param fileLocation
     */

    public static void uploadFile(String fileLocation) {
        try {
            StringSelection stringSelection = new StringSelection(fileLocation);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            // native key strokes for CTRL, V and ENTER keys
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    /**
     * Split the string at space
     *
     * @param str
     * @return
     */
    public static String[] splitString(String str) {
        String[] values = str.split(" ");
        return values;
    }


    /**
     * Appends chars to the given string resulting in a string of 4 chars length
     *
     * @param str
     * @return
     */
    public static String randomGenerator(String str, int length, String type) {

        StringBuilder salt = new StringBuilder(str);
        Random rnd = new Random();
        String saltStr ="";
        String SALTCHARS ="";

        switch (type) {
            case "numbers":
                SALTCHARS = "0123456789";
                while (salt.length() < length) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                saltStr= salt.toString();

            case "splchar":
                SALTCHARS = "! @#$%&*+";

                while (salt.length() < length) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                saltStr = salt.toString();

            case "numspecialchar":
                SALTCHARS = "0123456789 ! @#$%&*+";

                while (salt.length() < length) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                saltStr = salt.toString();

            case "string":
                SALTCHARS = "abcdefghijklmnopqrstuvwxyz";

                while (salt.length() <=length) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                saltStr = salt.toString();

            default:
                break;
        }

        return saltStr;
    }

    public String generateStringFromResource(String path) throws IOException {
      /*  ObjectMapper objectMapper = new ObjectMapper();
        Pet p = objectMapper.readValue(Files.readAllBytes(Paths.get(path)),
                Pet.class);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(p);*/

       // return json;
        return new String(Files.readAllBytes(Paths.get(path)));

    }
}
