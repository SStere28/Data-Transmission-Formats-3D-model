package com.epam.training.modeltransformer.writter;

import com.epam.training.modeltransformer.projection.rotation.Angle;

import javax.xml.stream.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class STAXCreator {

    public void createDocument(List<List<Angle>> data, String outputFilePath) throws XMLStreamException {

        XMLOutputFactory factory = XMLOutputFactory.newInstance();

        try {
            XMLStreamWriter writer = factory.createXMLStreamWriter(
                    new FileWriter(outputFilePath));

            writer.writeStartDocument();
            writer.writeStartElement("html");
            writer.writeStartElement("body");
            writer.writeStartElement("svg");
            writer.writeAttribute("width", "1000");
            writer.writeAttribute("height", "1000");
            for (List<Angle> data1 : data) {
                createPolygonElements(writer, data1);
            }

            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();

        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }

    }

    private void createPolygonElements(XMLStreamWriter writer, List<Angle> angles)
            throws XMLStreamException {

        writer.writeStartElement("polygon");
        writer.writeAttribute("points", "" + angles.get(1).getAngleX() + "," + angles.get(1).getAngleY() + " "
                + angles.get(0).getAngleX() + "," + angles.get(0).getAngleY() + " "
                + angles.get(2).getAngleX() + "," + angles.get(2).getAngleY());

        writer.writeAttribute("style", "fill:white;stroke:black;stroke-width:0.1");
        writer.writeEndElement();

    }


}
