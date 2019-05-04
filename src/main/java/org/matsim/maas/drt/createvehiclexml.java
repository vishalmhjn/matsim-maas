package org.matsim.maas.drt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Comment;


public class createvehiclexml {

    public static void main(String argv[]) {

        String csvFile = "/Users/vishal/Dropbox/Seminar Report/Full Population/Sampled_links10000.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int counter = 0;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Comment comment = doc.createComment("<!DOCTYPE vehicles SYSTEM \"http://matsim.org/files/dtd/dvrp_vehicles_v1.dtd\"");
            Element rootElement = doc.createElement("vehicles");
            doc.appendChild(rootElement);
            doc.insertBefore(comment, rootElement);



            try {

                br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) {
                    if (counter != 0) {
                        // use comma as separator
                        String[] link = line.split(cvsSplitBy);

                        Element vehicle = doc.createElement("vehicle");
                        rootElement.appendChild(vehicle);

                        // set attribute to staff element
                        Attr attr_id = doc.createAttribute("capacity");
                        attr_id.setValue("8");
                        vehicle.setAttributeNode(attr_id);

                        attr_id = doc.createAttribute("start_link");
                        attr_id.setValue(link[3]);
                        vehicle.setAttributeNode(attr_id);

                        attr_id = doc.createAttribute("t_0");
                        attr_id.setValue("0.0");
                        vehicle.setAttributeNode(attr_id);

                        attr_id = doc.createAttribute("t_1");
                        attr_id.setValue("86400.0");
                        vehicle.setAttributeNode(attr_id);

                        attr_id = doc.createAttribute("id");
                        attr_id.setValue("shuttle "+counter);
                        vehicle.setAttributeNode(attr_id);





                        //System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

                    }
                    counter = counter + 1;
                }
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (br != null) {
                    try {
                        br.close();
                        // write the content into xml file

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/Users/vishal/Dropbox/matsimtest/Sampled_links10000_XML.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        }
        catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }
}


