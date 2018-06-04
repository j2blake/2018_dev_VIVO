package edu.cornell.mannlib.vitro.webapp.utils.dataGetter;

import static edu.cornell.mannlib.vitro.testing.ModelUtilitiesTestHelper.dataProperty;
import static edu.cornell.mannlib.vitro.testing.ModelUtilitiesTestHelper.model;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.junit.Before;
import org.junit.Test;

import edu.cornell.mannlib.vitro.testing.AbstractTestClass;
import edu.cornell.mannlib.vitro.testing.ModelUtilitiesTestHelper;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.rdfservice.impl.jena.model.RDFServiceModel;
import stubs.edu.cornell.mannlib.vitro.webapp.modelaccess.ModelAccessFactoryStub;
import stubs.javax.servlet.http.HttpServletRequestStub;

public class CoverColorDataGetterTest extends AbstractTestClass {
    private static final String DATA_GETTER_URI = "http://test/data_getter_uri";
    private static final String PROPERTY_SAVE_TO_VAR = "http://vitro.mannlib.cornell.edu/ontologies/display/1.1#saveToVar";
    private static final String PROPERTY_QUERY = "http://vitro.mannlib.cornell.edu/ontologies/display/1.1#query";

    private static final String FACULTY_MEMBER_URI = "http://test/faculty_uri";
    private static final String PROPERTY_COVER_COLOR = "http://vivoweb.org/ontology/core#hasCoverColor";
    private static final String RAW_QUERY_STRING = "" //
            + "SELECT DISTINCT ?color \n" //
            + "WHERE { " //
            + "  ?faculty <" + PROPERTY_COVER_COLOR + "> ?color . \n" //
            + "}";

    private CoverColorDataGetter getter;
    private HttpServletRequestStub req;
    private VitroRequest vreq;
    private Model displayModel;
    private Model aboxModel;
    private Map<String, Object> resultMap;

    @Before
    public void setup() {
        // setLoggerLevel(CoverColorDataGetter.class, Level.DEBUG);

        displayModel = model(
                dataProperty(DATA_GETTER_URI, PROPERTY_SAVE_TO_VAR, "results"),
                dataProperty(DATA_GETTER_URI, PROPERTY_QUERY,
                        RAW_QUERY_STRING));
        
        req = new HttpServletRequestStub();
        req.addParameter("faculty", FACULTY_MEMBER_URI);
        vreq = new VitroRequest(req);

        aboxModel = ModelUtilitiesTestHelper.model(
                dataProperty(FACULTY_MEMBER_URI, PROPERTY_COVER_COLOR,
                        "green"));
        RDFServiceModel rdfService = new RDFServiceModel(aboxModel);

        ModelAccessFactoryStub factory = new ModelAccessFactoryStub();
        factory.get(req).setRDFService(rdfService);
    }

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void simplestPossibleSuccess() {
        getter = new CoverColorDataGetter(vreq, displayModel, DATA_GETTER_URI);
        resultMap = getter.getData(Collections.emptyMap());
        assertEquals(list(map("color", "green")), resultMap.get("results"));
    }
    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------

    @SafeVarargs
    final List<Map<String, String>> list(Map<String, String>... maps) {
        List<Map<String, String>> l = new ArrayList<>();
        for (Map<String, String> map : maps) {
            l.add(map);
        }
        return l;
    }

    Map<String, String> map(String key, String value) {
        Map<String, String> m = new HashMap<>();
        m.put(key, value);
        return m;
    }
}
