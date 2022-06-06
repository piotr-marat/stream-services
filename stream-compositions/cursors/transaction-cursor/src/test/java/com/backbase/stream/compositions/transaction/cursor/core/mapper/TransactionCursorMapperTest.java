package com.backbase.stream.compositions.transaction.cursor.core.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.backbase.stream.compositions.transaction.cursor.core.domain.TransactionCursorEntity;
import com.backbase.stream.compositions.transaction.cursor.model.TransactionCursor;
import com.backbase.stream.compositions.transaction.cursor.model.TransactionCursor.StatusEnum;
import com.backbase.stream.compositions.transaction.cursor.model.TransactionCursorResponse;
import com.backbase.stream.compositions.transaction.cursor.model.TransactionCursorUpsertRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TransactionCursorMapperTest {

  TransactionCursorMapper mapper =
      Mappers.getMapper(TransactionCursorMapper.class);

  @Test
  void testMapToModel_Success() {
    TransactionCursorResponse transactionCursorResponse =
        mapper.mapToModel(getMockDomain());
    assertNotNull(transactionCursorResponse);
  }

  @Test
  void testmapToDomain_Success() {
    TransactionCursorEntity transactionCursorEntity =
        mapper.mapToDomain(getMockModel());
    assertNotNull(transactionCursorEntity);
  }

  @Test
  void testMapper_Success() throws JsonProcessingException {
    List<Object> txnList = mapper.convertLastTransToListFormat(getMockDomain().getLast_txn_ids());
    assertEquals(4, txnList.size());

    String txnIds = mapper
        .convertLastTransToStringFormat(getMockModel().getCursor().getLastTxnIds());
    assertEquals(txnIds, getMockDomain().getLast_txn_ids());

    Map<String, String> additionsMap = mapper
        .convertJsonToMapFormat(getMockDomain().getAdditions());
    assertEquals(additionsMap, getMockModel().getCursor().getAdditions());

    String additions = mapper.convertMapToJsonFormat(getMockModel().getCursor().getAdditions());
    assertEquals(additions, getMockDomain().getAdditions());
  }

  @Test
  void testMapper_Default() throws JsonProcessingException {
    List<Object> txnNullList = mapper.convertLastTransToListFormat(null);
    assertEquals(0, txnNullList.size());

    assertNull(mapper.convertLastTransToStringFormat(null));

    assertNull(mapper.convertJsonToMapFormat(null));

    assertNull(mapper.convertMapToJsonFormat(null));
  }

  @Test
  void testMapper_Fail() {

    assertThrows(JsonProcessingException.class,
        () -> mapper.convertJsonToMapFormat("~"));

    assertThrows(NullPointerException.class,
        () -> mapper.convertMapToJsonFormat(Map.of("test", null)));
  }

  private TransactionCursorUpsertRequest getMockModel() {
    return new TransactionCursorUpsertRequest()
        .withCursor(new TransactionCursor()
            .withId("3337f8cc-d66d-41b3-a00e-f71ff15d93cq")
            .withArrangementId("4337f8cc-d66d-41b3-a00e-f71ff15d93cq")
            .withExtArrangementId("5337f8cc-d66d-41b3-a00e-f71ff15d93cq")
            .withLegalEntityId("test-ext-emp")
            .withLastTxnDate("2022-05-24 03:18:19")
            .withStatus(StatusEnum.IN_PROGRESS)
            .withLastTxnIds(List.of("11", "12", "13", "14"))
            .withAdditions(Map.of("key1", "val1")));
  }

  private TransactionCursorEntity getMockDomain() {
    TransactionCursorEntity transactionCursorEntity = new TransactionCursorEntity();
    transactionCursorEntity.setId("3337f8cc-d66d-41b3-a00e-f71ff15d93cq");
    transactionCursorEntity.setArrangement_id("4337f8cc-d66d-41b3-a00e-f71ff15d93cq");
    transactionCursorEntity.setExt_arrangement_id("5337f8cc-d66d-41b3-a00e-f71ff15d93cq");
    transactionCursorEntity.setLegal_entity_id("test-ext-emp");
    transactionCursorEntity.setLast_txn_date(Timestamp.from(Instant.now()));
    transactionCursorEntity.setStatus(StatusEnum.IN_PROGRESS.getValue());
    transactionCursorEntity.setLast_txn_ids("11,12,13,14");
    transactionCursorEntity.setAdditions("{\"key1\":\"val1\"}");
    return transactionCursorEntity;
  }
}