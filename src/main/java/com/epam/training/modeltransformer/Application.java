package com.epam.training.modeltransformer;

import com.epam.training.modeltransformer.projection.rotation.Angle;
import com.epam.training.modeltransformer.transformer.TriangleTransformer;

import java.io.IOException;

public class Application {
    private static final String EIFFEL_JSON = "input/eiffel.json";
    private static final String EIFFEL_HTML = "eiffel.html";
    private static final Angle EIFFEL_ANGLE = new Angle(1.8, -0.5, 0);
    private static final String TETRAHEDRON_JSON = "input/tetrahedron.json";
    private static final String TETRAHEDRON_HTML = "tetrahedron.html";
    private static final Angle TETRA_ANGLE = new Angle(-0.5, 1, 0.5);

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.transform();
    }

    private void transform() throws IOException {
        TriangleTransformer triangleTransformer = new TriangleTransformer();
        triangleTransformer.transform(TETRAHEDRON_JSON, TETRAHEDRON_HTML, TETRA_ANGLE);
        //triangleTransformer.transform(EIFFEL_JSON, EIFFEL_HTML, EIFFEL_ANGLE);
    }
}
