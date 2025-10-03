package templatesTutorial;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;

public class PrintInvitations {
    public static void main(String[] args) {
        try {
            // 1.
            FileInputStream fis = new FileInputStream("friends.xml");
            XMLDecoder decoder = new XMLDecoder(fis);
            List<Person> friends = (List<Person>) decoder.readObject();
            decoder.close();
            fis.close();

            // 2.
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init();

            // 3.
            Template template = velocityEngine.getTemplate("src/main/resources/invitation_template.vm");

            // 4.
            FileWriter writer = new FileWriter("invitations.html");

            // 5.
            for (Person friend : friends) {
                VelocityContext context = new VelocityContext();
                context.put("person", friend);
                StringWriter stringWriter = new StringWriter();
                template.merge(context, stringWriter);

                writer.write(stringWriter.toString());
                writer.write("\n");
            }

            writer.close();
            System.out.println("HTML invitations generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
