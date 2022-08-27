package com.backbase.stream.product.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Batch product ingestion mode.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchProductIngestionMode {

    private FunctionGroupsIngestionMode functionGroupsIngestionMode = FunctionGroupsIngestionMode.UPSERT;
    private DataGroupsIngestionMode dataGroupIngestionMode = DataGroupsIngestionMode.UPSERT;
    private ArrangementsIngestionMode arrangementsIngestionMode = ArrangementsIngestionMode.UPSERT;

    /**
     * @return True, if function groups should be replaced.
     * Otherwise, they should be just updated.
     */
    public Boolean isFunctionGroupsReplaceEnabled() {
        return functionGroupsIngestionMode == FunctionGroupsIngestionMode.REPLACE;
    }

    /**
     * @return True, if data groups should be replaced.
     * Otherwise, they should be just updated.
     */
    public Boolean isDataGroupsReplaceEnabled() {
        return dataGroupIngestionMode == DataGroupsIngestionMode.REPLACE;
    }

    /**
     * @return True, if arrangements should be replaced (non-existing arrangements will be REMOVED from DBS).
     * Otherwise, they should be just updated.
     */
    public Boolean isArrangementsReplaceEnabled() {
        return arrangementsIngestionMode == ArrangementsIngestionMode.REPLACE;
    }

    /**
     * Preset BatchProductIngestionMode: all set to UPSERT.
     *
     * @return BatchProductIngestionMode
     */
    public static BatchProductIngestionMode upsertMode() {
        return BatchProductIngestionMode.builder()
                .functionGroupsIngestionMode(FunctionGroupsIngestionMode.UPSERT)
                .dataGroupIngestionMode(DataGroupsIngestionMode.UPSERT)
                .arrangementsIngestionMode(ArrangementsIngestionMode.UPSERT)
                .build();
    }

    /**
     * Preset BatchProductIngestionMode: all set to REPLACE.
     *
     * @return BatchProductIngestionMode
     */
    public static BatchProductIngestionMode replaceMode() {
        return BatchProductIngestionMode.builder()
                .functionGroupsIngestionMode(FunctionGroupsIngestionMode.REPLACE)
                .dataGroupIngestionMode(DataGroupsIngestionMode.REPLACE)
                .arrangementsIngestionMode(ArrangementsIngestionMode.REPLACE)
                .build();
    }

    /**
     * Preset BatchProductIngestionMode: FunctionGroupsIngestionMode set to REPLACE, rest to UPSERT.
     *
     * @return BatchProductIngestionMode
     */
    public static BatchProductIngestionMode functionGroupsReplaceMode() {
        return BatchProductIngestionMode.builder()
                .functionGroupsIngestionMode(FunctionGroupsIngestionMode.REPLACE)
                .build();
    }

    /**
     * Preset BatchProductIngestionMode: DataGroupsIngestionMode set to REPLACE, rest to UPSERT.
     *
     * @return BatchProductIngestionMode
     */
    public static BatchProductIngestionMode dataGroupsReplaceMode() {
        return BatchProductIngestionMode.builder()
                .dataGroupIngestionMode(DataGroupsIngestionMode.REPLACE)
                .build();
    }

    /**
     * UPSERT - Function groups will be INSERTED/UPDATED.
     * REPLACE - Function groups will be REPLACED.
     */
    public enum FunctionGroupsIngestionMode {
        UPSERT,
        REPLACE;
    }

    /**
     * UPSERT - Data groups will be INSERTED/UPDATED.
     * REPLACE - Data groups will be REPLACED.
     */
    public enum DataGroupsIngestionMode {
        UPSERT,
        REPLACE
    }

    /**
     * UPSERT - Arrangements will be INSERTED/UPDATED.
     * REPLACE - Arrangements will be REPLACED.
     */
    public enum ArrangementsIngestionMode {
        UPSERT,
        REPLACE
    }
}
