package com.backbase.stream.compositions.legalentity.core.service.impl;

import com.backbase.stream.compositions.legalentity.core.model.LegalEntityPullRequest;
import com.backbase.stream.compositions.legalentity.core.service.LegalEntityIntegrationService;
import com.backbase.stream.compositions.legalentity.integration.client.LegalEntityIntegrationApi;
import com.backbase.stream.compositions.legalentity.integration.client.model.LegalEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class LegalEntityIntegrationServiceImpl implements LegalEntityIntegrationService {
    private final LegalEntityIntegrationApi legalEntityIntegrationApi;

    /**
     * {@inheritDoc}
     */
    public Mono<LegalEntity> pullLegalEntity(LegalEntityPullRequest ingestPullRequest) {
        return Mono.just(new LegalEntity().withName("LE_Retail_" + ingestPullRequest.getLegalEntityExternalId())
                .withActivateSingleServiceAgreement(true)
                .withExternalId(ingestPullRequest.getLegalEntityExternalId()));
        /*
        return legalEntityIntegrationApi
                .pullLegalEntity(
                        ingestPullRequest.getLegalEntityExternalId(),
                        ingestPullRequest.getAdditionalParameters())
                .map(PullLegalEntityResponse::getLegalEntity);

         */
    }
}
