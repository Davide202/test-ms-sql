package com.example.test_ms_sql.config;

import com.example.test_ms_sql.domain.Information;
import com.example.test_ms_sql.repo.InformationRepository;
import com.example.test_ms_sql.repo.ProcedureInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class AutomaticRun {

    private static final Logger logger = Logger.getLogger(AutomaticRun.class.getName());

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private ProcedureInformationRepository procedureInformationRepository;

    @Scheduled(fixedDelayString = "1", timeUnit = TimeUnit.HOURS)
    public void run() throws ParseException {
        logger.info("HERE WE GOO!!!");
        List<Information> list = new ArrayList<>();

        informationRepository.deleteAll(informationRepository.findAll());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Information information = Information.builder()
                .name("davide")
                .address("via adige")
                .birthday(format.parse("1987-05-15"))
                .build();

        information = informationRepository.save(information);

         /*
        list = informationRepository.findAll();
        logger.info(list.stream()
                        .map(Information::toString)
                        .collect(StringBuilder::new, StringBuilder::append,StringBuilder::append)
                        .toString()
        );
        */


         list= procedureInformationRepository.callStoredProcedure();

        logger.info(list.stream()
                .map(Information::toString)
                .collect(StringBuilder::new, StringBuilder::append,StringBuilder::append)
                .toString()
        );

    }
}
