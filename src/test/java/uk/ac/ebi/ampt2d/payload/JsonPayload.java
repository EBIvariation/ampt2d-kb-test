package uk.ac.ebi.ampt2d.payload;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonPayload {
    private static final String PAYLOAD_PATH = "payloads";
    private static final String JSON_FILE_EXTENSION = ".json";
    private static final String GET_METADATA = "getMetadata";
    private static final String GET_SAMPLE_METADATA = "getSampleMetadata";
    private static final String GET_DATA_DATASETS_INPUT = "getDataDatasetsInput";
    private static final String GET_DATA_DATASETS_OUTPUT = "getDataDatasetsOutput";
    private static final String GET_DATA_BASIC_INPUT = "getDataBasicInput";
    private static final String GET_DATA_BASIC_OUTPUT = "getDataBasicOutput";

    private String getMetadata;
    private String getSampleMetadata;
    private String getDataDatasetsInput;
    private String getDataDatasetsOutput;
    private String getDataBasicInput;
    private String getDataBasicOutput;

    public JsonPayload() throws Exception {
        getMetadata = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_METADATA + JSON_FILE_EXTENSION).toURI())));
        getSampleMetadata = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_SAMPLE_METADATA + JSON_FILE_EXTENSION).toURI())));
        getDataDatasetsInput = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_DATA_DATASETS_INPUT + JSON_FILE_EXTENSION).toURI())));
        getDataDatasetsOutput = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_DATA_DATASETS_OUTPUT + JSON_FILE_EXTENSION).toURI())));
        getDataBasicInput = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_DATA_BASIC_INPUT + JSON_FILE_EXTENSION).toURI())));
        getDataBasicOutput = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_DATA_BASIC_OUTPUT + JSON_FILE_EXTENSION).toURI())));
    }

    public String getMetadata() {
        return getMetadata;
    }

    public String getSampleMetadata() {
        return getSampleMetadata;
    }

    public String getDataDatasetsInput() {
        return getDataDatasetsInput;
    }

    public String getDataDatasetsOutput() {
        return getDataDatasetsOutput;
    }

    public String getDataBasicInput() {
        return getDataBasicInput;
    }

    public String getDataBasicOutput() {
        return getDataBasicOutput;
    }

    public String getPayload(String payloadName) throws Exception {
        return this.getClass().getDeclaredMethod(payloadName).invoke(this).toString();
    }
}
