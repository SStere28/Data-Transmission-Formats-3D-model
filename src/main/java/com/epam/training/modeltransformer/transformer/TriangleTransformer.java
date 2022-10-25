package com.epam.training.modeltransformer.transformer;

import com.epam.training.modeltransformer.projection.rotation.Angle;
import com.epam.training.modeltransformer.writter.STAXCreator;
import jakarta.json.*;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.*;


public class TriangleTransformer {
    public void transform(String inputFilePath, String outputFilePath, Angle angle) {

        List<List<Angle>> angleListFromJson = readFromJSON(inputFilePath);

        if (angleListFromJson.size() > 0) {
            List<List<Angle>> angleList = applyTransformations(transformTriangleToAngle(angleListFromJson, angle));
            STAXCreator staxCreator = new STAXCreator();
            try {
                staxCreator.createDocument(angleList, outputFilePath);
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<List<Angle>> readFromJSON(String imputFilePath) {

        List<List<Angle>> angles;
        File file = new File(imputFilePath);
        InputStream inputStream;

        try {
            inputStream = new FileInputStream(file);
            JsonReader jsonReader = Json.createReader(inputStream);
            JsonArray jsonArray = jsonReader.readArray();
            angles = new ArrayList<>(jsonArray.size());

            for (JsonValue jsonValue : jsonArray) {
                JsonArray jsonArrayOfArrays = jsonValue.asJsonArray();
                List<Angle> angleArrayList = new ArrayList<>(3);
                for (JsonValue jsonValue1 : jsonArrayOfArrays) {
                    JsonObject jsonObject = jsonValue1.asJsonObject();

                    try {
                        angleArrayList.add(new Angle(Double.parseDouble(jsonObject.get("x").toString()),
                                Double.parseDouble(jsonObject.get("y").toString()),
                                Double.parseDouble(jsonObject.get("z").toString())));
                    } catch (NumberFormatException e) {
                        return Collections.emptyList();
                    }
                }
                angles.add(angleArrayList);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return angles;
    }

    public List<List<Angle>> transformTriangleToAngle(List<List<Angle>> triangles, Angle angle) {

        List<List<Angle>> triangleList = new ArrayList<>(triangles.size());

        for (List<Angle> triangle : triangles) {
            List<Angle> angles = new ArrayList<>();
            for (Angle angle1 : triangle) angles.add(multiply(angle1, angle));
            triangleList.add(angles);
        }

        return triangleList;

    }

    public Angle multiply(Angle angle, Angle angle1) {


        double x1 = angle.getAngleX();
        double y1 = (Math.cos(angle1.getAngleX()) * angle.getAngleY()) + (-Math.sin(angle1.getAngleX()) * angle.getAngleZ());
        double z1 = (Math.sin(angle1.getAngleX()) * angle.getAngleY()) + (Math.cos(angle1.getAngleX()) * angle.getAngleZ());

        double x2 = (Math.cos(angle1.getAngleY()) * x1) + (Math.sin(angle1.getAngleY()) * z1);
        double z2 = (-Math.sin(angle1.getAngleY()) * x1) + (Math.cos(angle1.getAngleY()) * z1);

        double x3 = (Math.cos(angle1.getAngleZ()) * x2) + (-Math.sin(angle1.getAngleZ()) * y1);
        double y3 = (Math.sin(angle1.getAngleZ()) * x2) + (Math.cos(angle1.getAngleZ()) * y1);


        return new Angle(x3, y3, z2);
    }

    public List<List<Angle>> applyTransformations(List<List<Angle>> triangles) {

        List<List<Angle>> triangleList = new ArrayList<>(triangles.size());
        List<Angle> angles = new ArrayList<>();
        List<Angle> angleList = new ArrayList<>();

        for (List<Angle> triangle : triangles) {
            angles.addAll(triangle);
        }

        final double smallestX = angles.stream().mapToDouble(Angle::getAngleX).min().getAsDouble();
        final double smallestY = angles.stream().mapToDouble(Angle::getAngleY).min().getAsDouble();

        if (smallestY < 0 || smallestX < 0) {
            int iterator = 0;
            for (Angle angle : angles) {

                double x;
                double y;
                if (smallestX < 0) {
                    x = angle.getAngleX() + Math.abs(smallestX);
                } else x = angle.getAngleX();
                if (smallestY < 0) {
                    y = angle.getAngleY() + Math.abs(smallestY);
                } else y = angle.getAngleY();
                angleList.add(new Angle(x, y, angle.getAngleZ()));
                iterator++;
                if (iterator == 3) {
                    //  double averageZ = angleList.stream().mapToDouble(value -> value.getAngleZ()).average().getAsDouble();
                    angleList.sort(Comparator.comparing(Angle::getAngleZ).reversed());
                    triangleList.add(angleList);
                    angleList = new ArrayList<>();
                    iterator = 0;
                }
            }
        }


        return triangleList;
    }


}
