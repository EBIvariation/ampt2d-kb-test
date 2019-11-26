/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
    private static final String GET_SAMPLE_DATA_DATASETS_INPUT = "getSampleDataDatasetsInput";
    private static final String GET_SAMPLE_DATA_DATASETS_OUTPUT = "getSampleDataDatasetsOutput";
    private static final String GET_GAIT_ANALYSIS_INPUT = "getGaitAnalysisInput";
    private static final String GET_GAIT_ANALYSIS_OUTPUT = "getGaitAnalysisOutput";

    private static final String GET_METADATAOX = "getMetadataOx";
    private static final String GET_SAMPLE_METADATAOX = "getSampleMetadataOx";

    private String getMetadata;
    private String getSampleMetadata;
    private String getDataDatasetsInput;
    private String getDataDatasetsOutput;
    private String getDataBasicInput;
    private String getDataBasicOutput;
    private String getSampleDataDatasetsInput;
    private String getSampleDataDatasetsOutput;
    private String getGaitAnalysisInput;
    private String getGaitAnalysisOutput;

    private String getMetadataOx;
    private String getSampleMetadataOx;

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
        getSampleDataDatasetsInput = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_SAMPLE_DATA_DATASETS_INPUT + JSON_FILE_EXTENSION).toURI())));
        getSampleDataDatasetsOutput = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_SAMPLE_DATA_DATASETS_OUTPUT + JSON_FILE_EXTENSION).toURI())));
        getGaitAnalysisInput = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_GAIT_ANALYSIS_INPUT + JSON_FILE_EXTENSION).toURI())));
        getGaitAnalysisOutput = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_GAIT_ANALYSIS_OUTPUT + JSON_FILE_EXTENSION).toURI())));

        getMetadataOx = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_METADATAOX + JSON_FILE_EXTENSION).toURI())));
        getSampleMetadataOx = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(PAYLOAD_PATH + "/" + GET_SAMPLE_METADATAOX + JSON_FILE_EXTENSION).toURI())));
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

    public String getSampleDataDatasetsInput() {
        return getSampleDataDatasetsInput;
    }

    public String getSampleDataDatasetsOutput() {
        return getSampleDataDatasetsOutput;
    }

    public String getGaitAnalysisInput() {
        return getGaitAnalysisInput;
    }

    public String getGaitAnalysisOutput() {
        return getGaitAnalysisOutput;
    }


    public String getMetadataOx() {
        return getMetadataOx;
    }

    public String getSampleMetadataOx() {
        return getSampleMetadataOx;
    }

    public String getPayload(String payloadName) throws Exception {
        return this.getClass().getDeclaredMethod(payloadName).invoke(this).toString();
    }
}
