package com.option.portfolio.portfolio.service;

import com.option.portfolio.portfolio.Util;
import com.option.portfolio.portfolio.entity.Security;
import com.option.portfolio.portfolio.repository.SecurityRepository;
import com.option.portfolio.portfolio.entity.SecurityType;
import com.option.portfolio.portfolio.repository.SecurityTypeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import java.time.Instant;
import java.util.Map;

@Service
public class DataInitializrService {
    private final SecurityTypeRepository securityTypeRepository;
    private final SecurityRepository securityRepository;
    private final PositionCsvReader positionCsvReader;
    // creating a logger
    Logger log = LoggerFactory.getLogger(DataInitializrService.class);
    public DataInitializrService(SecurityTypeRepository securityTypeRepository, SecurityRepository securityRepository, PositionCsvReader positionCsvReader) {
        this.securityTypeRepository = securityTypeRepository;
        this.securityRepository = securityRepository;
        this.positionCsvReader = positionCsvReader;
    }

    @Transactional
    public void buildInitData() {
        var stockType = new SecurityType();
        stockType.setType("STOCK");

        var callType = new SecurityType();
        callType.setType("CALL");

        var pullType = new SecurityType();
        pullType.setType("PULL");

        final SecurityType stock = securityTypeRepository.save(stockType);
        final SecurityType call = securityTypeRepository.save(callType);
        final SecurityType pull = securityTypeRepository.save(pullType);

        Map<String, Pair<Integer, Double>> sample = positionCsvReader.readSampleFile();
        sample.keySet().forEach((k) -> {
                    try {
                        String[] optionArray = k.split("-");
                        if (optionArray.length == 1) {
                            var s = new Security();
                                s.setSymbol(optionArray[0]);
                                s.setTicker(optionArray[0]);
                                s.setSecurityType(stock);
                                s.setMu(Math.random());
                                s.setVolatility(Math.random());
                                s.setPrice(sample.get(k).getSecond());
                                s.setPosition(sample.get(k).getFirst());

                            securityRepository.save(s);
                        } else if (optionArray.length == 5) {
                            SecurityType securityType = optionArray[4].equals("P")?pull:call;
                            Instant maturity = Util.convertMaturityStrToInstant(optionArray[1]+"-"+optionArray[2]);
                            var s = new Security();
                                s.setSymbol(k);
                                s.setTicker(optionArray[0]);
                                s.setSecurityType(securityType);
                                s.setMu(Math.random());
                                s.setVolatility(Math.random());
                                s.setStrike(Double.parseDouble(optionArray[3]));
                                s.setMaturity(maturity);
                                s.setPrice(sample.get(k).getSecond());
                                s.setPosition(sample.get(k).getFirst());

                            securityRepository.save(s);
                        } else {
                            log.warn("Skip the sample record : " + k);
                        }
                    } catch (Exception ex) {
                        log.error("Skip the sample record : " + k, ex);
                    }
                }
        );
    }
}
